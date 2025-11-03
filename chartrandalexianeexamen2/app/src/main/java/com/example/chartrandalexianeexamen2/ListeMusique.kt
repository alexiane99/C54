package com.example.chartrandalexianeexamen2

import com.beust.klaxon.Json

class ListeMusique {

    @Json(name = "items")

    var listeMusique : List<Chanson> = emptyList()
}