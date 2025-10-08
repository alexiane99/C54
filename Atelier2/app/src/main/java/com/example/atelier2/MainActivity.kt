package com.example.atelier2

import android.media.browse.MediaBrowser
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.atelier2.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var playerview: PlayerView // lateinit pour les composants graphiques

    var player : ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        playerview = findViewById(R.id.player_view)

        player = ExoPlayer.Builder(this@MainActivity).build()
    }

    override fun onStart() {
        super.onStart()

        // Bind the player to the view
        playerview.player = player

        val mp3url = "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3"

        // Build the media item
        val mediaItem = MediaItem.fromUri(mp3url)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()


//        var onPlay : Boolean = false
//
//        playerview.setOnClickListener {
//
//            if(!onPlay) {
//                player?.play()
//                onPlay = true
//            }
//
//            player?.pause()
//        }

    }



}