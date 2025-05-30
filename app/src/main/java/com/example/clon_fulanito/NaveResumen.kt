package com.example.clon_fulanito


import com.google.gson.annotations.SerializedName

data class NaveResumen(
    val name: String,
    val model: String?,
    val manufacturer: String?,
    val cost_in_credits: String?,
    val uid: String,
    val url: String
)