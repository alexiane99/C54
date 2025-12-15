package com.example.examen_final

import com.beust.klaxon.Json

class ListeCadeaux {

    @Json(name = "cadeaux")

    var listeCadeaux: List<Cadeau> = emptyList()
}