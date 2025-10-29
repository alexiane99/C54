package com.example.tp1

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.tp1.databinding.PlayerActivityBinding

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: PlayerActivityBinding

    lateinit var playerview : PlayerView
    lateinit var parent: LinearLayout
    lateinit var title : TextView
    lateinit var artiste : TextView
    lateinit var boutonPlay : ImageView
    lateinit var boutonPause : ImageView
    lateinit var boutonPrevious : ImageView
    lateinit var boutonNext : ImageView

    var chanson : Chanson? = null
    var liste : ListeMusique = Modele.playlist
    var player : ExoPlayer? = null
    var url : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chanson = intent.getParcelableExtra<Chanson>("chanson")

        url = chanson!!.source

        //binding = PlayerActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.chanson) //(binding.root) #pour afficher la page

        playerview = findViewById(R.id.player_view)

        playerview.setUseController(false); // on annule l'utilisation des boutons

        player = ExoPlayer.Builder(this@PlayerActivity).build()

        parent = findViewById(R.id.chanson)
        title = findViewById(R.id.texteTitre)
        artiste = findViewById(R.id.texteArtiste)

        boutonPlay = findViewById(R.id.boutonPlay)
        boutonPause = findViewById(R.id.boutonPause)
        boutonPrevious = findViewById(R.id.boutonPrevious)
        boutonNext = findViewById(R.id.boutonNext)

        title.text = chanson?.title
        artiste.text = chanson?.artist


        // initialiser les écouteurs d'événements
        val ec = Ecouteur()

        boutonPlay.setOnClickListener(ec)
        boutonPause.setOnClickListener(ec)
        boutonPrevious.setOnClickListener(ec)
        boutonNext.setOnClickListener(ec)


//        for(enfant in parent) {
//
//            if(enfant is ImageView) {
//
//                enfant.setOnClickListener(ec)
//
//            }
//        }

    }

    inner class Ecouteur : OnClickListener {

        override fun onClick(v: View?) {

            if(v == findViewById(R.id.boutonPlay)) {
                player?.play()

                Toast.makeText(this@PlayerActivity, "Play!", LENGTH_LONG).show()
            }

            else if(v == findViewById(R.id.boutonPause)) {
                player?.pause()

                Toast.makeText(this@PlayerActivity, "Pause!", LENGTH_LONG).show()
            }

            else if(v == findViewById(R.id.boutonNext)) {

                if(player!!.hasNextMediaItem()) {

                    player!!.seekToNextMediaItem()

                }

                Toast.makeText(this@PlayerActivity, "Next!", LENGTH_LONG).show()

            }

            else if(v == (findViewById(R.id.boutonPrevious))) {

                if(player!!.hasPreviousMediaItem()) {

                    player!!.seekToPreviousMediaItem()

                }

                Toast.makeText(this@PlayerActivity, "Previous!", LENGTH_LONG).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()

        playerview.player = player

        val mediaItem = MediaItem.fromUri(url!!)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()

        init_playlist()

    }

    fun init_playlist() {

        for(i in 0 ..liste.listeMusique.size - 1) {

            val mediaItem = MediaItem.fromUri(liste.listeMusique.get(i).source)

            player?.addMediaItem(mediaItem)
        }

    }

    fun setChanson(url : String) {

        val mediaItem = MediaItem.fromUri(url)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()

        title.text = chanson!!.title
        artiste.text = chanson!!.artist

    }
    override fun onStop() {
        super.onStop()
        player?.release()
    }




}

//ACTION -- MÉTHODE
// Charger un média	player.setMediaItem(MediaItem.fromUri(uri))
//Démarrer la lecture	player.play()
//Mettre en pause	player.pause()
//Aller à un instant précis	player.seekTo(positionMs)
//Lire la position actuelle	player.currentPosition
//Lire la durée totale	player.duration
//Avancer de quelques secondes	player.seekForward()
//Reculer de quelques secondes	player.seekBack()
//Volume	player.volume = 0.5f
//Vitesse	player.setPlaybackSpeed(1.5f)
