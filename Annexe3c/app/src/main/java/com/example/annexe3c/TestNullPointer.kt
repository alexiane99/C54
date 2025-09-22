package com.example.annexe3c

fun main(args: Array<String>)
{
//    var mot:String? =null
//    println ( mot.length)  // en Java, ça ferait un NullPointerException

//    var mot:String = "abc"
//    var nombre:Int? = mot as? Int // transtypage sécuritaire
//    println(nombre)

    val listeAvecDesNull: List<String?> = listOf("hugo", "loic", null, "eddy")
    for (item in listeAvecDesNull) {
        item?.let { println(item.length) }
    }


}
