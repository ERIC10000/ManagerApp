package com.example.managerapp

import android.graphics.Bitmap
import android.graphics.Canvas

//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.os.Environment
//import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
//import androidx.cardview.widget.CardView
//import java.io.ByteArrayOutputStream
//import java.io.File
import android.content.Context



//import com.itextpdf.io.image.ImageDataFactory
//import com.itextpdf.kernel.pdf.PdfDocument
//import com.itextpdf.kernel.pdf.PdfWriter
//import com.itextpdf.layout.Document
//import com.itextpdf.layout.element.Image


import android.content.Intent
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.os.Bundle

import android.widget.TextView
import com.example.managerapp.helpers.PrefsHelper

import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.app.DownloadManager



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


        //id  card to pdf
        val download: AppCompatButton = findViewById(R.id.btn_login)
        download.setOnClickListener {

            // front
            val cardView : CardView = findViewById(R.id.cardView)
            createAndSavePDF(this, cardView)


           // Now you can save the PDF file and provide a download link or any other functionality you want.
            Toast.makeText(applicationContext, "Downloaded Successfully, Check Downloads", Toast.LENGTH_LONG).show()

      }



    }

    private fun createAndSavePDF(context: Context, view: View) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(view.width, view.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        view.draw(canvas)

        pdfDocument.finishPage(page)

        // Get the directory for saving the PDF
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "PDFs")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val fileName = "KeNaWaPWA.pdf"
        val file = File(directory, fileName)

        try {
            val outputStream = FileOutputStream(file)
            pdfDocument.writeTo(outputStream)
            pdfDocument.close()
            outputStream.close()

//            // Downloads
//            val intent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
//            context.startActivity(intent)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}