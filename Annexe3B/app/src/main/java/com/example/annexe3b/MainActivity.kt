package com.example.annexe3b

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {


    // si émulateur ne fonctionne pas, mettre nouveau Pixel 4, API 34 car 36 pas supporté
    lateinit var seekBsonnerie : SeekBar
    lateinit var seekBmedia : SeekBar
    lateinit var seekBnotif : SeekBar

    var volume:Volume? = null // type est Volume ou null

    // peut aussi être créé dans le fichier onCreate

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

        deSerializer(this@MainActivity)


        //val ec = Ecouteur()

//        seekBsonnerie.setOnSeekBarChangeListener(ec)
//        seekBmedia.setOnSeekBarChangeListener(ec)
//        seekBnotif.setOnSeekBarChangeListener(ec)



    }

//    inner class Ecouteur : OnSeekBarChangeListener {
//
//        fun OnSeekBarChangeListener(v: View?) {
//
//            if (v == seekBsonnerie) {
//
//            }
//
//            if (v == seekBmedia) {
//
//            }
//
//            if (v == seekBnotif) {
//
//            }
//        }

//    }

    fun serialiserVolume(contexte: Context) : Volume {

        val fos = contexte.openFileOutput("serialisation.ser", Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)

        var v = Volume(seekBsonnerie.progress, seekBmedia.progress, seekBnotif.progress)

        oos.use{

            // on met volume directement

            oos.writeObject(Volume(seekBsonnerie.progress, seekBmedia.progress, seekBnotif.progress))
        }

        return v;
    }

    fun deSerializer(contexte: Context) {

        try {
            val fis = contexte.openFileInput("serialisation.ser")
            val ois = ObjectInputStream(fis)
            ois.use {
                val v = ois.readObject() as Volume

                // on met volume directement volume!!.sonnerie (!! --> certain qu'il n'est pas nul)

                seekBsonnerie.progress = v.sonnerie

                seekBmedia.progress = v.media

                seekBnotif.progress = v.notif
            }
        }
        catch (fnfe: FileNotFoundException) {

            Toast.makeText(this@MainActivity, "Il n'y a pas de fichier de sérialisation", Toast.LENGTH_LONG).show()

            // peut init à 50 par défaut
        }

    }

    override fun onStop() { // Ctrl + O pour générer le onStop

        //serialiserVolume(this@MainActivity)

        super.onStop()

        try {
            serialiserVolume(this@MainActivity) // écrire ici la méthode dans le try catch
        }
        catch (io:IOException) {

            io.printStackTrace()
        }
    }
}