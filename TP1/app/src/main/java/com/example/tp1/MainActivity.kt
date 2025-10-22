package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    lateinit var liste : ListView

//    val lanceur: ActivityResultLauncher<Intent> = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult(), Klaxon())


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

        //lanceur.launch(Intent(this@MainActivity, Klaxon::class.java)) // appelle java

        Modele()
//
//        val adapt = SimpleAdapter(this, remplir,R.layout.chanson, arrayOf("nom", "prix"),intArrayOf(R.id.texteNom, R.id.textePrix) )
//
//        // le lier au listview
//        liste.adapter = adapt
//
    }
}