package com.example.tp1

import android.annotation.SuppressLint
import android.content.Context
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
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class PlayerActivity : AppCompatActivity() {

    //private lateinit var binding: PlayerActivityBinding
    lateinit var playerview : PlayerView
    lateinit var parent: LinearLayout
    var title : TextView?= null
    var artiste : TextView?= null
    var boutonPlay : ImageView?= null
    var boutonPause : ImageView?= null
    var boutonPrevious : ImageView?= null
    var boutonNext : ImageView?= null
    var start: TextView?= null
    var end : TextView?= null
    var seekBar: SeekBar?= null
    var boutonForward : ImageView?= null
    var boutonBack : ImageView?= null

    var chanson : Chanson? = null
    var indexChanson : Int = 0
    var liste : ListeMusique = Modele.playlist
    var player : ExoPlayer? = null
    var updateSecondes : MyTimer? = null
    var sl : SeekListener? = null

    val lanceur: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), CallBackInfosChanson())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupérer le intent de la playlist
        indexChanson = intent.getIntExtra("indexChanson", 0)

        // Initialiser le player
        if(player == null) {

            player = ExoPlayer.Builder(this@PlayerActivity).build()
        }

        // Initialiser la vue

        //binding = PlayerActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.chanson) //(binding.root) #pour afficher la page

        playerview = findViewById(R.id.player_view)
        playerview.setUseController(false); // on annule l'utilisation des boutons
        playerview.isClickable = true

        parent = findViewById(R.id.chanson)
        title = findViewById(R.id.texteTitre)
        artiste = findViewById(R.id.texteArtiste)

        boutonPlay = parent.findViewById<ImageView>(R.id.boutonPlay)
        boutonPause = parent.findViewById<ImageView>(R.id.boutonPause)
        boutonPrevious = parent.findViewById<ImageView>(R.id.boutonPrevious)
        boutonNext = parent.findViewById<ImageView>(R.id.boutonNext)
        boutonForward = parent.findViewById<ImageView>(R.id.boutonForward)
        boutonBack = parent.findViewById<ImageView>(R.id.boutonBackward)

        start = parent.findViewById<TextView>(R.id.start)
        end = parent.findViewById<TextView>(R.id.end)
        seekBar = parent.findViewById<SeekBar>(R.id.seekBar)

        // Initialiser la première chanson à l'écoute
        chanson = liste.listeMusique[indexChanson]

        // initialiser les écouteurs d'événements
        val ec = Ecouteur()

        boutonPlay?.setOnClickListener(ec)
        boutonPause?.setOnClickListener(ec)
        boutonPrevious?.setOnClickListener(ec)
        boutonNext?.setOnClickListener(ec)
        boutonForward?.setOnClickListener(ec)
        boutonBack?.setOnClickListener(ec)
        playerview.setOnClickListener(ec)

        sl = SeekListener()
        seekBar?.setOnSeekBarChangeListener(sl)

