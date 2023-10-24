package com.curso.android.app.practica.sk8_random_trick

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.os.CountDownTimer
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ActivityBattle: AppCompatActivity() {
    private lateinit var firstName: TextView
    private lateinit var secudName: TextView
    private lateinit var spinertime: Spinner
    private lateinit var texTime: TextView
    private lateinit var btnEmpezar: Button
    private lateinit var btnDelete1: Button
    private lateinit var btnDelete2: Button
    private lateinit var pareja: MutableList<String>
    private var countDownTimer: CountDownTimer? = null
    private lateinit var imgVS: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        firstName = findViewById<TextView>(R.id.firstname)
        secudName = findViewById<TextView>(R.id.secundname)
        spinertime = findViewById<Spinner>(R.id.spinnerTime)
        texTime = findViewById<TextView>(R.id.textTiempo)
        btnEmpezar = findViewById<Button>(R.id.buttonempezar)
        btnDelete1 = findViewById<Button>(R.id.buttonDeletePlayer1)
        btnDelete2 = findViewById<Button>(R.id.buttonDeletePlayer2)
        imgVS = findViewById<ImageView>(R.id.imageViewVS)

        var idaYVuelta = 0

        val tiempos = listOf<Int>(2,10,60, 120, 180)

        val adarter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tiempos)

        spinertime.adapter = adarter

        val battalions = intent.getStringArrayExtra("parde") ?: arrayOf<String>()
        val list = battalions.toList()

        pareja = list.toMutableList()

        if(list.size ==  1){
            firstName.text = list[0]
            secudName.visibility = View.INVISIBLE
            imgVS.visibility = View.INVISIBLE
            spinertime.visibility = View.INVISIBLE
            btnEmpezar.text = "Pass"
            texTime.visibility = View.INVISIBLE
            val layoutoParamss = firstName.layoutParams as ConstraintLayout.LayoutParams

            layoutoParamss.horizontalBias = 0.5f

            firstName.layoutParams = layoutoParamss

        }

        if (list != null && list.size >= 2) {
            firstName.text = list[0]
            secudName.text = list[1]
        }
        fun vibrate(){
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            if (vibrator.hasVibrator()){
                vibrator.vibrate(1000)
            }
        }

        fun setupTimer(milliseconds: Int) {
            countDownTimer?.cancel() // Detener el temporizador anterior, si existe

            countDownTimer = object : CountDownTimer(milliseconds.toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsRemaining = millisUntilFinished / 1000
                    texTime.text = "$secondsRemaining"
                }

                override fun onFinish() {
                    texTime.text = "Tiempo"
                    vibrate()
                    if (idaYVuelta >= 4 ){
                        btnEmpezar.text = "Replica"
                        btnDelete2.visibility=View.VISIBLE
                        btnDelete1.visibility=View.VISIBLE
                    }



                }
            }
        }

        fun onStop() {
            super.onStop()
            // Detener el temporizador para evitar fugas de memoria
            countDownTimer?.cancel()
        }



        btnEmpezar.setOnClickListener(){
            idaYVuelta++
            if (list.size == 1){
                ActivityListName.CacheManager.cacheListName.removeAll(pareja)
                val listallena = ActivityListName.CacheManager.cacheListName
                ActivityListName.CacheManager.cacheListWins.add(pareja[0])
                val intent = Intent(this, ActivityListName::class.java)
                listallena.shuffle()
                intent.putExtra("nombrs", listallena.toTypedArray())
                startActivity(intent)
            } else{
                val inputTime = spinertime.selectedItem as Int
                setupTimer(inputTime * 1000) // Convierte segundos a milisegundos
                countDownTimer?.start()
            }

        }


        btnDelete1.setOnClickListener(){
            ActivityListName.CacheManager.cacheListName.removeAll(pareja)
            ActivityListName.CacheManager.cacheListWins.add(pareja[1])
            val listallena = ActivityListName.CacheManager.cacheListName
            val intent = Intent(this, ActivityListName::class.java)
            intent.putExtra("nombrs", listallena.toTypedArray())
            startActivity(intent)
            idaYVuelta = 0
        }

        btnDelete2.setOnClickListener(){
            ActivityListName.CacheManager.cacheListName.removeAll(pareja)
            val listallena = ActivityListName.CacheManager.cacheListName
            ActivityListName.CacheManager.cacheListWins.add(pareja[0])
            val intent = Intent(this, ActivityListName::class.java)
            intent.putExtra("nombrs", listallena.toTypedArray())
            startActivity(intent)
            idaYVuelta = 0
        }


    }


}