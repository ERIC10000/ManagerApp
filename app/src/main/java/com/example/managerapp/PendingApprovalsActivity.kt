package com.example.managerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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

class PendingApprovalsActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var unApprovedMembersAdapter: UnApprovedMembersAdapter
    lateinit var swipe : SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_approvals)

        val county = PrefsHelper.getPrefs(applicationContext , "county")
        val text: TextView = findViewById(R.id.noApprovals)

        fetchUnapprovedMembers(text , county)
        unApprovedMembersAdapter = UnApprovedMembersAdapter(applicationContext)
        recyclerView = findViewById(R.id.recycler1)
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipe = findViewById(R.id.swipe)
        swipe.setOnRefreshListener {
            fetchUnapprovedMembers(text , county)
        }
    }

    fun fetchUnapprovedMembers(text: TextView, county: String){
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
}