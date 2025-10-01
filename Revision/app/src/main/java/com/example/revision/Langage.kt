package com.example.revision

import java.io.Serializable

data class Langage (var nom : String, var rang2024 : String, var rang2019 : String, var rang2012 : String ) : Serializable {

    override fun toString() : String {

        return "$nom (2024: $rang2024, 2019: $rang2019, 2012: $rang2012)"

    }
}