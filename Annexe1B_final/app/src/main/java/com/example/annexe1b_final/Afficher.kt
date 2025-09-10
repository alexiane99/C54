package com.example.annexe1b_final

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.InputStreamReader

class Afficher : AppCompatActivity() {

    lateinit var liste : ListView
    lateinit var boutonRetour: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_afficher)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.liste)
        boutonRetour = findViewById(R.id.boutonRetour)

        liste.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, lireVecteur()))

        boutonRetour.setOnClickListener {

            finish()
        }

    }

    fun lireVecteur() : ArrayList<String> {

        val fis = openFileInput("planete_satell.txt")

        val isr = InputStreamReader(fis)

        val br = BufferedReader(isr)

        val a : ArrayList<String>

        br.use {

            a = br.readLines() as ArrayList<String>
        }

        return a

    }
}