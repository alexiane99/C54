package com.example.tp1

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.DecimalFormat
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
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.Listener
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

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
    lateinit var boutonForward : ImageView
    lateinit var boutonBack : ImageView

    var chanson : Chanson? = null
    var indexChanson : Int = 0
    var liste : ListeMusique = Modele.playlist
    var player : ExoPlayer? = null
    var url : String? = null
    var updateSecondes : MyTimer? = null
    var sl : SeekListener? = null

    val lanceur: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), CallBackInfosChanson())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupérer le intent de la playlist
        indexChanson = intent.getIntExtra("indexChanson", 0)

        // Initialiser le player
        player = ExoPlayer.Builder(this@PlayerActivity).build()


        // Initialiser la vue

        //binding = PlayerActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.chanson) //(binding.root) #pour afficher la page

        playerview = findViewById(R.id.player_view)
        playerview.setUseController(false); // on annule l'utilisation des boutons
        playerview.isClickable = true

        parent = findViewById(R.id.chanson)
        title = findViewById(R.id.texteTitre)
        artiste = findViewById(R.id.texteArtiste)

        boutonPlay = findViewById(R.id.boutonPlay)
        boutonPause = findViewById(R.id.boutonPause)
        boutonPrevious = findViewById(R.id.boutonPrevious)
        boutonNext = findViewById(R.id.boutonNext)
        boutonForward = findViewById(R.id.boutonForward)
        boutonBack = findViewById(R.id.boutonBackward)
        start = findViewById(R.id.start)
        end = findViewById(R.id.end)
        seekBar = findViewById(R.id.seekBar)

        // Initialiser la première chanson à l'écoute
        chanson = liste.listeMusique[indexChanson]


        // initialiser les écouteurs d'événements
        val ec = Ecouteur()

        boutonPlay.setOnClickListener(ec)
        boutonPause.setOnClickListener(ec)
        boutonPrevious.setOnClickListener(ec)
        boutonNext.setOnClickListener(ec)
        boutonForward.setOnClickListener(ec)
        boutonBack.setOnClickListener(ec)
        playerview.setOnClickListener(ec)

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

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }

    }

    inner class Ecouteur : OnClickListener {

        override fun onClick(v: View?) {

            println(player?.duration)
            println(player?.currentPosition)

            if(v == boutonPlay) {
                player?.play()

                Toast.makeText(this@PlayerActivity, "Play!", LENGTH_LONG).show()
            }

            else if(v == boutonPause) {
                player?.pause()

                Toast.makeText(this@PlayerActivity, "Pause!", LENGTH_LONG).show()
            }

            else if(v == boutonNext) {

                if(player!!.hasNextMediaItem()) {

                    player!!.seekToNextMediaItem()
                    updateInfosChanson()

                }

                Toast.makeText(this@PlayerActivity, "Next!", LENGTH_LONG).show()

            }

            else if(v == boutonPrevious) {

                if(player!!.hasPreviousMediaItem()) {

                    player!!.seekToPreviousMediaItem()
                    updateInfosChanson()

                }

                Toast.makeText(this@PlayerActivity, "Previous!", LENGTH_LONG).show()
            }

            else if(v == boutonBack) {

                var updatePos = player!!.currentPosition - 10000

                if(updatePos < 0) {
                    updatePos = 0 // si on recule trop, on met la position au début
                }
                player!!.seekTo(updatePos)

                Toast.makeText(this@PlayerActivity, "Back 10 sec", LENGTH_LONG).show()

            }
            else if(v == boutonForward) {

                var updatePos = player!!.currentPosition + 10000

                if(updatePos > player!!.duration) {

                    updatePos = player!!.duration // si le temps dépasse la chanson, on met à la fin de la chanson
                }
                player!!.seekTo(updatePos)

                Toast.makeText(this@PlayerActivity, "Plus 10 sec", LENGTH_LONG).show()

            }
            else if(v == playerview) {
                val i : Intent = Intent(this@PlayerActivity, InfosActivity::class.java)
                val indexCurrent =  player!!.currentMediaItemIndex
                i.putExtra("indexCurrent", indexCurrent)
                lanceur.launch(i)
            }
        }

    }
    inner class CallBackInfosChanson() : ActivityResultCallback<ActivityResult> {

        override fun onActivityResult(result: ActivityResult) {

            if(result.resultCode == RESULT_OK) {

                val intentRetour = result.data
            }
        }

    }

    inner class MyTimer(millisUntilFinished: Long, interval:Long) : CountDownTimer(millisUntilFinished, interval) {

        var onPause = false

        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {

            if(!onPause) {

                var currentPos = player!!.currentPosition
                var duration = player!!.duration

                println(currentPos)
                println(duration)

                if (duration > 0 && currentPos >= 0) { // si on est avant la fin de la chanson

                    seekBar.max = millisToSec(duration)
                    seekBar.progress = millisToSec(currentPos)

                    var temps_restant = duration - currentPos

                    end.text = format_timer(temps_restant)
                    start.text = format_timer(currentPos)

                }
            }
        }
        fun setPause() {
            onPause = true
        }
        fun setPlay() {

            onPause = false
        }

        // Convertir les millisecondes en secondes
        fun millisToSec(milisec : Long) : Int {

            return (milisec/1000).toInt()

        }
        // convertir le temps en format String
        fun format_timer(temps : Long) : String {

            val nf : NumberFormat
            nf = DecimalFormat("00")

            val min = (temps / 60000) % 60
            val sec = (temps / 1000) % 60

            return "${nf.format(min)}:${nf.format((sec))}"
        }

//    Format mm:ss
//    private fun formatTime(ms: Long): String {
//        val totalSeconds = ms / 1000
//        val minutes = totalSeconds / 60
//        val seconds = totalSeconds % 60
//        return String.format("%02d:%02d", minutes, seconds)
//        }

//        fun get_counter() : Int {
//
//            return counter!!
//        }

        override fun onFinish() {

            end.text = "00:00"
            seekBar.progress = 0
        }

    }

    override fun onStart() {
        super.onStart()

        playerview.player = player

        init_playlist()

        player?.seekToDefaultPosition(indexChanson)
        player?.prepare()
        player?.play()

        updateInfosChanson()

        player?.addListener(
            object : Listener {

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    if(playbackState == Player.STATE_READY) {

                        if (updateSecondes == null) {
                            updateSecondes = MyTimer(Long.MAX_VALUE, 1000)
                            updateSecondes!!.start()
                        }
                    }
                    if (playbackState == Player.STATE_ENDED) {

                        updateSecondes!!.onFinish()
                    }
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    if(isPlaying) {

                        updateSecondes?.setPlay()
                    }
                    else {
                        updateSecondes?.setPause()
                    }
                }
            }
        )

    }

    private fun init_playlist() {

        for(i in 0 ..liste.listeMusique.size - 1) {

            val mediaItem = MediaItem.fromUri(liste.listeMusique.get(i).source)

            player?.addMediaItem(mediaItem)
        }

    }
    fun updateInfosChanson() {

        val indexCurrent = player?.currentMediaItemIndex ?: 0
        chanson = liste.listeMusique[indexCurrent]

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
