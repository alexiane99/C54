package com.example.annexe1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var boutonAjouter: Button
    lateinit var boutonAfficher: Button
    lateinit var boutonQuitter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        boutonAjouter = findViewById(R.id.boutonAjouter)
        boutonAfficher = findViewById(R.id.boutonAfficher)
        boutonQuitter = findViewById(R.id.boutonQuitter)

        // 1ere
        val ec = Ecouteur()

        //2e etape
        boutonAjouter.setOnClickListener(ec)
        boutonAfficher.setOnClickListener(ec)
        boutonQuitter.setOnClickListener(ec)
    }

    //3e etape
    inner class Ecouteur : View.OnClickListener {

        override fun onClick(v: View?) {

            when (v) {

                boutonQuitter -> finish()
                boutonAjouter -> {

                    val i = Intent(this@MainActivity, AjouterMemo::class.java)
                    startActivity(i)
                }
                boutonAfficher -> {

                    val i = Intent(this@MainActivity, ListeMemos::class.java)
                    startActivity(i)
                }

            }
        }
    }
}