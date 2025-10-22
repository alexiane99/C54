package com.example.tp1

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ObservateurActivity : AppCompatActivity(), ObservateurChangement {

    var leModele: Sujet? = null

    lateinit var playlistView : ListView

    var p : ArrayList<HashMap<String, Any>> = ArrayList()

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

        playlistView = findViewById(R.id.liste)

    }

    override fun onStart() {
        super.onStart()
        leModele = Modele
        (leModele as Modele).ajouterObservateur(this) // on ajouter l'observateur ( l'activité ) au modèle ( le sujet )
    }

    override fun onDestroy() {
        super.onDestroy()
        leModele!!.enleverObservateur(this)
    }

    override fun changement(estRempli: Int) {

        remplirListeView()

    }

    fun remplirArrayList() : ArrayList<HashMap<String, Any>> {

        val listeTemp = ArrayList<HashMap<String, Any>>()

        val listeChansons = Modele.playlist.listeMusique
        for(i in 0 ..listeChansons.size) {

            // java style
            var temp = HashMap<String, Any>()
            temp.put("id", listeChansons.get(i).id)
            temp.put("title", listeChansons.get(i).title)
            temp.put("album", listeChansons.get(i).album)
            temp.put("artist", listeChansons.get(i).artist)
            temp.put("genre", listeChansons.get(i).genre)
            temp.put("source", listeChansons.get(i).source)
            temp.put("image", listeChansons.get(i).image)
            temp.put("trackNumber", listeChansons.get(i).trackNumber.toString())
            temp.put("totalTrackcount", listeChansons.get(i).totalTrackCount.toString())
            temp.put("duration", listeChansons.get(i).duration.toString())
            temp.put("site", listeChansons.get(i).site)

            listeTemp.add(temp)
        }

        return listeTemp

    }

    fun remplirListeView() {

        p = remplirArrayList()

        val adapt = SimpleAdapter(this, p,R.layout.chanson, arrayOf("title", "artist"),intArrayOf(
            androidx.core.R.id.title, R.id.genre) )

        // le lier au listview
        playlistView.adapter = adapt
    }
}