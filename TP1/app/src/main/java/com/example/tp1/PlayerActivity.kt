package com.example.tp1

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.tp1.databinding.PlayerActivityBinding

import com.google.android.material.bottomnavigation.BottomNavigationView

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: PlayerActivityBinding

    lateinit var playerview : PlayerView
    var player : ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PlayerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerview = findViewById(R.id.player_view)

        player = ExoPlayer.Builder(this@PlayerActivity).build()

        playerview.setUseController(false); // on annule l'utilisation des boutons
    }
}