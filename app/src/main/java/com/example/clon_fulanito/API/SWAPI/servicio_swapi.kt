package com.example.clon_fulanito.API.SWAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InstanciaRetrofitSWAPI {
    private const val url_base = "https://www.swapi.tech/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url_base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val consumir_servicio: SWAPIInterfaz by lazy {
        retrofit.create(SWAPIInterfaz::class.java)
    }
}