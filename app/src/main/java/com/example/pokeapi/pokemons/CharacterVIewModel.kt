package com.example.pokeapi.pokemons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapi.apiconfig.ApiClient
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
                for (id in 1..200) { // Ajusta el rango según sea necesario
                    val response = apiService.getPokemonByIdOrName(id.toString()).execute()
                    if (response.isSuccessful) {
                        val pokemon = response.body()
                        val spriteUrl = pokemon?.sprites?.front_default // Cambia a front_default
                            ?: "" // Usa una cadena vacía si no hay sprite
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

