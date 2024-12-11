package com.example.pokeapi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokeapi.apiconfig.ApiClient
import com.example.pokeapi.pokemons.ApiServicePokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PokemonDetailScreenShyny(id: String) {
    val pokemonName = remember { mutableStateOf("Loading...") }
    val pokemonType = remember { mutableStateOf("Loading...") }
    val pokemonImage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(id) {
        val apiService = ApiClient.retrofit.create(ApiServicePokemon::class.java)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.getPokemonByIdOrName(id).execute()
            }
            if (response.isSuccessful) {
                val pokemon = response.body()
                pokemonName.value = pokemon?.name ?: "Unknown"
                pokemonType.value = pokemon?.types?.joinToString { it.type.name } ?: "Unknown"
                pokemonImage.value = pokemon?.sprites?.front_shiny
            } else {
                pokemonName.value = "Error"
                pokemonType.value = "Error"
                pokemonImage.value = null
            }
        } catch (e: Exception) {
            pokemonName.value = "Error"
            pokemonType.value = "Error"
            pokemonImage.value = null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (pokemonImage.value != null) {
                AsyncImage(
                    model = pokemonImage.value,
                    contentDescription = "Image of ${pokemonName.value}",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("Loading Image...", textAlign = TextAlign.Center)
            }

            Text(
                text = """
                    Pokémon Details:
                    ID: $id
                    Name: ${pokemonName.value}
                    Type: ${pokemonType.value}
                """.trimIndent(),
                textAlign = TextAlign.Center
            )
        }
    }
}
