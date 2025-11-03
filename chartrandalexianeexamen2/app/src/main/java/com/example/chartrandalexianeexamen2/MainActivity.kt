package com.example.chartrandalexianeexamen2

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import com.beust.klaxon.Klaxon
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    val url = "https://api.jsonbin.io/v3/b/6908bbe4d0ea881f40d109b6?meta=false"
    lateinit var playlist : ArrayList<HashMap<String, Any>>
    lateinit var progressBar : ProgressBar
    lateinit var champAlbum : TextView
    lateinit var boutonVerifier : Button
    var listeReponses = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {

        val queue = Volley.newRequestQueue(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById(R.id.progressBar)
        champAlbum = findViewById(R.id.champAlbum)
        boutonVerifier = findViewById(R.id.boutonVerifier)

        val ec = Ecouteur()

        boutonVerifier.setOnClickListener(ec)

        val stringRequest = StringRequest(

            Request.Method.GET, url,
            {
                response ->

                val li: ListeMusique = Klaxon().parse<ListeMusique>(response) ?: ListeMusique()
                Toast.makeText(this, "Les ${li.listeMusique.size} items ont bien été chargés!", LENGTH_LONG).show()
                println(li.listeMusique.size)
                remplirArrayList(li)


//                val li = response.getJSONArray(1)
//                Toast.makeText(this, "Response ${li?.length()}", LENGTH_LONG).show()

            },
            { Toast.makeText(this,"erreur", LENGTH_LONG).show()}
        )

        queue.add(stringRequest)
    }
    fun remplirArrayList(li : ListeMusique) : ArrayList<HashMap<String, Any>> {

        val listeTemp = ArrayList<HashMap<String, Any>>()

        val listeChansons = li

        for (i in 0..listeChansons.listeMusique.size - 1) {

            // java style
            var temp = HashMap<String, Any>()
            temp.put("nom", listeChansons.listeMusique.get(i).nom)

            listeTemp.add(temp)
        }

        playlist = listeTemp

        println(playlist)

        return playlist
    }

    inner class Ecouteur : OnClickListener {

        override fun onClick(v: View?) {

            if(v == boutonVerifier) {

                verifierReponse()
            }
        }

    }
    fun verifierReponse() {

        var response = champAlbum.text.toString()
        var invalide = true
        listeReponses.add(response)

        for(i in 0.. playlist.size - 1) {

            var nom_album = playlist.get(i).get("nom")

            if (response.equals(nom_album)) {

                for(i in 0 .. listeReponses.size - 1) {

                    var verifRep = listeReponses.get(i)

                    if(verifRep.equals(response)) {

                        Toast.makeText(this, "Vous ne pouvez pas réutiliser la même réponse", LENGTH_LONG).show()

                    }
                    else {
                        invalide = false
                        progressBar.progress += 1
                    }
                }

            }
        }

        if(invalide) {
            Toast.makeText(this, "Mauvause réponse", LENGTH_LONG).show()
        }

        champAlbum.text = ""
    }
    override fun onStop() {
        super.onStop()

        listeReponses = ArrayList<String>()
    }
}

