package com.example.clon_fulanito

import com.google.gson.annotations.SerializedName

data class PaginaContenedora(
    val message: String,
    val total_records: Int,
    val total_pages: Int,
    val results: List<NaveResumen>
)