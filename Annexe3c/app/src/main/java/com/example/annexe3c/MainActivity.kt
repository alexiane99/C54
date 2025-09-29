package com.example.annexe3c

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Identity
import android.widget.CheckBox
import android.widget.EditText


import android.widget.LinearLayout
import android.widget.TextView
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


    lateinit var dent1: LinearLayout
    lateinit var dent2: LinearLayout
    lateinit var noDent1 : TextView
    lateinit var noDent2 : TextView
    lateinit var check1 : CheckBox
    lateinit var check2 : CheckBox
    lateinit var note1 : TextView
    lateinit var note2 : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dent1 = findViewById(R.id.dent1)
        dent2 = findViewById(R.id.dent2)
        noDent1 = findViewById(R.id.noDent1)
        noDent2 = findViewById(R.id.noDent2)
        note1 = findViewById(R.id.note1)
        note2 = findViewById(R.id.note2)
        check1 = findViewById(R.id.check1)
        check2 = findViewById(R.id.check2)

        deSerializer(this@MainActivity)

//        try {
//            val fis = openFileInput("dents.ser")
//            val ois = ObjectInputStream(fis)
//
//            ois.use {
//                var dent1 : Note = ois.readObject() as Note
//
//                (dent1.getchildAt(0) as EditText).setText(dent1.noDent.toString())
//            }
//        }

    }

    // Enregistrement des infos dans le fichier de sérialisation
    fun serialiserNote(contexte : Context) {

        val fos = contexte.openFileOutput("serialisation.ser", Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)

        var note1 = Note(noDent1.text.toString(), check1.isChecked, note1.text.toString())
        var note2 = Note(noDent2.text.toString(), check2.isChecked, note2.text.toString())

        var liste = ArrayList<Note>()
        liste.add(note1)
        liste.add(note2)

        oos.use {

            oos.writeObject(liste)

        }

    }

    fun deSerializer(contexte : Context) {

       try {

           val fis = contexte.openFileInput("serialisation.ser")
           val ois = ObjectInputStream(fis)

           var l = ArrayList<Note>()

           var n1 : Note
           var n2 : Note

           ois.use {

               l = ois.readObject() as ArrayList<Note> // pour transtyper

               if(l.isNotEmpty()) {

                   n1 = Note(l[0].noDent, l[0].traitementCanal, l[0].notes)

                   noDent1.setText(n1.noDent)

                   if (n1.traitementCanal) {

                       check1.isActivated
                   }

                   note1.setText(n1.notes)

                   if(l.size == 2) {

                       n2 = Note(l[1].noDent, l[1].traitementCanal, l[1].notes)

                       noDent2.setText(n2.noDent)

                       if (n2.traitementCanal) {

                           check2.isActivated
                       }

                       note2.setText(n2.notes)
                   }
               }

           }

       }
       catch(fnfe: FileNotFoundException) {

           // s'il n'y a pas d'infos à afficher, on passe ici
           Toast.makeText(this@MainActivity, "Il n'y a pas de fichier de sérialisation", Toast.LENGTH_LONG).show()

       }

    }

    override fun onStop() { // Ctrl + O pour générer le onStop

        super.onStop()

        try {
            serialiserNote(this@MainActivity) // écrire ici la méthode dans le try catch
        }
        catch (io: IOException) {

            io.printStackTrace()
        }
    }

}