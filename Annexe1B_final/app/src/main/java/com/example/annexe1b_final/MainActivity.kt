package com.example.annexe1b_final

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.MODE_APPEND
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Scanner
import java.util.Vector

class MainActivity : AppCompatActivity() {

    lateinit var champNom : TextView
    lateinit var champSatell : TextView
    lateinit var boutonConfirm : Button

    var vecteurPlanetes = Vector<Planete>() // init le vecteur ici et non dans le OnCreate

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

        updateVecteur(vecteurPlanetes)


//        vecteurPlanetes.add(Planete ("Mercure", 0))
//        vecteurPlanetes.add(Planete ("Venus", 0))
//        vecteurPlanetes.add(Planete ("Terre", 1))
//        vecteurPlanetes.add(Planete ("Mars", 2))
//        vecteurPlanetes.add(Planete ("Jupiter", 95))
//        vecteurPlanetes.add(Planete ("Saturne", 146))
//        vecteurPlanetes.add(Planete ("Uranus", 28))
//        vecteurPlanetes.add(Planete ("Neptune", 14))
    }

    inner class Ecouteur : View.OnClickListener {

        override fun onClick(v: View?) {

            when (v) {

                boutonConfirm -> {
                    addPlanete(vecteurPlanetes)

                    val i = Intent(this@MainActivity, Afficher::class.java)

                    startActivity(i)
                }
            }
        }
    }

    fun updateVecteur(vector : Vector<Planete>) {

        val fis = openFileInput("planete_satell.txt")

        //val fis = getResources().openRawResource(R.raw.planete_satell)

        //val isr = InputStreamReader(fis)

        //val br = BufferedReader(isr)


        fis.use { // br.use

            val scanner = Scanner(fis)

            //val ligne = br.readLine()

            //br.forEachLine { }

            while(scanner.hasNextLine()) {

//                val planete = scanner.nextLine()
//
//                val nbSatell = scanner.nextInt()


                // code trouvé par AI

                val ligne = scanner.nextLine()

                val infos = ligne.trim().split(" ")

                if (infos.size == 2) {

                    val planete = infos[0]

                    val nbSatell = infos[1]

                    vector.add(Planete(planete, (nbSatell).toInt()))
                }

//                while(scanner.hasNext() {
//
//                    var temp = Planete(scanner.next(), scanner.nextInt()) // ou has.next() as Int
//                        vector.add(temp)
//                    })


            }


        }



    }

    fun addPlanete(vector: Vector<Planete>) {

        val nom = champNom.text.toString()
        val nbSatell = champSatell.text.toString()

        if (nom.isNotEmpty() && nbSatell.isNotEmpty()) {

            val fos = openFileOutput("planete-satell.txt", MODE_APPEND) // append ajoute à la suite du contenu déjà là
            val osw = OutputStreamWriter(fos)
            val bw = BufferedWriter(osw)

            val descripPlanete = nom + " " + nbSatell

            bw.write(descripPlanete)

            bw.newLine()

            bw.close()

            champNom.setText("")

            champSatell.setText("")

            finish()


        }

        updateVecteur(vector)
    }



}
