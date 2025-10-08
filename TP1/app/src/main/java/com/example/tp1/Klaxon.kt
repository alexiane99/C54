package com.example.tp1

import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class Klaxon {

    fun lireAPI(url : String) {

        val stringRequest = StringRequest(

            Request.Method.GET, url,
            {
                response ->
                    val li: ListeMusique =
                        Klaxon().parse<ListeMusique>(response) ?: ListeMusique() // pour se débarrasser du ?

                //Toast.makeText(this@Klaxon, "Response ${li.listeMusique.size}", LENGTH_LONG).show()
            },
            {
                //Toast.makeText(this, "erreur", Toast.LENGTH_LONG).show()
            }
        )


    }
}