package com.example.annexe8b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var boutonGD : Button
    lateinit var boutonAnim : Button
    lateinit var boutonSplash : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        boutonGD = findViewById(R.id.boutonGD)
        boutonAnim = findViewById(R.id.boutonAnim)
        boutonSplash = findViewById(R.id.boutonSplash)

        boutonGD.setOnClickListener {

            val i = Intent(this@MainActivity, GDActivity::class.java)
            startActivity(i)
        }

        boutonAnim.setOnClickListener {

            val i = Intent(this@MainActivity, AnimActivity::class.java)
            startActivity(i)
        }

        boutonSplash.setOnClickListener {

            val i = Intent(this@MainActivity, SplashActivity::class.java)
            startActivity(i)
        }
    }
}