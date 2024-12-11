package com.example.pokeapi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = "https://cdn.leonardo.ai/users/7226c195-c506-4153-88d6-a0b77a400353/generations/8a3bcccf-f3e0-43df-add7-9dfad94a8bed/Leonardo_Phoenix_a_dark_and_blurred_background_with_a_predomin_2.jpg", // Cambia esta URL por la que desees usar
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

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
                        .padding(8.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray),
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
                style = TextStyle(
                    color = Color.White,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}
