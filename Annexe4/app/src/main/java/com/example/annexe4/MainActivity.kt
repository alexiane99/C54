package com.example.annexe4

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileNotFoundException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {

    lateinit var boutonAdd : Button
    lateinit var champTexte : TextView

    var util : Utilisateur? = null
    val lanceur: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), CallBackUtilisateur())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        boutonAdd = findViewById(R.id.boutonAdd)
        champTexte = findViewById(R.id.texte)

        // avec lambda
        boutonAdd.setOnClickListener{ lanceur.launch(Intent(this@MainActivity, IdentifyUtilisateur::class.java)) }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            util = savedInstanceState?.getSerializable("util", Utilisateur::class.java)
        else
            util = savedInstanceState?.getSerializable("util") as Utilisateur?

        champTexte.text = "Bonjour ${util?.prenom?:" "} ${util?.nom?:" "}!" // ?: elvis

        // serialisation
        util = recupererUtilisateurSerialisation()
        champTexte.text = "Bonjour ${util?.prenom?:" "} ${util?.nom?:" "}!"


    }

    // savedInstanceStates
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        // conserver l'utilisateur
        outState.putSerializable("util",util)
    }

    override fun onStop() { // Ctrl + O
        super.onStop()

        // sauvegarder dans le fichier
        val fos = openFileOutput("serialisation.ser", Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)

        oos.use {

            oos.writeObject(util)

        }

        // on refait l'inverse dans le onCreate
    }

    fun recupererUtilisateurSerialisation () : Utilisateur? {

        try {

            val fis = openFileInput("serialisation.ser")
            val ois = ObjectInputStream(fis)

            ois.use {

                util = ois.readObject() as Utilisateur

            }


        }
        catch(fnfe: FileNotFoundException) {

            Toast.makeText(this@MainActivity, "Il n'y a pas de fichier de sérialisation", Toast.LENGTH_LONG).show()

        }

        return util

    }

    inner class CallBackUtilisateur() : ActivityResultCallback<ActivityResult> {

        override fun onActivityResult(result: ActivityResult) {

            if(result.resultCode == RESULT_OK) {

                val intentRetour = result.data // retrouve le intent de retour

                if (intentRetour != null)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) // récupère objet

                        util = intentRetour.getSerializableExtra("util", Utilisateur::class.java)
                    else
                        util = intentRetour.getSerializableExtra("util") as Utilisateur?


                champTexte.text = ("Bonjour ${util?.prenom?:" "} ${util?.nom?:" "}!") // ? elvis -> si le membre de gauche est nul

            }

        }
    }


}