package com.example.annexe1

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.time.LocalDate

class AjouterActivity : AppCompatActivity() {

    lateinit var boutonAdd: Button
    lateinit var champMemo : EditText
    lateinit var boutonEcheance : Button
    lateinit var champDate : TextView

    var dateChoisie = LocalDate.now().plusDays(1)

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
        boutonEcheance = findViewById(R.id.boutonEcheance)
        champDate = findViewById(R.id.champDate)

        val ec = Ecouteur()

        boutonEcheance.setOnClickListener(ec)

        // simplification interface fonctionnelle + lambda + lambda comme dernier param -> à revoir
  //      boutonAdd.setOnClickListener {

//            var texteMemo = champMemo.text.toString()
//            // verifier si texteMemo est vide
//
//            if (!texteMemo.isEmpty()) {
//
//                val fos = openFileOutput("fichier.txt", MODE_APPEND) // append ajoute à la suite du contenu déjà là
//                val osw = OutputStreamWriter(fos)
//                val bw = BufferedWriter(osw)
//
//                bw.write(texteMemo)
//                bw.newLine()
//
//                bw.close()
//
//                champMemo.text.clear()
//
//                finish()

            //}


        //}
    }

    inner class Ecouteur : OnClickListener, OnDateSetListener {

        override fun onClick(v: View?) {
            if (v == boutonEcheance) {

                val d = DatePickerDialog(this@AjouterActivity)

                d.setOnDateSetListener(this)
                // on veut l'afficher
                d.show()

            }
            else // bouton Ajouter
            {
                //ajouter le memo dans la liste du singleton

//                if(v == boutonAdd) {
//                }

                SingletonMemos.ajouterMemo(Memo(champMemo.text.toString(), dateChoisie))//LocalDate.parse(champDate.text.toString())

                // mettre à jour ici le fichier de sérialisation

                SingletonMemos.serialiserListe(this@AjouterActivity)

                champMemo.text.clear()
                champDate.text = ""

                //}

               // finish()

                val i = Intent(this@AjouterActivity, MainActivity::class.java)
                startActivity(i)
            }

        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

            // aller chercher le message et le localDate

            dateChoisie = LocalDate.of(year, month+1, dayOfMonth) // attention, commence à zéro!!

            champDate.setText(dateChoisie.toString())

            //var date = champDate.text.toString()

             //créer un objet Memo

             //ajouter à la liste




        }


    }

}