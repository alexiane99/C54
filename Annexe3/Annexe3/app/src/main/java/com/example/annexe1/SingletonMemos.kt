package com.example.annexe1

import android.content.Context
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import kotlin.collections.ArrayList

object SingletonMemos {

    private var liste:ArrayList<Memo> = ArrayList()

    fun ajouterMemo (memo: Memo) {

        liste.add(memo)
    }

    fun getListe() : ArrayList<Memo> {

        return liste
    }

    // besoin du contexte car n'est pas dans une activité
    fun serialiserListe(contexte : Context) {

        // le contexte ouvre un fichier pour de sérialisation pour insérer la liste
        val fos = contexte.openFileOutput("serialisation.ser", Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos) // tampon spécial pour les objets

        oos.use {  // fct de haut niveau qui prend en parametre une lambda, donc pas besoin de ( )
            oos.writeObject(liste) // permet de créer la liste ici
        }
    }

    // attention au fileNotFoundException
    fun recupererListe(contexte:Context) : ArrayList<Memo> {

        if (liste.isEmpty()) {

            val fis = contexte.openFileInput("serialisation.ser")
            val ois = ObjectInputStream(fis)

            ois.use {

                liste = ois.readObject() as ArrayList<Memo>

            }
        }

        return ArrayList(liste) // on retourne une copie de sécurité de notre liste
    }
}