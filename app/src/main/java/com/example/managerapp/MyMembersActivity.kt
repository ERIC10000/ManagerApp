package com.example.managerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.managerapp.adapters.MembersAdapter
import com.example.managerapp.adapters.UnApprovedMembersAdapter
import com.example.managerapp.helpers.ApiHelper
import com.example.managerapp.helpers.Constants
import com.example.managerapp.helpers.PrefsHelper
import com.example.managerapp.models.ApprovedMemberItem
import com.example.managerapp.models.Member
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

class MyMembersActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var membersAdapter: MembersAdapter
    lateinit var swipe : SwipeRefreshLayout
    lateinit var itemList: List<Member>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_members)

        val county = PrefsHelper.getPrefs(applicationContext , "county")
        val text: TextView = findViewById(R.id.noApprovals)

        fetchMembers(text , county)
        membersAdapter = MembersAdapter(applicationContext)
        recyclerView = findViewById(R.id.recycler2)
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipe = findViewById(R.id.swipe)
        swipe.setOnRefreshListener {
            fetchMembers(text , county)
        }

        val searchBar : EditText = findViewById(R.id.search)
        searchBar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    fun fetchMembers(text: TextView, county: String){
        val helper = ApiHelper(this)
        val body = JSONObject()
        body.put("county",county)
        val api = Constants.BASE_URL + "view_members"
        helper.post(api , body , object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                swipe.isRefreshing  = false
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(
                    result.toString() ,
                    Array<Member>::class.java).toList()

                membersAdapter.setListItems(itemList)
                recyclerView.adapter = membersAdapter
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

    private fun filter(text: String) {



        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Member> = ArrayList()
        // running a for loop to compare elements.
        for (item in itemList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.FirstName.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            membersAdapter.filterList(filteredlist)
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            membersAdapter.filterList(filteredlist)
        }
    }
}