package com.example.annexe_1b

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.annexe_1b.ui.theme.Annexe_1BTheme
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Scanner

class MainActivity : AppCompatActivity() {

    private lateinit var champNom : EditText
    private lateinit var nbLines : TextView
    private lateinit var nbCaract : TextView
    private lateinit var nbC : TextView
    private lateinit var nbMot : TextView

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
        nbLines = findViewById(R.id.nbLine)
        nbCaract = findViewById(R.id.nbCaract)
        nbC = findViewById(R.id.nbC)
        nbMot = findViewById(R.id.nbMot)

        // initialiser écouteur

        val ec = Ecouteur()

        champNom.setOnClickListener(ec)
    }

    inner class Ecouteur : View.OnClickListener {

        override fun onClick(v:View?) {

            when(v) {

                champNom -> addNom()
            }
        }

    }

    private fun countNbLignes() : TextView {

        val fis = openFileInput("fichier.txt")

        val isr = InputStreamReader(fis)

        val br = BufferedReader(isr)

        var nbLignes = 0

        br.use {

            //var ligne = br.readLine()

            //while(ligne != null)

                // for (line in br.lines())
                // br.readLines().size -> compte le nb éléments

            br.forEachLine { nbLignes += 1 } // va aussi fermer le br lorsque la tâche est terminée
        }

       nbLines.text = nbLignes.toString()

        return nbLines
    }

    private fun countNombreCaract() : TextView {

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

        return nbCaract

    }

    private fun countNombreC() : TextView {

        val fis = openFileInput("fichier.txt")

        val isr = InputStreamReader(fis)

        val br = BufferedReader(isr)

        var nombreC = 0

        br.use {


            br.forEachLine {

                val ligne = br.readLine() // String

//                for (i in 0 <..ligne.length)
//                    if(ligne.elementAt(i)== 'C' || ligne.elementAt(i)== 'c'  )
//                        nombreC += 1

                br.forEachLine {

                    if (ligne.contains('c') || ligne.contains('C')) {

                        nombreC += 1
                    }
                }

            }

        }

        nbC.text = nombreC.toString()

        return nbC
    }

    private fun addNom() {

        val nom = champNom.text.toString()

        if (nom.isNotEmpty()) {

            val fos = openFileOutput("fichier.txt", MODE_APPEND) // append ajoute à la suite du contenu déjà là
            val osw = OutputStreamWriter(fos)
            val bw = BufferedWriter(osw)

            bw.write(nom)
            bw.newLine()

            bw.close()

            champNom.text.clear()

            finish()

    }


    fun scannerNbMots() : TextView {

        // Les scanners utilisent des jetons (ou tokens)

        // caract blanc -> est le delimiter par défaut d'un scanner

        var nbMots = 0

        val fis = openFileInput("fichier.txt")

        fis.use {

            val scanner = Scanner(fis) // on n'appelle pas useDelimiter car on veut un caract blanc

            while(scanner.hasNext()) {

                nbMots++

                scanner.next()
            }

//            val line = scanner.nextLine()
//
//            val mots = line.trim().split("\\s+") // n'est pas nécessaire normalement car delimiter est caract blanc par défaut
//
//            nbMots = mots.size

            nbMot.text = nbMots.toString()

            return nbMot

        }


    }

        nbLines = countNbLignes()
        nbCaract = countNombreCaract()
        nbC = countNombreC()

        nbMot = scannerNbMots()



}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Annexe_1BTheme {
        Greeting("Android")
    }
}

}