package com.example.annexe3b

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var seekBsonnerie : SeekBar
    lateinit var seekBmedia : SeekBar
    lateinit var seekBnotif : SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        seekBsonnerie = findViewById(R.id.seekBsonnerie);
        seekBmedia = findViewById(R.id.seekBmedia);
        seekBnotif = findViewById(R.id.seekBnotif);


        val ec = Ecouteur()

        seekBsonnerie.setOnSeekBarChangeListener(ec)
        seekBmedia.setOnSeekBarChangeListener(ec)
        seekBnotif.setOnSeekBarChangeListener(ec)

        var sonnerie = 0
        var media = 0
        var notif = 0



    }

    inner class Ecouteur : OnSeekBarChangeListener {

        override fun OnSeekBarChangeListener(v: View?) {

            if (v == seekBsonnerie) {
                
            }

            if (v == seekBmedia) {

            }

            if (v == seekBnotif) {

            }
        }

    }
    @Override void onProgressChanged()

    fun createVolume() {





    }
}