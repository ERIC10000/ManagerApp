package com.example.managerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
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

        // change Password Dialog
        val changePassDialog: LinearLayout = findViewById(R.id.changePassword)
        changePassDialog.setOnClickListener {

            val alertDialog = AlertDialog.Builder(this@MainActivity).create()
            alertDialog.setTitle("")
            val view =
                LayoutInflater.from(this@MainActivity).inflate(R.layout.change_password_dialog, null, false)
            alertDialog.setView(view)

            // radio button implementation here...


            alertDialog.show()
        }


        // my members
        val myMembers: LinearLayout = findViewById(R.id.linearMembers)
        myMembers.setOnClickListener {

            val intent = Intent(applicationContext, MyMembersActivity::class.java)
            startActivity(intent)
        }


        // pending approvals
        val pendingApprovals: LinearLayout = findViewById(R.id.linearPendingApprovals)
        pendingApprovals.setOnClickListener {

            val intent = Intent(applicationContext, PendingApprovalsActivity::class.java)
            startActivity(intent)
        }


        // Add Profile Photo
        val addProfilePhoto: LinearLayout = findViewById(R.id.linearAddProfilePhoto)
        addProfilePhoto.setOnClickListener {

            val intent = Intent(applicationContext, AddProfilePhotoActivity::class.java)
            startActivity(intent)




        }


        // intent preview id
        val download: AppCompatButton = findViewById(R.id.btn_login)
        download.setOnClickListener {
            val intent = Intent(applicationContext, PreviewIDActivity::class.java)
            startActivity(intent)

        }



//        unApprovedMembersAdapter = UnApprovedMembersAdapter(applicationContext)
//        recyclerView = findViewById(R.id.recycler1)
//        recyclerView.layoutManager = LinearLayoutManager(this)




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

//        val text: TextView = findViewById(R.id.noApprovals)
        val managerTitle = findViewById<TextView>(R.id.managerTitle)
        val firstName = PrefsHelper.getPrefs(applicationContext , "firstName")
        val lastName = PrefsHelper.getPrefs(applicationContext , "lastName")
        val county = PrefsHelper.getPrefs(applicationContext , "county")
        val id = PrefsHelper.getPrefs(applicationContext , "id")

        val bindedCounty = findViewById<TextView>(R.id.kaunti)
        bindedCounty.text = "  " +county

        val count = findViewById<TextView>(R.id.memberCount)
        getMemberCount(count,county)

        val idNo = findViewById<TextView>(R.id.idvalue)
        idNo.text = "  " +id

        val name = findViewById<TextView>(R.id.namevalue)
        name.text =  "  " + firstName + " " + lastName

        val caunty = findViewById<TextView>(R.id.countyvalue)
        caunty.text = "    " + county



        managerTitle.text = "  " +firstName + " " + lastName


//        fetchUnapprovedMembers(text , county)
//
//
//        swipe = findViewById(R.id.swipe)
//        swipe.setOnRefreshListener {
//            fetchUnapprovedMembers(text , county)
//        }
    }



    fun getMemberCount (count:TextView , county: String){
        val helper = ApiHelper(this)
        val api = Constants.BASE_URL + "view_number_of_members"
        val body = JSONObject()
        body.put("county",county)
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

