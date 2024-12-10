package com.example.pokeapi.apiconfig

import com.example.pokeapi.pokemons.Pokedex
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Peticiones {
    @GET("pokemon/{id}")
    fun consultar(@Path("id") id: String): Call<Pokedex>
}