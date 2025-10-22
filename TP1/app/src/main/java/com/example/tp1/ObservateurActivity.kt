package com.example.tp1

import android.os.Bundle
import android.view.View
import android.widget.SimpleAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.google.common.collect.Queues
import java.util.Queue

class ObservateurActivity : AppCompatActivity(), ObservateurChangement {

    var leModele: Sujet? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById<View>(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        leModele = Modele()
        (leModele as Modele).ajouterObservateur(this) // on ajouter l'observateur ( l'activité ) au modèle ( le sujet )
    }

    override fun onDestroy() {
        super.onDestroy()
        leModele!!.enleverObservateur(this)
    }

    override fun changement(estRempli: Int) {




    }

    fun decomposerReponse(li : Queue<String>) {

//        val remplir = ArrayList<HashMap<String, Chanson>>()
//
//        for(i in 0 ..li.size) {
//
//            // java style
//            var temp = HashMap<String, Chanson>()
//            temp.put("id", li.add("id").toString())

//            val id : String,
//            val title :String,
//            val album: String,
//            val artist: String,
//            val genre : String,
//            val source: String,
//            val image: String,
//            val trackNumber : Int,
//            val totalTrackCount: Int,
//            val duration : Int,
//            val site : String){
            }
//            remplir.add(temp)
        //}

//        val adapt = SimpleAdapter(this, remplir,R.layout.accessoires, arrayOf("nom", "prix"),intArrayOf(R.id.texteNom, R.id.textePrix) )
//
//        // le lier au listview
//        liste.adapter = adapt
   // }
}