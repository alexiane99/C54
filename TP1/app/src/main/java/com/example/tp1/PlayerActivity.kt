package com.example.tp1

import android.icu.text.DecimalFormat
import android.icu.text.MessageFormat.format
import android.icu.text.NumberFormat
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.media3.ui.TimeBar

class PlayerActivity : AppCompatActivity() {

    //private lateinit var binding: PlayerActivityBinding

    lateinit var playerview : PlayerView
    lateinit var parent: LinearLayout
    lateinit var title : TextView
    lateinit var artiste : TextView
    lateinit var boutonPlay : ImageView
    lateinit var boutonPause : ImageView
    lateinit var boutonPrevious : ImageView
    lateinit var boutonNext : ImageView
    lateinit var start: TextView
    lateinit var end : TextView
    lateinit var seekBar: SeekBar

    var chanson : Chanson? = null
    var liste : ListeMusique = Modele.playlist
    var player : ExoPlayer? = null
    var url : String? = null
    var updateSecondes : MyTimer? = null
    var sl : SeekListener? = null

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
        start = findViewById(R.id.start)
        end = findViewById(R.id.end)
        seekBar = findViewById(R.id.seekBar)

        title.text = chanson?.title
        artiste.text = chanson?.artist


        // initialiser les écouteurs d'événements
        val ec = Ecouteur()

        boutonPlay.setOnClickListener(ec)
        boutonPause.setOnClickListener(ec)
        boutonPrevious.setOnClickListener(ec)
        boutonNext.setOnClickListener(ec)

        sl = SeekListener()
        seekBar.setOnSeekBarChangeListener(sl)


//        for(enfant in parent) {
//
//            if(enfant is ImageView) {
//
//                enfant.setOnClickListener(ec)
//
//            }
//        }

    }

    inner class SeekListener : OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            seekBar!!.progress = progress
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            TODO("Not yet implemented")
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            TODO("Not yet implemented")
        }

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

    inner class MyTimer(millisUntilFinished: Long, interval:Long) : CountDownTimer(millisUntilFinished, interval) {

//        var counter : Int = 0

        override fun onTick(millisUntilFinished: Long) {

            val currentPos = player!!.currentPosition
            val duration = player!!.duration

            if (duration > 0) { // si on est avant la fin de la chanson

                seekBar.max = (duration / 1000).toInt()
                seekBar.progress = (currentPos / 1000).toInt()

            }

           // counter = counter!!.plus(1)

            //sl!!.onProgressChanged(seekBar, counter, true)

            val nf : NumberFormat
            nf = DecimalFormat("00")
            val min = (millisUntilFinished / 60000) % 60
            val sec = (millisUntilFinished / 1000) % 60
            end.text = nf.format(min) + ":" + nf.format(sec)

            end.text = currentPos.toString()
        }


//        // Mettre à jour les labels de temps
//        start.text = formatTime(currentPosition)
//        end.text = formatTime(duration)
//    }
//
//    override fun onFinish() {
//        start.text = "00:00"
//        end.text = "00:00"
//        seekBar.progress = 0
//    }

//    // Format mm:ss
//    private fun formatTime(ms: Long): String {
//        val totalSeconds = ms / 1000
//        val minutes = totalSeconds / 60
//        val seconds = totalSeconds % 60
//        return String.format("%02d:%02d", minutes, seconds)
   // }
//        fun get_counter() : Int {
//
//            return counter!!
//        }

        override fun onFinish() {

            //end.text = ("00:00")
        }

        // Mettre à jour la SeekBar
//        if (duration > 0) {
//            seekBar.max = (duration / 1000).toInt()
//            seekBar.progress = (currentPosition / 1000).toInt()
//        }
//
//        // Mettre à jour les TextViews start / end
//        start.text = formatTime(currentPosition)
//        end.text = formatTime(duration)

    }

    override fun onStart() {
        super.onStart()

        playerview.player = player

        val mediaItem = MediaItem.fromUri(url!!)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()

        updateSecondes = MyTimer(chanson!!.duration.toLong(), 1000)
        updateSecondes!!.start()
        //seekBar.progress = updateSecondes

        // max length duration / 1000

        init_playlist()


    }

    fun init_playlist() {

        for(i in 0 ..liste.listeMusique.size - 1) {

            val mediaItem = MediaItem.fromUri(liste.listeMusique.get(i).source)

            player?.addMediaItem(mediaItem)
        }

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
