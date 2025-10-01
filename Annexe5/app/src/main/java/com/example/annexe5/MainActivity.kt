package com.example.annexe5

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var liste : ListView

    var v: ArrayList<HashMap<String, Any>> = ArrayList()

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

        v = remplirArrayList()

        val adapt = SimpleAdapter(this, v , //remplirArrayList(),
            R.layout.chanson, arrayOf("position", "nom","date", "image"),
            intArrayOf(R.id.textePosition, R.id.textNom, R.id.textDate, R.id.texteCover ))
        // liste --> ArrayList // maps --> hashmap ; chaque item représente un hashmap

        //liste.setAdapter(adapt) // java
        liste.adapter = adapt // kotlin


        val ec = Ecouteur()

        //liste.setOnClickListener(ec)
        //liste.setOnItemClickListener(ec) // java
        liste.onItemClickListener = ec // kotlin


        // autre méthode avec lambda, moins avantageux car doit réutiliser les paramètres

//        liste.setOnItemClickListener { _, view : View?, position: Int, _ ->
//
//            val linearlayout = view as LinearLayout
//            val textview = linearlayout.findViewById<TextView>(R.id.textNom)
//            Toast.makeText(this@MainActivity, textview.text.toString(), Toast.LENGTH_SHORT).show()
//
//        }

    }

    inner class Ecouteur : AdapterView.OnItemClickListener {

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {



//            val selectedSong = v.get(position)
//            val nomChanson = selectedSong["nom"] as String // kotlin style

            var titre = v[position]["nom"].toString()

            Toast.makeText(this@MainActivity, "Vous avez sélectionnée ${titre}", Toast.LENGTH_SHORT).show()

            // on part du paramètre view
//            val linearlayout = view as LinearLayout
//            val textview = linearlayout.findViewById<TextView>(R.id.textNom)
//
//            Toast.makeText(this@MainActivity, textview.text.toString(), Toast.LENGTH_SHORT).show()
        }

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