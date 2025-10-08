package com.example.tp1

import android.app.Activity.RESULT_OK
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

//class Klaxon() : ActivityResultCallback<ActivityResult> {

//    val url = "https://api.jsonbin.io/v3/b/680a6a1d8561e97a5006b822?meta=false"
//
//    override fun onActivityResult(result:ActivityResult) {
//
//        if(result.resultCode == RESULT_OK) {
//
//            val intentRetour = result.data
//
//
//            val stringRequest = StringRequest(
//
//                Request.Method.GET, url,
//                { response ->
//                    val li: ListeMusique =
//                        Klaxon().parse<ListeMusique>(response)
//                            ?: ListeMusique() // pour se débarrasser du ?
//
//                    intentRetour.data = Toast.makeText(this@Klaxon, "Response ${li.listeMusique.size}", LENGTH_LONG).show()
//                },
//                {
//                    Toast.makeText(this, "erreur", Toast.LENGTH_LONG).show()
//                }
//            )
//        }
//
//
//    }
//}