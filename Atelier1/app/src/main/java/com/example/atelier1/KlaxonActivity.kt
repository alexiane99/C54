package com.example.atelier1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.SimpleAdapter
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class KlaxonActivity : AppCompatActivity() {

    lateinit var liste: ListView
    val url = "https://api.jsonbin.io/v3/b/67fe6a908a456b796689f63d?meta=false"

    override fun onCreate(savedInstanceState: Bundle?) {

        val queue = Volley.newRequestQueue(this)

        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_klaxon)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.liste)
        val ec = Ecouteur()

        liste.onItemClickListener = ec

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val li: ListeProduits =
                    Klaxon().parse<ListeProduits>(response)
                        ?: ListeProduits() // se debarrasser du ?
                decomposerReponse(li)
                Toast.makeText(this@KlaxonActivity, "Response ${li.articles.size}", LENGTH_LONG)
                    .show()
                // decomposerReponse()
            },
            { Toast.makeText(this, "erreur", Toast.LENGTH_LONG).show() })

        // reste à prendre la liste et la convertir dans un simple adapter

        queue.add(stringRequest) // ne pas oublier d'ajouter la requête
    }

    fun decomposerReponse(li: ListeProduits): ArrayList<HashMap<String, Any>> {

        val listTemp = ArrayList<HashMap<String, Any>>()

        val listeProducts = li

        for (i in 0.rangeTo(listeProducts.articles.size - 1)) {
//        for(i in 0 ≤ ... ≤ listeProducts.articles.size - 1) {

            var temp = HashMap<String, Any>()
            temp.put("nom", listeProducts.articles.get(i).nom)
            temp.put("prix", listeProducts.articles.get(i).prix)
            listTemp.add(temp)
        }

        val adapt = SimpleAdapter(this, listTemp, R.id.main_klaxon, arrayOf("nom", "prix"))
    }

    inner class Ecouteur : AdapterView.OnItemClickListener {

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        }

    }

    fun decomposerReponse(): Unit { // en partant de liste activity

    }
}