package com.example.tp1

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

object Modele: Sujet { // Singleton object

    private var valeur = 0
    private var obs: ObservateurChangement? = null // c'est un observateur, il pourrait en avoir plusieurs

    var playlist : ListeMusique = ListeMusique()


    val url = "https://api.jsonbin.io/v3/b/680a6a1d8561e97a5006b822?meta=false"

    // constructeur unique

   fun init(context: Context) {

        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest( // stringRequest?

            Request.Method.GET, url,
            { response ->

                val li: ListeMusique =
                    Klaxon().parse<ListeMusique>(response) ?: ListeMusique() // pour se débarrasser du ?

                Toast.makeText(context, "Response ${li.listeMusique.size}", LENGTH_LONG).show()

                println(li.listeMusique.size)

                playlist = li


                avertirObservateurs()
            },
            {
                Toast.makeText(context, "erreur", LENGTH_LONG).show()
            }
        )

        queue.add(stringRequest) // ne pas oublier d'ajouter la requête



    }


    fun getValeur(): Int {
        return valeur
    }


    fun setValeur(valeur: Int) {
        this.valeur = valeur
        //changement de l'état du sujet, avertir les observateurs

    }


    // méthodes de l'interface Sujet
    override fun ajouterObservateur(obs: ObservateurChangement) {
        this.obs = obs
    }

    override fun enleverObservateur(obs: ObservateurChangement) {

        this.obs = null // il y en a un seul
    }

    override fun avertirObservateurs() {
        obs!!.changement(1) // important
    }


}