//        for(enfant in parent) {
//
//            if(enfant is ImageView) {
//
//                enfant.setOnClickListener(ec)
//
    }

    fun serializerProgression(context: Context)   {
        try {
            val fos = context.openFileOutput("serialisation.ser", Context.MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)

            oos.use {

                oos.writeObject(Progression(player!!.currentPosition, seekBar!!.progress))
            }
        }
        catch(io:IOException) {

            io.printStackTrace()
        }
    }

    fun deserializerProgression(context: Context) {
        try {
            val fis = context.openFileInput("serialisation.ser")
            val ois = ObjectInputStream(fis)

            ois.use {
                val p = ois.readObject() as Progression

                player!!.seekTo(p.progress)
                seekBar!!.progress = p.seekBarProgress
                player!!.play()
            }
        }
        catch (fnfe: FileNotFoundException) {
            Toast.makeText(this@PlayerActivity, "Il n'y a pas de fichier de sérialisation", Toast.LENGTH_LONG).show()
        }
        catch(e: Exception) {
            e.printStackTrace()
            context.deleteFile("serialisation.ser")
        }
    }

    inner class SeekListener : OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            seekBar!!.progress = progress
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            player?.pause()

        }
        override fun onStopTrackingTouch(seekBar: SeekBar?) {

            var newPos = seekBar!!.progress
            player!!.seekTo((newPos * 1000).toLong())
            player?.play()

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
                updateSecondes!!.setPause()

                var updatePos = player!!.currentPosition - 10000

                if(updatePos < 0) {
                    updatePos = 0 // si on recule trop, on met la position au début
                }
                player!!.seekTo(updatePos)

                playerview.postDelayed({updateSecondes!!.setPlay()}, 500) // ajout pcq lag avec les appels de SeekTo

                Toast.makeText(this@PlayerActivity, "Back 10 sec", LENGTH_LONG).show()
            }
            else if(v == boutonForward) {
                updateSecondes?.setPause()

                var updatePos = player!!.currentPosition + 10000

                if(updatePos > player!!.duration) {

                    updatePos = player!!.duration // si le temps dépasse la chanson, on met à la fin de la chanson
                }
                player!!.seekTo(updatePos)

                playerview.postDelayed({ updateSecondes!!.setPlay() }, 500) // ici aussi


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

                val result = result.data

                val resumeProgress = result?.getBooleanExtra("resumeProgression", false)
                val resumeIndex = result?.getIntExtra("indexChanson", 0)

                if(resumeProgress!!) {

                    indexChanson = resumeIndex!!
                    player?.seekToDefaultPosition(indexChanson)
                    deserializerProgression(this@PlayerActivity)
                    updateInfosChanson()
                }

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

                    seekBar!!.max = millisToSec(duration)
                    seekBar!!.progress = millisToSec(currentPos)

                    var temps_restant = duration - currentPos

                    end!!.text = format_timer(temps_restant)
                    start!!.text = format_timer(currentPos)

                }
            }
        }
        fun setPause() {
            onPause = true
        }
        fun setPlay() {

            onPause = false
        }
        fun millisToSec(milisec : Long) : Int {  // Convertir les millisecondes en secondes

            return (milisec/1000).toInt()
        }
        fun format_timer(temps : Long) : String {  // convertir le temps en format String

            val nf : NumberFormat
            nf = DecimalFormat("00")

            val min = (temps / 60000) % 60
            val sec = (temps / 1000) % 60

            return "${nf.format(min)}:${nf.format((sec))}"
        }
        override fun onFinish() {

            end!!.text = "00:00"
            seekBar!!.progress = 0
        }
    }

    override fun onStart() {
        super.onStart()
        println("onStart")

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
                            updateSecondes = MyTimer(Long.MAX_VALUE, 1500)
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

        if(player?.mediaItemCount == 0) { // si la playlist est vide

            for (i in 0..liste.listeMusique.size - 1) {

                val mediaItem = MediaItem.fromUri(liste.listeMusique.get(i).source)
                player?.addMediaItem(mediaItem)
            }
        }

    }
    fun updateInfosChanson() {

        val indexCurrent = player?.currentMediaItemIndex ?: 0
        chanson = liste.listeMusique[indexCurrent]

        title!!.text = chanson!!.title
        artiste!!.text = chanson!!.artist

    }
    override fun onPause() {
        super.onPause()
        println("onPause")
        player?.pause()
        updateSecondes?.setPause()

        try {
            serializerProgression(this@PlayerActivity) // écrire ici la méthode dans le try catch
        }
        catch (io:IOException) {

            io.printStackTrace()
        }

    }
    override fun onStop() {
        super.onStop()
        println("onStop")

        try {
            serializerProgression(this@PlayerActivity) // écrire ici la méthode dans le try catch
        }
        catch (io:IOException) {

            io.printStackTrace()
        }

    }
    override fun onResume() {
        super.onResume()
        println("onResume")
//        playerview.player = player

        deserializerProgression(this@PlayerActivity)
        updateInfosChanson()

        player?.play()
        updateSecondes?.setPlay()
    }
    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
        player?.release()
        player = null
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
