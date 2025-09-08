package com.example.annexe1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter

class AjouterMemo : AppCompatActivity() {

    lateinit var boutonAdd: Button
    lateinit var champMemo : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter_memo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        boutonAdd = findViewById(R.id.boutonAdd)
        champMemo = findViewById(R.id.champMemo)

        // simplification interface fonctionnelle + lambda + lambda comme dernier param -> à revoir
        boutonAdd.setOnClickListener {

            var texteMemo = champMemo.text.toString()
            // verifier si texteMemo est vide

            if (!texteMemo.isEmpty()) {

                val fos = openFileOutput("fichier.txt", MODE_APPEND) // append ajoute à la suite du contenu déjà là
                val osw = OutputStreamWriter(fos)
                val bw = BufferedWriter(osw)

                bw.write(texteMemo)
                bw.newLine()

                bw.close()

                champMemo.text.clear()

                finish()

            }


        }
    }
}