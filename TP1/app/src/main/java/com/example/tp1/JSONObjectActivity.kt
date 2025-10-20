package com.example.tp1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class JSONObjectActivity : AppCompatActivity() {

    val url = "https://api.jsonbin.io/v3/b/680a6a1d8561e97a5006b822?meta=false"

    override fun onCreate(savedInstanceState: Bundle?) {

        val queue = Volley.newRequestQueue(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jsonobject)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val jsonRequest = JsonObjectRequest(

            Request.Method.GET, url,
            { response ->
                val tab = response.getJSONArray("music")
            },
            {
                Toast.makeText(this,"erreur", Toast.LENGTH_LONG).show()
            }
        )

        queue.add(jsonRequest)
    }
}