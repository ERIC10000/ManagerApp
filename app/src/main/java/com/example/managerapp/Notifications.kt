package com.example.managerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.managerapp.adapters.NotificationAdapter
import com.example.managerapp.helpers.ApiHelper
import com.example.managerapp.helpers.Constants
import com.example.managerapp.helpers.PrefsHelper
import com.example.managerapp.models.Notification
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

class Notifications : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var notificationAdapter: NotificationAdapter
    lateinit var itemList: List<Notification>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)


        recyclerView = findViewById(R.id.recylerView)
        notificationAdapter = NotificationAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemList = listOf()
        val searchBar : EditText = findViewById(R.id.searchBar)
        searchBar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


        getNotifications()
    }


    private fun getNotifications() {

        val helper = ApiHelper(this)
        val api = Constants.BASE_URL + "/check_notification"
        val body = JSONObject()
        val county = PrefsHelper.getPrefs(this , "county")
        body.put("county" , county )
        helper.post(api , body , object:ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(
                    result.toString(),
                    Array<Notification>::class.java
                ).toList()
                notificationAdapter.setListItems(itemList)
                recyclerView.adapter = notificationAdapter
                Log.d("meso",itemList.toString())
            }

            override fun onSuccess(result: JSONObject?) {

            }

            override fun onFailure(result: String?) {

            }
        })


    }

    //Filter
    private fun filter(text: String) {



        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Notification> = ArrayList()
        // running a for loop to compare elements.
        for (item in itemList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.title!!.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            notificationAdapter.filterList(filteredlist)
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            notificationAdapter.filterList(filteredlist)
        }
    }
}