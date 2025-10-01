package com.example.revision

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Scanner

class MainActivity : AppCompatActivity() {

    lateinit var lastActivity : TextView
    lateinit var counter : TextView
    lateinit var liste : ListView

    var listeLangages = ArrayList<Langage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.liste)
        counter = findViewById(R.id.counter)
        lastActivity = findViewById(R.id.lastActivity)

        liste.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, lireListe()))

        counter.text = countInexistants2012().toString()

        deSerializer(this@MainActivity)
    }

    fun lireListe() : ArrayList<Langage> { // pour afficher liste

        try {

            //val fis = openFileInput("langage.txt")
            val fis = getResources().openRawResource(R.raw.langage)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            //val ois = ObjectInputStream(fis)

            br.use { // ois

               val scanner = Scanner(br)

                while(scanner.hasNextLine()) {

                    val ligne = scanner.nextLine()

                    val langage = ligne.trim().split(",")

                    if (langage.size == 5) {

                        val nom = langage[0]
                        val rang2024 = langage[1]
                        val rang2019 = langage[2]
                        val rang2012 = langage[3]

                        listeLangages.add(Langage(nom, rang2024, rang2019, rang2012))

                    }
                }


            }
        }
        catch(fnfe: FileNotFoundException) {

            // s'il n'y a pas d'infos à afficher, on passe ici
            Toast.makeText(this@MainActivity, "Il n'y a pas de fichier texte", Toast.LENGTH_LONG).show()

        }

        return listeLangages

    }

    fun countInexistants2012() : Int {

        var counter : Int = 0

        if(listeLangages.isNotEmpty()) {


            for(i in 0 until listeLangages.size) {

                if(listeLangages[i].rang2012 == "99") {

                    counter++
                }
            }
        }

        return counter

    }

    fun serialiserActivity(contexte: Context) {

        val fos = contexte.openFileOutput("serialisation.ser", Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)

        var dateTime = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss")

        oos.use {

            oos.writeObject(dateTime.format(formatter))
        }
    }

    override fun onStop() { // Ctrl + O pour générer le onStop

        super.onStop()

        try {
            serialiserActivity(this@MainActivity) // écrire ici la méthode dans le try catch
        }
        catch (io: IOException) {

            io.printStackTrace()
        }
    }

    fun deSerializer(contexte : Context) {

        try {
            val fis = contexte.openFileInput("serialisation.ser")
            val ois = ObjectInputStream(fis)

            ois.use {

                var dateTime = ois.readObject()

                lastActivity.text = dateTime.toString()

            }
        }
        catch(fnfe: FileNotFoundException) {

            // s'il n'y a pas d'infos à afficher, on passe ici
            Toast.makeText(this@MainActivity, "Il n'y a pas de fichier de sérialisation", Toast.LENGTH_LONG).show()

        }
    }

}