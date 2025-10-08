package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon

class MainActivity : AppCompatActivity() {

    lateinit var liste : ListView

    val url = "https://api.jsonbin.io/v3/b/680a6a1d8561e97a5006b822?meta=false"

//    val lanceur: ActivityResultLauncher<Intent> = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult(), Klaxon())


    override fun onCreate(savedInstanceState: Bundle?) {

        val queue = Volley.newRequestQueue(this)

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

        val stringRequest = StringRequest(

            Request.Method.GET, url,
            { response ->
                val li: ListeMusique =
                    Klaxon().parse<ListeMusique>(response) ?: ListeMusique() // pour se débarrasser du ?

               Toast.makeText(this, "Response ${li.listeMusique.size}", LENGTH_LONG).show()
                println(li.listeMusique.size)
            },
            {
                Toast.makeText(this, "erreur", Toast.LENGTH_LONG).show()
            }
        )

        queue.add(stringRequest) // ne pas oublier d'ajouter la requête
    }
}