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

class MainActivity : AppCompatActivity() {

    lateinit var boutonAdd : Button
    lateinit var champTexte : TextView

    lateinit var lanceur: ActivityResultLauncher<Intent>

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

        lanceur = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback { AddUser()})

        // avec lambda
        boutonAdd.setOnClickListener{ lanceur.launch(Intent(this@MainActivity, AjouterUser::class.java)) }
    }

    inner class AddUser() : ActivityResultCallback<ActivityResult> {

        override fun onActivityResult(result: ActivityResult) {



        }
    }



}