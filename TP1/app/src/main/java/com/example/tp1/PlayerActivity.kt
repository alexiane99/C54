package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.tp1.databinding.PlayerActivityBinding

import com.google.android.material.bottomnavigation.BottomNavigationView

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: PlayerActivityBinding

    lateinit var playerview : PlayerView
    var player : ExoPlayer? = null
    var url : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chanson = intent.getParcelableExtra<Chanson>("chanson")

        url = chanson!!.source

        binding = PlayerActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.chanson) //(binding.root) #pour afficher la page

        playerview = findViewById(R.id.player_view)

        player = ExoPlayer.Builder(this@PlayerActivity).build()

        playerview.setUseController(false); // on annule l'utilisation des boutons


    }

    override fun onStart() {
        super.onStart()

        playerview.player = player

        val mediaItem = MediaItem.fromUri(url!!)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()

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
