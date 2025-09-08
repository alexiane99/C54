package com.example.annexe_1b

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.annexe_1b.ui.theme.Annexe_1BTheme
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Scanner

class MainActivity : ComponentActivity() {

    lateinit var champNom : EditText
    lateinit var nbLines : TextView
    lateinit var nbCaract : TextView
    lateinit var nbC : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Annexe_1BTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                champNom = findViewById(R.id.champNom)
                nbLines = findViewById(R.id.nbLine)
                nbCaract = findViewById(R.id.nbCaract)
                nbC = findViewById(R.id.nbC)
            }
        }
    }

    fun count_nbLignes() : Int {

        val fis = openFileInput("okok.txt")

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

        return nbLignes
    }

    fun count_nombreCaract() : Int {

        val fis = openFileInput("okok.txt")

        val isr = InputStreamReader(fis)

        val br = BufferedReader(isr)

        var sumCaractLignes = 0

        br.use {

            var ligne = br.readLine() // String

            br.forEachLine { ligne ->

                //var ligne = br.readLine() // String

                sumCaractLignes += ligne.length
            }


        }

        return sumCaractLignes

    }

    fun count_nombreC() : Int {

        val fis = openFileInput("okok.txt")

        val isr = InputStreamReader(fis)

        val br = BufferedReader(isr)

        var nombreC = 0

        br.use {


            br.forEachLine {

                var ligne = br.readLine() // String

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

        return nombreC
    }

    fun add_nom() {

        var nom = champNom.text.toString()

        if (!nom.isEmpty()) {

            val fos = openFileOutput("fichier.txt", MODE_APPEND) // append ajoute à la suite du contenu déjà là
            val osw = OutputStreamWriter(fos)
            val bw = BufferedWriter(osw)

            bw.write(nom)
            bw.newLine()

            bw.close()

            champNom.text.clear()

            finish()

    }


    fun scanner_nbMots() : Int {

        var nbMots = 0

        val scanner = Scanner(System.`in`)

        val line = scanner.nextLine()

        val mots = line.trim().split("\\s+")

        nbMots = mots.size

        return nbMots



    }


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