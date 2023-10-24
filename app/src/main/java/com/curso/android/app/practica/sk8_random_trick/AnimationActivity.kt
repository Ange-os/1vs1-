package com.curso.android.app.practica.sk8_random_trick

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnimationActivity: AppCompatActivity() {
    private lateinit var imgLogo: ImageView
    private lateinit var titulo: TextView
    private lateinit var subtitulo: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_animation)

        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.desplazar_arriba)

        val animation2: Animation = AnimationUtils.loadAnimation(this, R.anim.desplazar_abajo)

        imgLogo = findViewById<ImageView>(R.id.imageLogo)

        titulo = findViewById<TextView>(R.id.titulo)

        subtitulo = findViewById<TextView>(R.id.Subtitle)

        imgLogo.startAnimation(animation)

        titulo.startAnimation(animation)

        subtitulo.startAnimation(animation2)

        Handler().postDelayed({
            // Iniciar el nuevo Activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Finalizar este Activity
            finish()
        }, 4000)

    }
}