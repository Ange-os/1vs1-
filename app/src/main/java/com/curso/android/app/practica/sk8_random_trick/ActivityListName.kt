package com.curso.android.app.practica.sk8_random_trick

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity.*
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.curso.android.app.practica.sk8_random_trick.ActivityListName.CacheManager.cacheListName
import com.curso.android.app.practica.sk8_random_trick.ActivityListName.CacheManager.cacheListWins

class ActivityListName: AppCompatActivity() {

    object CacheManager {
        var cacheListName: MutableList<String> = mutableListOf()
        val cacheListWins: MutableList<String> = mutableListOf()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_name)



        //val list = findViewById<ListView>(R.id.listView)
        val btnBack = findViewById<Button>(R.id.buttoback)

        val names = intent.getStringArrayExtra("nombrs") ?: arrayOf<String>()
        var listname = names.toList() as List<String>

        val limpiaSet: MutableSet<String> = HashSet(listname)

        limpiaSet.shuffled()

        val limpiaList: MutableList<String> = limpiaSet.toMutableList()

        limpiaList.shuffled()

        val paresname = limpiaList.chunked(2)

        val table = findViewById<TableLayout>(R.id.tablepares)

        cacheListName.addAll(limpiaList)


        if (cacheListName.size == 0){
            val intent = Intent(this, ActivityListName::class.java)
            intent.putExtra("nombrs", cacheListWins.toTypedArray())
            cacheListWins.clear()
            startActivity(intent)


        } else if (cacheListName.size == 1){
            val intent = Intent(this, ActivityWin::class.java)
            val elemento = cacheListName.firstOrNull()
            val champion = elemento ?: ""
            intent.putExtra("champion", champion)
            cacheListName.clear()
            cacheListWins.clear()
            startActivity(intent)

        } else{
            for (par in paresname){
                val tableRow = TableRow(this)
                val btn = Button(this)

                val listparName = ArrayList<String>()



                for (name in par){
                    val textview = TextView(this)
                    textview.text = name
                    listparName.add(name)
                    textview.textSize = 23f
                    val typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                    textview.setTypeface(typeface)
                    textview.setPadding(20,20,20,20)

                    val borderDrawable = GradientDrawable()
                    borderDrawable.shape = GradientDrawable.RECTANGLE
                    borderDrawable.setStroke(3, Color.BLACK)
                    borderDrawable.cornerRadius = 1f

                    textview.background = borderDrawable

                    tableRow.addView(textview)
                }
                tableRow.setPadding(38, 38, 38, 38)
                tableRow.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                val border = View(this)

                val layoutParams = TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT)
                border.setBackgroundColor(Color.BLACK)

                tableRow.addView(border, 1, layoutParams)

                val borderDrawable = GradientDrawable()
                borderDrawable.shape = GradientDrawable.RECTANGLE
                borderDrawable.setStroke(10, Color.BLACK)
                borderDrawable.cornerRadius = 5f
                tableRow.background = borderDrawable


                val borderBtn = GradientDrawable()

                borderBtn.cornerRadius = 30f

                btn.background = borderBtn

                if (par.size != 1){
                    btn.setBackgroundColor(Color.parseColor("#e82e3a"))
                    btn.text = "Battle"
                }else{
                    btn.setBackgroundColor(Color.parseColor("#2e97e8"))
                    btn.text = "Pass"
                }


                val params = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                params.gravity = CENTER
                btn.layoutParams = params
                btn.gravity = CENTER

                btn.setOnClickListener{
                    val intent = Intent(this, ActivityBattle::class.java)
                    intent.putExtra("parde", listparName.toTypedArray())
                    startActivity(intent)
                }

                table.addView(tableRow)
                table.addView(btn)

            }

        }

        btnBack.setOnClickListener{
            finish()
        }

    }
}




