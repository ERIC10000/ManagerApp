package com.example.managerapp

import android.graphics.Bitmap
import android.graphics.Canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import java.io.ByteArrayOutputStream
import java.io.File



import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image

class PreviewIDActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_idactivity)


        //id  card to pdf
        val download: AppCompatButton = findViewById(R.id.btn_login)
        download.setOnClickListener {

            val pdfFileName = "IDCARD.pdf"
            val pdfDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            val pdfFile = File(pdfDirectory, pdfFileName)

            val pdfDocument = PdfDocument(PdfWriter(pdfFile))
            val pdf = Document(pdfDocument)

            // Create a bitmap from your CardView
            val cardView : CardView = findViewById(R.id.cardView)
            val cardViewBitmap = viewToBitmap(cardView)


            // Create a bitmap from your CardView
            val cardView2 : CardView = findViewById(R.id.cardView2)
            val cardViewBitmap2 = viewToBitmap(cardView2)



            // Convert the bitmap to an iText Image
            val image = Image(ImageDataFactory.create(cardViewBitmapToByteArray(cardViewBitmap)))
            pdf.add(image)

            // Convert the bitmap to an iText Image
            val image2 = Image(ImageDataFactory.create(cardViewBitmapToByteArray(cardViewBitmap2)))
            pdf.add(image2)

            // Close the document
            pdf.close()

            // Now you can save the PDF file and provide a download link or any other functionality you want.
            Toast.makeText(applicationContext, "Saved Successfully, Proceed to Downloads", Toast.LENGTH_LONG).show()


        }


    }

    private fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(canvas)
        return bitmap
    }

    private fun cardViewBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}