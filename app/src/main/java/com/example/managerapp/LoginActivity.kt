package com.example.managerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.managerapp.helpers.ApiHelper
import com.example.managerapp.helpers.Constants
import com.example.managerapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val email = findViewById<EditText>(R.id.emailLogin)
        val password = findViewById<EditText>(R.id.passwordLogin)
        val login = findViewById<AppCompatButton>(R.id.btn_login)
        val link = findViewById<TextView>(R.id.ForgotLogin)

        link.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }

        login.setOnClickListener {
            if(email.text.toString().isEmpty() || password.text.toString().isEmpty()){
                Toast.makeText(applicationContext, "Please fill in the required fields", Toast.LENGTH_SHORT).show()
            }
            else{
                    login(email , password)
            }
        }

    }

    private fun login (email:EditText , password:EditText){
        val helper = ApiHelper(this)
        val api = Constants.BASE_URL + "manager_login"
        val body = JSONObject()
        body.put("email",email.text.toString())
        body.put("password",password.text.toString())
        helper.post(api , body , object: ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {

                val data = result?.getJSONObject("data")
                val id = data?.getInt("ID")
                PrefsHelper.savePrefs(applicationContext,"id",id!!.toString())
                val firstName = data?.getString("First Name")
                PrefsHelper.savePrefs(applicationContext,"firstName",firstName!!)

                val lastName = data?.getString("Last Name")
                PrefsHelper.savePrefs(applicationContext,"lastName",lastName!!)

                Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext , MainActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext, "An error occurred , check if your email is correct", Toast.LENGTH_SHORT).show()
            }
        })
    }
}