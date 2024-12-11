package com.example.pokeapi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokeapi.apiconfig.ApiClient
import com.example.pokeapi.pokemons.ApiServicePokemon
import retrofit2.create

@Composable
fun PokemonDetailScreen(id: String, name: String = "Loading...", type: String = "Loading...") {
    val pokemonName = remember { mutableStateOf(name) }
    val pokemonType = remember { mutableStateOf(type) }

    LaunchedEffect(id) {
        val apiService = ApiClient.retrofit.create(ApiServicePokemon::class.java)
        val response = apiService.getPokemonByIdOrName(id).execute()
        if (response.isSuccessful) {
            val pokemon = response.body()
            pokemonName.value = pokemon?.name ?: "Unknown"
            pokemonType.value = pokemon?.types?.joinToString { it.type.name } ?: "Unknown"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = """
                Pokémon Details:
                ID: $id
                Name: ${pokemonName.value}
                Type: ${pokemonType.value}
            """.trimIndent(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
