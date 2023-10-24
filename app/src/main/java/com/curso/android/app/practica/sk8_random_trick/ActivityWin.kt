package com.curso.android.app.practica.sk8_random_trick

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView

import android.widget.TextView


import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class ActivityWin: AppCompatActivity() {
    private lateinit var textName: TextView
    private lateinit var data: TextView
    private lateinit var btnCompartir: ImageButton
    private lateinit var btnimg: ImageButton
    private lateinit var btnempezar: ImageView

    fun ShareScreenshot(){
        val rootView = window.decorView.rootView
        rootView.isDrawingCacheEnabled = true

        val screenshot = Bitmap.createBitmap(rootView.drawingCache)
        rootView.isDrawingCacheEnabled = false

        val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screenshot.png")
        val fileOutputStream = FileOutputStream(file)
        screenshot.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)

        textName = findViewById<TextView>(R.id.textViewWin)

        data = findViewById<TextView>(R.id.textViewData)


        btnCompartir = findViewById<ImageButton>(R.id.imageButtonCompartir)

        btnimg = findViewById<ImageButton>(R.id.imageButtonAdd)

        btnempezar = findViewById(R.id.imageBorrar)

        val champion = intent.getStringExtra("champion")

        textName.text = champion



        fun ShareScreenshot(){
            val rootView = window.decorView.rootView
            rootView.isDrawingCacheEnabled = true

            val screenshot = Bitmap.createBitmap(rootView.drawingCache)
            rootView.isDrawingCacheEnabled = false

            val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screenshot.png")
            val fileOutputStream = FileOutputStream(file)
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()

            // Agrega el código para compartir la imagen aquí
            val imageUri = Uri.fromFile(file)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)

            // Agrega las aplicaciones de destino que desees permitir que el usuario elija
            val targetApps = arrayOf("com.instagram.android", "com.facebook.katana", "com.whatsapp")
            shareIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetApps.map { packageName ->
                val intent = Intent(shareIntent)
                intent.setPackage(packageName)
                intent
            }.toTypedArray())

            startActivity(Intent.createChooser(shareIntent, "Compartir captura de pantalla"))
        }
        val date = Date()
        val timeZone = TimeZone.getDefault()
        val sdf = SimpleDateFormat("MMM dd yyyy")
        sdf.timeZone = timeZone
        val formattedDate = sdf.format(date)

        // Combina el texto existente con la hora formateada
        val extra = data.text.toString()
        val todo = "$extra $formattedDate"
        data.text = todo
        btnimg.setOnClickListener {
            // Abre la galería de fotos
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, SELECT_IMAGE_REQUEST)
        }
        btnCompartir.setOnClickListener(){
            ShareScreenshot()
        }

        btnempezar.setOnClickListener(){
            ActivityListName.CacheManager.cacheListName.clear()
            ActivityListName.CacheManager.cacheListWins.clear()

            val intent= Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
        }




    }
    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            // Obtiene la imagen seleccionada
            val selectedImageUri = data?.data

            // Muestra la imagen en el ImageView
            btnimg.setImageURI(selectedImageUri)
        }
    }

    companion object {
        private const val SELECT_IMAGE_REQUEST = 1
    }
}