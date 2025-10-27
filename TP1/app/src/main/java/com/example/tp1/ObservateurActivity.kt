package com.example.tp1

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.example.tp1.Modele.url


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

        val ec = Ecouteur()

        liste.onItemClickListener = ec
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

        val adapt = myAdapter(this, p, R.layout.titre_playlist, arrayOf("title", "artist", "album", "image"),
            intArrayOf(R.id.titreTexte, R.id.artisteText, R.id.albumText, R.id.texteCover))

        // le lier au listview
        liste.adapter = adapt

        // inner class MonAdapter(context: Context, data: List<Map<String, Any>>, resource: Int, from: Array<String>, to: IntArray) : SimpleAdapter(context, data, resource, from, to)
    }

    inner class myAdapter(context : Context, data: MutableList<out MutableMap<String, Any>>, resource: Int, from: Array<String>, to: IntArray) : SimpleAdapter(context, data, resource, from, to) {

        override fun setViewImage( v: ImageView, value : String) : Unit  {


            Glide.with(this@ObservateurActivity).load(value).into(v)

        }

    }

    inner class Ecouteur : OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            val i : Intent = Intent(this@ObservateurActivity, PlayerActivity::class.java) // on va ouvrir le player ici
            startActivity(i);
        }


    }

    open class CountDownTimer() {

       // var updateSecondes = MonTimer(nombre de ms avant la fin du timer, invervalle entre chaque tic)

    }

}