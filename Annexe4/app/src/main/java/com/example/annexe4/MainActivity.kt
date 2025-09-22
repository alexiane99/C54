package com.example.annexe4

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var boutonAdd : Button

    val lanceur: ActivityResultLauncher<Intent>(Activity)

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

        val ec = Ecouteur()

        boutonAdd.setOnClickListener(ec)

        // avec lambda
        boutonAdd.setOnClickListener{ lanceur.launch(Intent(this@MainActivity))}
    }

    inner class Ecouteur : View.OnClickListener {

        override fun onClick(v: View?) {

            when (v) {
                boutonAdd -> {

                    val i = Intent(this@MainActivity, AjouterUser::class.java)
                    startActivity(i)
                }

            }
        }
    }


}