package com.example.pokeapi.pokemons

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


data class Ability(
    val ability: NamedAPIResource,
    val is_hidden: Boolean,
    val slot: Int
)

data class NamedAPIResource(
    val name: String,
    val url: String
)

data class Type(
    val slot: Int,
    val type: NamedAPIResource
)

data class PokemonResponse(
    val id: Int,
    val name: String,
    val base_experience: Int,
    val height: Int,
    val weight: Int,
    val abilities: List<Ability>,
    val types: List<Type>,
    val sprites: Sprites,
    val location_area_encounters: String
)

data class Sprites(
    val front_default: String?,
    val front_shiny: String?,
    val other: OtherSprites?
)

data class OtherSprites(
    val official_artwork: OfficialArtwork?
)

data class OfficialArtwork(
    val front_default: String?,
    val front_shiny: String?
)

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<NamedAPIResource>
)


interface ApiServicePokemon {
    @GET("pokemon/")
    fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<PokemonListResponse>

    @GET("pokemon/{idOrName}/")
    fun getPokemonByIdOrName(@Path("idOrName") idOrName: String): Call<PokemonResponse>
}


class Pokedex {
    var id: String = ""
    var name: String = ""
    var height: String = ""
    var frontDefault: String? = null

    fun loadFromResponse(pokemonResponse: PokemonResponse) {
        id = pokemonResponse.id.toString()
        name = pokemonResponse.name
        height = pokemonResponse.height.toString()
        frontDefault = pokemonResponse.sprites.front_default
    }

    override fun toString(): String {
        return "Pokedex(id='$id', name='$name', height='$height', frontDefault='$frontDefault')"
    }
}


object RetrofitClient {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    val apiService: ApiServicePokemon by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiServicePokemon::class.java)
    }
}

fun main() {
    val apiService = RetrofitClient.apiService

    val call = apiService.getPokemonByIdOrName("pikachu")
    call.enqueue(object : retrofit2.Callback<PokemonResponse> {
        override fun onResponse(
            call: Call<PokemonResponse>,
            response: retrofit2.Response<PokemonResponse>
        ) {
            if (response.isSuccessful) {
                val pokemonResponse = response.body()
                if (pokemonResponse != null) {
                    val pokedex = Pokedex()
                    pokedex.loadFromResponse(pokemonResponse)
                    println(pokedex)
                }
            } else {
                println("Error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
            println("Failure: ${t.message}")
        }
    })
}
