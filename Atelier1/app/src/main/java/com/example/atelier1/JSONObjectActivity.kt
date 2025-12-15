package com.example.atelier1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.lang.reflect.Method

class JSONObjectActivity : AppCompatActivity() {

    lateinit var liste : ListView

    val url = "https://api.jsonbin.io/v3/b/67fe6a908a456b796689f63d?meta=false"

    override fun onCreate(savedInstanceState: Bundle?) {

        val queue = Volley.newRequestQueue(this)

        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_klaxon)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.liste)

        val jsonRequest = JsonObjectRequest( // object car { }
            // si [ ], alors JSONArrayRequest
            // request.getJSONArray
            // getJSONObject
            Request.Method.GET, url, null,
            {
                response ->
                    val tab = response.getJSONArray("accessoires")
                    decomposerReponse(tab)

           },
            { Toast.makeText(this,"erreur", Toast.LENGTH_LONG).show()} )

        queue.add(jsonRequest) // ne pas oublier d'ajouter la requête


        val ec = Ecouteur()
        liste.onItemClickListener = ec
    }

    inner class Ecouteur : AdapterView.OnItemClickListener {

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            var nom = liste.adapter.getItem(position).toString()

            Toast.makeText(this@JSONObjectActivity,"Vous avez sélectionné ${nom}", Toast.LENGTH_LONG).show()

        }
    }
    fun decomposerReponse(tab : JSONArray) {

        val remplir = ArrayList<HashMap<String, String>>()

        for(i in 0 ..tab.length() - 1) {

            // java style
            var temp = HashMap<String, String>()
            temp.put("nom", tab.getJSONObject(i).get("nom").toString())
            temp.put("prix", tab.getJSONObject(i).get("prix").toString())
            remplir.add(temp)
        }

        val adapt = SimpleAdapter(this, remplir,R.layout.accessoires, arrayOf("nom", "prix"),intArrayOf(R.id.texteNom, R.id.textePrix) )

        // le lier au listview
        liste.adapter = adapt

    }



}
