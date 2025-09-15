package com.example.annexe1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileNotFoundException

class ListeActivity : AppCompatActivity() {

    lateinit var liste : ListView
    lateinit var boutonRetour: Button

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
        boutonRetour = findViewById(R.id.boutonRetour)

        //liste.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, lireMemos()!!))

        liste.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lireMemos()!!)

//        boutonRetour.setOnClickListener {
//
//            finish()
//        }
    }

//    fun lireMemos() : ArrayList<String>
//    {
        // flux de données

//        val fis = openFileInput("fichier.txt")
//
//        val isr = InputStreamReader(fis)
//
//        val br = BufferedReader(isr)


//        br.use {
//
//            var a = ArrayList<String>(
//
//            )
//            a = br.readLines() as ArrayList<String> // transtypage
//        }

//}


    fun lireMemos() : ArrayList<String>? {

        //var v : ArrayList<Memo>? = null
        var triee : ArrayList<String>? = null

        try {
            var v = SingletonMemos.recupererListe(this@ListeActivity) // qui vient du singleton

            v.sortWith(compareBy{it.echeance}) // fonction de haut niveau car prend en parametre une lambda; trie en fonction de l'échéance
            triee = ArrayList<String>() // liste de String vide
            for (memo in v) // pour chaque memo dans la liste
                triee.add(memo.message + " " + memo.echeance) // ajoute seulement les messages des memos

        }
        catch(f:FileNotFoundException)
        {

            Toast.makeText(this@ListeActivity, "pas de fichier de sérialisarion", Toast.LENGTH_SHORT).show() // pour la durée
            finish()
        }

        return triee

    }


}