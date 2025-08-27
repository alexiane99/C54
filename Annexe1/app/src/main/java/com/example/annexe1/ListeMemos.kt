package com.example.annexe1

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

class ListeMemos : AppCompatActivity() {

    lateinit var liste : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_liste_memos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.liste)

        liste.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, lireMemos()))


    }

    fun lireMemos() : ArrayList<String>
    {
        // flux de données

        val fis = openFileInput("fichier.txt")

        val isr = InputStreamReader(fis)

        val br = BufferedReader(isr)

        val arrayliste = ArrayList<String>()

        var ligne = br.readLine()

        while(ligne != null) {

            arrayliste.add( ligne )
            ligne = br.readLine()
        }

        br.close()
        return arrayliste // longue méthode, peut être fait en 2 lignes



    }
}