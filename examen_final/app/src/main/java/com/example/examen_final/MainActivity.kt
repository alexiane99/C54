package com.example.examen_final

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import com.beust.klaxon.Klaxon

class MainActivity : AppCompatActivity() {

    val url = "https://api.jsonbin.io/v3/b/6763118eacd3cb34a8bbdcb1?meta=false"

    override fun onCreate(savedInstanceState: Bundle?) {

        val queue = Volley.newRequestQueue(this)

        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val stringRequest = StringRequest( // stringRequest?

            Request.Method.GET, url,
            { response ->
                val li: ListeCadeaux = Klaxon().parse<ListeCadeaux>(response) ?: ListeCadeaux()

                decomposerReponse(li)

                Toast.makeText(this, "${li.listeCadeaux.size}", LENGTH_LONG).show()

            },
            {
                Toast.makeText(this, "erreur", LENGTH_LONG).show()
            }
        )

        queue.add(stringRequest)

    }

    fun decomposerReponse(li: ListeCadeaux) : ArrayList<HashMap<String, Any>> {

        val listTemp = ArrayList<HashMap<String, Any>>()

        val listeCadeaux = li

        for (i in 0.rangeTo(listeCadeaux.listeCadeaux.size - 1)) {

            var temp = HashMap<String, Any>()
            temp.put("nom", listeCadeaux.listeCadeaux.get(i).nom)
            temp.put("nom", listeCadeaux.listeCadeaux.get(i).provenance)
            listTemp.add(temp)
        }

        return listTemp
    }

    // getStats

    // if provenance == Papa, count += 1 ..... pour les 3

}