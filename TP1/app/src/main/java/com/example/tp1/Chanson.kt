package com.example.tp1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serial
import java.io.Serializable

@Parcelize
data class Chanson (val id : String,
                    val title :String,
                    val album: String,
                    val artist: String,
                    val genre : String,
                    val source: String,
                    val image: String,
                    val trackNumber : Int,
                    val totalTrackCount: Int,
                    val duration : Int,
                    val site : String) : Parcelable
{
}