package com.example.atelier1

import com.beust.klaxon.Json

class ListeProduits() {

    @Json(name = "accessoires") // plutôt qu'accessoires, important, doit utiliser @json avant
    // sinon utiliser le même nom
    var articles: List<Produit> = emptyList()
}