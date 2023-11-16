package com.example.managerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.managerapp.helpers.PrefsHelper


class PreviewIDActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_idactivity)

        val firstName = PrefsHelper.getPrefs(applicationContext , "firstName")
        val lastName = PrefsHelper.getPrefs(applicationContext , "lastName")
        val county = PrefsHelper.getPrefs(applicationContext , "county")
        val id = PrefsHelper.getPrefs(applicationContext , "id")

        val idName : TextView = findViewById(R.id.namevalue)
        val idNo : TextView = findViewById(R.id.idvalue)
        val idPhone : TextView = findViewById(R.id.telvalue)
        val idCounty : TextView = findViewById(R.id.countyvalue)

        idName.text = firstName + " " + lastName
        idNo.text = id
        idPhone.text = "0714296170"
        idCounty.text = county

    }
}