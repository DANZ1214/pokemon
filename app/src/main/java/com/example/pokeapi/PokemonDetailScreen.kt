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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.create

@Composable
fun PokemonDetailScreen(id: String) {
    // Variables de estado para almacenar los detalles del Pokémon
    val pokemonName = remember { mutableStateOf("Loading...") }
    val pokemonType = remember { mutableStateOf("Loading...") }

    // Efecto lanzado para realizar el llamado a la API
    LaunchedEffect(id) {
        val apiService = ApiClient.retrofit.create(ApiServicePokemon::class.java)
        try {
            // Llamado a la API en un hilo secundario
            val response = withContext(Dispatchers.IO) {
                apiService.getPokemonByIdOrName(id).execute()
            }
            if (response.isSuccessful) {
                val pokemon = response.body()
                // Actualización de las variables de estado con los datos del Pokémon
                pokemonName.value = pokemon?.name ?: "Unknown"
                pokemonType.value = pokemon?.types?.joinToString { it.type.name } ?: "Unknown"
            } else {
                pokemonName.value = "Error"
                pokemonType.value = "Error"
            }
        } catch (e: Exception) {
            // Manejo de errores
            pokemonName.value = "Error"
            pokemonType.value = "Error"
        }
    }

    // UI para mostrar los detalles del Pokémon
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
