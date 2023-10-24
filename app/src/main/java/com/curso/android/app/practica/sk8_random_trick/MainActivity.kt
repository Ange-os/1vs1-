package com.curso.android.app.practica.sk8_random_trick


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private lateinit var edittext: EditText
    private lateinit var addbtn: Button
    private lateinit var btnDialo: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edittext = findViewById<EditText>(R.id.editTextText)
        addbtn = findViewById<Button>(R.id.buttonadd)

        val listName = ArrayList<String>()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listName)


        addbtn.setOnClickListener {
            val name = edittext.text.toString()
            if (name.isNotEmpty()) {
                listName.add(name)
                adapter.notifyDataSetChanged()
                edittext.text.clear()
            }

        }

        btnDialo = findViewById<Button>(R.id.buttonDialo)

        fun showListDialog() {
            val dialogView = layoutInflater.inflate(R.layout.dialog_list, null)
            val listView = dialogView.findViewById<ListView>(R.id.listView)


            listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listName)


            val dialog = AlertDialog.Builder(this)
                .setTitle("Players")
                .setView(dialogView)
                .setPositiveButton("keys", null)
                .setNegativeButton("cerrar", null)
                .create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener{
                dialog.dismiss()
            }
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener{
                listName.shuffled(random = java.util.Random())
                val intent = Intent(this, ActivityListName::class.java)
                intent.putExtra("nombrs", listName.toTypedArray())
                startActivity(intent)
            }
        }

        btnDialo.setOnClickListener(){
            showListDialog()
        }

    }

}



