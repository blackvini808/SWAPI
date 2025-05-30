package com.example.clon_fulanito.API.SWAPI


data class NaveEspacial(
    val message: String,
    val result: NaveDetalle
)

data class NaveDetalle(
    val properties: NaveProperties
)

data class NaveProperties(
    val name: String,
    val model: String,
    val manufacturer: String,
    val cost_in_credits: String,
    val length: String,
    val max_atmosphering_speed: String,
    val crew: String,
    val passengers: String,
    val cargo_capacity: String,
    val consumables: String,
    val hyperdrive_rating: String,
    val MGLT: String,
    val starship_class: String
)