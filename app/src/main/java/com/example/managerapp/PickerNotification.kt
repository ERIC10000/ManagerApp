package com.example.managerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.managerapp.helpers.ApiHelper
import com.example.managerapp.helpers.Constants
import org.json.JSONArray
import org.json.JSONObject

class PickerNotification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picker_notification)

        val message = findViewById<EditText>(R.id.emailLogin)
        val title  = findViewById<EditText>(R.id.emailLogin2)
        val send  = findViewById<AppCompatButton>(R.id.btn_register)
        send.setOnClickListener {
            if (message.text.toString().isEmpty() || title.text.toString().isEmpty()){
                Toast.makeText(applicationContext, "Please fill in the details", Toast.LENGTH_SHORT).show()
            }
            else{
                sendMessage(message , title)
            }
        }
    }

    private fun sendMessage (message: EditText , title:EditText){
        val helper = ApiHelper(this)
        val api = Constants.BASE_URL + "send_notifications_to_pickers"
        val body = JSONObject()
        body.put("notification", message.text.toString())
        body.put("sender","Manager")
        body.put("title",title.text.toString())
        helper.post(api , body , object:ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, result?.getString("message"), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(result: String?) {

            }
        })
    }
}