package com.example.managerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvNotifyPickers  = findViewById<TextView>(R.id.NotifyPickers)
        tvNotifyPickers.setOnClickListener {
            val intent = Intent(applicationContext, PickerNotification::class.java)
            startActivity(intent)
        }
        val managerTitle = findViewById<TextView>(R.id.managerTitle)
        managerTitle.text = ""
    }


}