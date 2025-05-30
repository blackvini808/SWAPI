package com.example.clon_fulanito.API.SWAPI

import com.example.clon_fulanito.PaginaContenedora
import com.example.clon_fulanito.API.SWAPI.NaveEspacial

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SWAPIInterfaz {

    @GET("starships")
    suspend fun obtener_naves_espaciales(@Query("page") pagina: Int): PaginaContenedora

    @GET("starships/{id}")
    suspend fun obtener_nave(@Path("id") id: Int): NaveEspacial
}