package com.example.annexe5

import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var liste : ListView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.liste)

        val adapt = SimpleAdapter(this, remplirArrayList(),
            R.layout.chanson, arrayOf("position", "nom","date", "image"),
            intArrayOf(R.id.textePosition, R.id.textNom, R.id.textDate, R.id.texteCover ))
        // liste --> ArrayList // maps --> hashmap ; chaque item représente un hashmap

        liste.setAdapter(adapt)
    }


    fun remplirArrayList() : ArrayList<HashMap<String, Any>> {

        var listeDonnes = ArrayList<HashMap<String, Any>>()

        var chanson = HashMap<String, Any>()

        //chanson.put("position", 3)
        chanson["position"] = 3

        chanson["nom"] = "Touch Me"
        chanson["date"] = "22/03/86"
        chanson["image"] = R.drawable.touchme

        listeDonnes.add(chanson)

        chanson = HashMap()
        chanson["position"] = 3
        chanson["date"] = "22/03/86"
        chanson["image"] = R.drawable.touchme


        return listeDonnes
    }
}