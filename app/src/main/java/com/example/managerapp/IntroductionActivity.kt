package com.example.managerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class IntroductionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        val start = findViewById<AppCompatButton>(R.id.btn_firstscreen)
        start.setOnClickListener {
            val intent = Intent(applicationContext , LoginActivity::class.java)
            startActivity(intent)
        }
    }
}