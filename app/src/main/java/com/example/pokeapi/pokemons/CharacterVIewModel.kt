package com.example.pokeapi.pokemons


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapi.apiconfig.ApiClient // Assuming this provides a Retrofit instance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CharacterViewModel : ViewModel() {

    private val apiService = ApiClient.retrofit.create(ApiServicePokemon::class.java)

    var isLoading by mutableStateOf(true)
        private set

    var pokemonList by mutableStateOf(listOf<Pair<Int, String>>())
        private set

    fun fetchPokemonData() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempList = mutableListOf<Pair<Int, String>>()
            try {
                for (id in 1..10) { // Ajusta el rango según sea necesario
                    val response = apiService.getPokemonByIdOrName(id.toString()).execute()
                    if (response.isSuccessful) {
                        val pokemon = response.body()
                        val spriteUrl = pokemon?.sprites?.other?.official_artwork?.front_default
                            ?: "https://via.placeholder.com/150" // Placeholder para sprites nulos
                        tempList.add(id to spriteUrl)
                    } else {
                        println("Error fetching Pokémon with ID $id: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                println("Exception fetching Pokémon data: ${e.message}")
            } finally {
                withContext(Dispatchers.Main) {
                    pokemonList = tempList.toList()
                    isLoading = false
                }
            }
        }
    }
}
