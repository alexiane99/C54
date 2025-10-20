package com.example.examen1alexianechartrand

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Scanner

class MainActivity : AppCompatActivity() {

    lateinit var boutonStart : Button
    lateinit var textePomme : TextView
    lateinit var texteCereale : TextView

    var listeProduits = ArrayList<Produit>()
    var pomme : Produit? = null
    var cereale : Produit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        boutonStart = findViewById(R.id.boutonStart)
        textePomme = findViewById(R.id.textePommes)
        texteCereale = findViewById(R.id.texteCereales)


        deSerializer(this@MainActivity) // pour retrouver les infos déjà enregistrés s'il y a lieu

        boutonStart.setOnClickListener {

            if (listeProduits.isEmpty()) { // pour la première fois

                lireFichier()
                println(listeProduits)

                var pommeAubaine = trouverAubaine("Sac Pommes")
                textePomme.text =
                    ("${pomme?.nom ?: " "} + ${pomme?.prix ?: " "} + ${pomme?.epicerie ?: " "}")


                pomme = pommeAubaine

                var cerealeAubaine = trouverAubaine("Céréales")
                texteCereale.text =
                    ("${cereale?.nom ?: " "} + ${cereale?.prix ?: " "} + ${cereale?.epicerie ?: " "}")
                //println(listeProduits)

                cereale = cerealeAubaine

                serializerProduits(this@MainActivity)

            }
            else { // devrait passer ici les prochaines fois si on clique sur le bouton

                Toast.makeText(this@MainActivity, "Analyse terminée", Toast.LENGTH_LONG).show()

            }

        }
    }

    fun serializerProduits(context: Context) { // pour save les infos

        val fos = openFileOutput("serialisation.ser", Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)

        var liste = ArrayList<Produit>()

        if(pomme != null) {
            liste.add(pomme!!)
        }

        if(cereale != null) {
            liste.add(cereale!!)
        }

        oos.use {

            oos.writeObject(liste) //as ArrayList<Produit>

        }
    }

    fun deSerializer(context: Context) { // pour récup les infos

        try {

            val fis = context.openFileInput("serialisation.ser")
            val ois = ObjectInputStream(fis)

            var l = ArrayList<Produit>()

            var pomme : Produit
            var cereale : Produit

            ois.use {

                l = ois.readObject() as ArrayList<Produit>

                if (l.isNotEmpty()) {

                    pomme = Produit(l[0].nom, l[0].prix, l[0].epicerie)

                    textePomme.text =
                        ("${pomme?.nom ?: " "} + ${pomme?.prix ?: " "} + ${pomme?.epicerie ?: " "}")


                    cereale = Produit(l[1].nom, l[1].prix, l[1].epicerie)
                    texteCereale.text =
                        ("${cereale?.nom ?: " "} + ${cereale?.prix ?: " "} + ${cereale?.epicerie ?: " "}")
                }
            }

        }
        catch(fnfe: FileNotFoundException) {

            // s'il n'y a pas d'infos à afficher, on passe ici
            Toast.makeText(this@MainActivity, "Il n'y a pas de fichier de sérialisation", Toast.LENGTH_LONG).show()

        }

    }

    fun lireFichier() { // lire le fichier et mettre dans une liste de produits

        val fis = getResources().openRawResource(R.raw.circulaires)
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        br.use {

            val scanner = Scanner(br)

            while(scanner.hasNextLine()) {

                val ligne = scanner.nextLine()

                val produit = ligne.trim().split(",") // et \n

                if(produit.size == 3) {

                    val nom = produit[0]
                    val prix = produit[1]
                    val epicerie = produit[2]

                    listeProduits.add(Produit(nom, prix.toDouble(), epicerie))
                }
            }

        }

    }

    fun trouverAubaine(nomProd : String) : Produit {

        var produitAubaine = Produit("", 0.00, "")

        // méthode pour trier directement

        // méthode Eric
        // 1 set meilleure prix et meilleurObject = null
        // if object.prix < meilleurPrix
        // set meilleurPrix = objet.prix
        // meilleurObjet = objet

        if(listeProduits.isNotEmpty()) {

            listeProduits.sortWith(compareBy{it.prix})

            for (produit in listeProduits) {

                if (produit.nom == nomProd) {

                    produitAubaine = produit
                }
            }

           // var listeTemp = ArrayList<String>()

//            for(i in 0 until listeProduits.size)   {
//
//                if (listeProduits.elementAt(i).nom == nomProd) {
//
//                    for(j in i until listeProduits.size) // 2e boucle pour vérifier et comparer les autres items du même type
//
//                        if (listeProduits.elementAt(i).prix < listeProduits.elementAt(j).prix)
//
//                            listeProduits[i] = Produit(
//                                listeProduits.elementAt(i).nom,
//                                listeProduits.elementAt(i).prix,
//                                listeProduits.elementAt(i).epicerie
//                            )
//
//                            produitAubaine = listeProduits[i]
//                }
//            }

        }

        return produitAubaine

    }
}