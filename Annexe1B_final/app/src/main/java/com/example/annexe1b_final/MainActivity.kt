package com.example.annexe1b_final

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.MODE_APPEND
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.util.Vector

class MainActivity : AppCompatActivity() {

    lateinit var champNom : TextView
    lateinit var champSatell : TextView
    lateinit var boutonConfirm : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        champNom = findViewById(R.id.champNom)
        champSatell = findViewById(R.id.champSatell)
        boutonConfirm = findViewById(R.id.boutonConfirm)


        val ec = Ecouteur()

        boutonConfirm.setOnClickListener(ec)


        val vecteurPlanetes = Vector<Planete>()

        vecteurPlanetes.add(Planete ("Mercure", 0))
        vecteurPlanetes.add(Planete ("Venus", 0))
        vecteurPlanetes.add(Planete ("Terre", 1))
        vecteurPlanetes.add(Planete ("Mars", 2))
        vecteurPlanetes.add(Planete ("Jupiter", 95))
        vecteurPlanetes.add(Planete ("Saturne", 146))
        vecteurPlanetes.add(Planete ("Uranus", 28))
        vecteurPlanetes.add(Planete ("Neptune", 14))

    }

    inner class Ecouteur : View.OnClickListener {

        override fun onClick(v: View?) {

            when (v) {

                boutonConfirm -> addPlanete()
            }
        }
    }

    fun addPlanete() {

        val nom = champNom.text.toString()

        if (nom.isNotEmpty()) {

            val fos = openFileOutput("fichier.txt", MODE_APPEND) // append ajoute à la suite du contenu déjà là
            val osw = OutputStreamWriter(fos)
            val bw = BufferedWriter(osw)

            bw.write(nom)
            bw.newLine()

            bw.close()

            //champNom.text.clear()

            finish()

        }
    }
}
