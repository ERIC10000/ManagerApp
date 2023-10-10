package com.example.managerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.managerapp.helpers.ApiHelper
import com.example.managerapp.helpers.Constants
import com.example.managerapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val passwordLogin = findViewById<EditText>(R.id.passwordLogin)
        val passwordLogin2 = findViewById<EditText>(R.id.passwordLogin2)
        val nextButton = findViewById<AppCompatButton>(R.id.next2)

        nextButton.setOnClickListener {
            if (passwordLogin.text.toString().isEmpty() || passwordLogin2.text.toString()
                    .isEmpty()
            ) {

                Toast.makeText(applicationContext, "The passwords do not match", Toast.LENGTH_SHORT)
                    .show()
            } else {
                    val id = PrefsHelper.getPrefs(applicationContext,"id")

                    updatePassword(passwordLogin2 , id.toInt())
            }
        }
    }

    private fun updatePassword(password:EditText , id:Int){
        val helper = ApiHelper(this)
        val api = Constants.BASE_URL + "update_password"
        val body = JSONObject()
        body.put("password",password.text.toString())
        body.put("id",id)

        helper.post(api , body , object:ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                var response = result?.getString("message")
                Toast.makeText(applicationContext, response, Toast.LENGTH_SHORT).show()
                if (response == "Password Updated successfully"){
                    val intent = Intent(applicationContext , LoginActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext, "An error occurred , Please try again later", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
