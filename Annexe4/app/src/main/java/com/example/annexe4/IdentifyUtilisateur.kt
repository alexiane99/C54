package com.example.annexe4

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IdentifyUtilisateur : AppCompatActivity() {

    lateinit var boutonConfirm: Button
    lateinit var champPrenom: TextView
    lateinit var champNom: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        boutonConfirm = findViewById(R.id.boutonConfirm)
        champPrenom = findViewById(R.id.prenom)
        champNom = findViewById(R.id.nom)

        boutonConfirm.setOnClickListener {

            val retour = Intent()

            var newUser = Utilisateur(champPrenom.text.toString(), champNom.text.toString())

            retour.putExtra("util", newUser) // permet de sauvegarder un objet dans un intent
            setResult(RESULT_OK, retour)
            finish() // superposés, visuellement on revient à MainActivity

        }

    }

}

//    inner class ActivResultCallBack
//    OnActivityResult
//    if result.code == resultok
//    val intentRetour = result.data
//    if(intentRetour != null)
//        if (Buil.VERSIONSDK.INT <= tirmisu)
//            util = intentRertour.getSerializableExtra("util", Utilisateur::class.java)
