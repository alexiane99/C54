package com.example.atelier1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    val url = "https://api.jsonbin.io/v3/b/67fe6a908a456b796689f63d?meta=false"

    override fun onCreate(savedInstanceState: Bundle?) {

        val queue = Volley.newRequestQueue(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_klaxon)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        val stringRequest = StringRequest(

            Request.Method.GET, url,

            Repondeur(),
            RepondeurErreurs()

//            { response ->
//                //Toast.makeText(this@MainActivity, response.toString(), Toast.LENGTH_LONG).show()
//            },
//            {
//                //Toast.makeText(this,"ereur", Toast.LENGTH_LONG).show()
//            }
        )

        queue.add(stringRequest) // ne pas oublier d'ajouter la requête
    }

    inner class Repondeur : Response.Listener<String> {

        override fun onResponse(response: String?) {

            Toast.makeText(this@MainActivity, response.toString(), Toast.LENGTH_LONG).show()
        }
    }

    inner class RepondeurErreurs : Response.ErrorListener {

        override fun onErrorResponse(error: VolleyError) {

            Toast.makeText(this@MainActivity,"erreur", Toast.LENGTH_LONG).show()
        }
    }
}