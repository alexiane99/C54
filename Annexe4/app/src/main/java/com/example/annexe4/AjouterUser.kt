package com.example.annexe4

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AjouterUser : AppCompatActivity() {

    lateinit var boutonConfirm : Button
    lateinit var champPrenom : TextView
    lateinit var champNom : TextView

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

        val ec = Ecouteur()

        boutonConfirm.setOnClickListener(ec)
    }

    fun getUsername() {

        val retour = Intent()
        var newUser = Utilisateur(champPrenom.text.toString(), champNom.text.toString())
        retour.putExtra("util", newUser)
        //setResult(Id, retour)
        //finish() // superposés, visuellement on revient à MainActivity

        //return newUser // fichier util

    }

    inner class Ecouteur : View.OnClickListener { // lambda

        override fun onClick(v: View?) {

            when (v) {

                boutonConfirm -> {

                    val i = Intent(this@AjouterUser, MainActivity::class.java)
                    startActivity(i)
                }

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
}