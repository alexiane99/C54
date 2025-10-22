package com.example.tp1

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ObservateurActivity : AppCompatActivity(), ObservateurChangement {


    //var leModele: Sujet? = null

    lateinit var liste : ListView

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

        liste= findViewById(R.id.liste)

        Modele.ajouterObservateur(this) // on ajouter l'observateur ( l'activité ) au modèle ( le sujet ); toujours avant
        Modele.init(applicationContext)




    }

    override fun onStart() {
        super.onStart()

    }

    override fun onDestroy() {
        super.onDestroy()
        Modele.enleverObservateur(this)
    }

    override fun changement(estRempli: Int) {

        remplirListeView()

    }

    fun remplirArrayList() : ArrayList<HashMap<String, Any>> {

        val listeTemp = ArrayList<HashMap<String, Any>>()

        val listeChansons = Modele.playlist.listeMusique
        for(i in 0 ..listeChansons.size - 1) {

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

        val adapt = SimpleAdapter(this, p, R.layout.titre_playlist, arrayOf("title", "artist", "album"),
            intArrayOf(R.id.titreTexte, R.id.artisteText, R.id.albumText))

        // le lier au listview
        liste.adapter = adapt

        // inner class MonAdapter(context: Context, data: List<Map<String, Any>>, resource: Int, from: Array<String>, to: IntArray) : SimpleAdapter(context, data, resource, from, to)
    }

//    class Glide {
//
//        open fun setViewImage( v: ImageView, value : String)  {
//
//            Glide.with(this).load(value).into(v)
//
//    Glide.with(fragment)
//    .load(url)
//    .into(imageView);
//        }
//
//    }
}