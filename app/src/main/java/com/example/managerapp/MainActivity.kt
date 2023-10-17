package com.example.managerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.managerapp.adapters.UnApprovedMembersAdapter
import com.example.managerapp.helpers.ApiHelper
import com.example.managerapp.helpers.Constants
import com.example.managerapp.helpers.PrefsHelper
import com.example.managerapp.models.ApprovedMemberItem
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var unApprovedMembersAdapter: UnApprovedMembersAdapter
    lateinit var swipe : SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        unApprovedMembersAdapter = UnApprovedMembersAdapter(applicationContext)
        recyclerView = findViewById(R.id.recycler1)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val count = findViewById<TextView>(R.id.memberCount)
        getMemberCount(count)

        val logout = findViewById<ImageView>(R.id.logout)
        logout.setOnClickListener {
            PrefsHelper.clearPrefs(this)
            startActivity(Intent(applicationContext , LoginActivity::class.java))
        }



        val tvNotifyPickers  = findViewById<TextView>(R.id.NotifyPickers)
        tvNotifyPickers.setOnClickListener {
            val intent = Intent(applicationContext, PickerNotification::class.java)
            startActivity(intent)
        }

        val text: TextView = findViewById(R.id.noApprovals)
        val managerTitle = findViewById<TextView>(R.id.managerTitle)
        val firstName = PrefsHelper.getPrefs(applicationContext , "firstName")
        val lastName = PrefsHelper.getPrefs(applicationContext , "lastName")
        val county = PrefsHelper.getPrefs(applicationContext , "county")
        managerTitle.text = firstName + " " + lastName
        fetchUnapprovedMembers(text , county)

        swipe = findViewById(R.id.swipe)
        swipe.setOnRefreshListener {
            fetchUnapprovedMembers(text , county)
        }
    }

    fun fetchUnapprovedMembers(text:TextView , county: String){
        val helper = ApiHelper(this)
        val body = JSONObject()
        body.put("county",county)
        val api = Constants.BASE_URL + "view_unapproved_members"
        helper.post(api , body , object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                swipe.isRefreshing  = false
                val gson = GsonBuilder().create()
                val itemList:List<ApprovedMemberItem> = gson.fromJson(
                    result.toString() ,
                    Array<ApprovedMemberItem>::class.java).toList()

                unApprovedMembersAdapter.setListItems(itemList)
                recyclerView.adapter = unApprovedMembersAdapter
                Log.d("jeso",itemList.toString())
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(result: JSONObject?) {
                text.visibility = View.VISIBLE
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
                swipe.isRefreshing  = false
            }

            override fun onFailure(result: String?) {
                text.visibility = View.VISIBLE
                swipe.isRefreshing  = false
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getMemberCount (count:TextView){
        val helper = ApiHelper(this)
        val api = Constants.BASE_URL + "view_number_of_members"
        val body = JSONObject()
        body.put("ward","Makadara")
        helper.post(api , body , object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                var counted = result?.getString("row_count")
                count.text = counted
            }

            override fun onFailure(result: String?) {

            }
        })

    }



    }

