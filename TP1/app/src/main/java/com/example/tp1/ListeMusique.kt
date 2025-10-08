package com.example.tp1

import com.beust.klaxon.Json

class ListeMusique {

    @Json(name = "music")

    var listeMusique : List<Chanson> = emptyList()
}