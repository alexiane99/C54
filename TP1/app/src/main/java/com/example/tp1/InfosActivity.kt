package com.example.tp1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.tp1.ObservateurActivity

class InfosActivity : AppCompatActivity() {

    lateinit var boutonRetour : Button
    lateinit var site : TextView
    var chanson : Chanson? = null
    var index : Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.infos_chanson)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.infos_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        index = intent.getIntExtra("indexCurrent", 0)

        chanson = Modele.playlist.listeMusique.get(index)

        var titre = findViewById<TextView>(R.id.infoTitre)
        var artiste = findViewById<TextView>(R.id.infoArtiste)
        var album = findViewById<TextView>(R.id.infoAlbum)
        var genre = findViewById<TextView>(R.id.infoGenre)
        var duration = findViewById<TextView>(R.id.infoDuration)
        var image = findViewById<ImageView>(R.id.albumCover)
        site= findViewById<TextView>(R.id.infoSite)
        boutonRetour = findViewById<Button>(R.id.boutonRetourPlayer)

        setViewImage(image, chanson!!.image)

        titre.text = chanson!!.title
        artiste.text = chanson!!.artist
        album.text = chanson!!.album
        genre.text = chanson!!.genre
        duration.text = format_timer(chanson!!.duration)
        site.text = chanson!!.site

        val ec = Ecouteur()
        boutonRetour.setOnClickListener(ec)
        site.setOnClickListener(ec)

    }

    inner class Ecouteur : OnClickListener {
        override fun onClick(v: View?) {

            if(v == site) {

                val i : Intent = Intent(Intent.ACTION_VIEW, chanson!!.site.toUri())
                startActivity(i)

            }
            if(v == boutonRetour) {

                val retour = Intent()
                retour.putExtra("indexChanson", index)
                retour.putExtra("resumeProgression", true)
                setResult(RESULT_OK, retour)
                finish()

            }
        }

    }
    fun setViewImage( v: ImageView, value : String) : Unit  {

        Glide.with(this@InfosActivity).load(value).into(v)

    }
    fun format_timer(tempsSec : Int) : String {  // convertir le temps en format String

        val nf : NumberFormat
        nf = DecimalFormat("00")

        val min = (tempsSec / 60)
        val sec = (tempsSec) % 60

        return "${nf.format(min)}:${nf.format((sec))}"
    }


}