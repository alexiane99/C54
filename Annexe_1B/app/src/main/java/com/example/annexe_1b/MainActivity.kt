package com.example.annexe_1b

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Scanner

class MainActivity : AppCompatActivity() {

    lateinit var champNom: EditText
    lateinit var nbLine: TextView
    lateinit var nbCaract: TextView
    lateinit var nbC: TextView
    lateinit var nbMot: TextView
    lateinit var boutonGo : Button

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
        nbLine = findViewById(R.id.nbLine)
        nbCaract = findViewById(R.id.nbCaract)
        nbC = findViewById(R.id.nbC)
        nbMot = findViewById(R.id.nbMot)
        boutonGo = findViewById(R.id.boutonGo)

        // initialiser écouteur

        //val ec = Ecouteur()

        //boutonGo.setOnClickListener(ec)

        update()

        boutonGo.setOnClickListener { // on peut aussi écrire directement car lambda

            addNom()
            update()
        }
    }

//    inner class Ecouteur : View.OnClickListener {
//
//        override fun onClick(v: View?) {
//
//            when(v) {
//
//                boutonGo -> {
//                    addNom()
//                    update()
//                }
//            }
//        }
//
//    }


    fun update() { // pour exécuter chacune des fonctions lorsqu'on ouvre le fichier

        countNbLignes()
        countNombreCaract()
        countNombreC()
        countNbMots()
    }

    fun countNbLignes() {

        val fis = openFileInput("fichier.txt")
        val isr = InputStreamReader(fis) // pour lire un flux de caractère
        val br = BufferedReader(isr)

        var nbLignes = 0

        br.use {

            //var ligne = br.readLine()

            //while(ligne != null)

            // for (line in br.lines())
            // br.readLines().size -> compte le nb éléments

            br.forEachLine { nbLignes += 1 }
            // va aussi fermer le br lorsque la tâche est terminée, car fct lambda
        }

        nbLine.text = nbLignes.toString()

    }

    fun countNombreCaract(){

        val fis = openFileInput("fichier.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        var sumCaractLignes = 0

        br.use {

            //var ligne = br.readLine() // String

            br.forEachLine { ligne ->

                //var ligne = br.readLine() // String

                sumCaractLignes += ligne.length
            }

        }

        nbCaract.text = sumCaractLignes.toString()

    }

    fun countNombreC() {

        val fis = openFileInput("fichier.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        var nombreC = 0

        br.use {

            br.forEachLine { ligne ->

                //val ligne = br.readLine() // String

                var i: Int;

//                for (i in 0 <..ligne.length)
//                    if(ligne.elementAt(i)== 'C' || ligne.elementAt(i)== 'c'  )
//                        nombreC += 1

                for (i in 0 until ligne.length) {

                    if (ligne.elementAt(i) == 'c' || ligne.elementAt(i) == 'C') {

                        nombreC += 1
                    }
                }
            }

        }

        nbC.text = nombreC.toString()

    }

    fun addNom() {

        val nom = champNom.text.toString()

        if (nom.isNotEmpty()) {

            val fos = openFileOutput("fichier.txt", MODE_APPEND) // append ajoute à la suite du contenu déjà là
            val osw = OutputStreamWriter(fos) // pour modifier un flux de caractères
            val bw = BufferedWriter(osw)

            bw.write(nom)
            bw.newLine()

            bw.close()

            champNom.text.clear()

            //finish()
        }
    }

    fun countNbMots() {

        // Les scanners utilisent des jetons (ou tokens)
        // caract blanc -> est le delimiter par défaut d'un scanner

        var nbMots = 0

        val fis = openFileInput("fichier.txt")

        fis.use {

            val scanner = Scanner(fis)
            // on n'appelle pas useDelimiter car on veut un caract blanc

            while(scanner.hasNext()) {

                nbMots++
                scanner.next()
            }

            //            val line = scanner.nextLine()
            //            val mots = line.trim().split("\\s+") // n'est pas nécessaire normalement car delimiter est caract blanc par défaut
            //            nbMots = mots.size

        }

        nbMot.text = nbMots.toString()

    }
}