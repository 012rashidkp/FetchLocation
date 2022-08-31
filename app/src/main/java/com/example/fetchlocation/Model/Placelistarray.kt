package com.example.fetchlocation.Model

import java.io.Serializable

data class Placelistarray(
    val title:String,
    val description:String,
    val latitude:Double,
    val longitude:Double
):Serializable
