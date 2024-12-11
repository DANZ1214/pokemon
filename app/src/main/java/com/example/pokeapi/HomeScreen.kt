package com.example.pokeapi

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokeapi.ImagenCard.ImageCard
import com.example.pokeapi.apiconfig.ApiClient
import com.example.pokeapi.pokemons.ApiServicePokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val searchQuery = remember { mutableStateOf("") }
    val searchResult = remember { mutableStateOf<PokemonSearchResult?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo
        AsyncImage(
            model = "https://cdn.leonardo.ai/users/7226c195-c506-4153-88d6-a0b77a400353/generations/8a3bcccf-f3e0-43df-add7-9dfad94a8bed/Leonardo_Phoenix_a_dark_and_blurred_background_with_a_predomin_2.jpg", // Cambia esta URL por la que desees usar
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                label = {
                    Text(
                        text = "Buscar Pokémon",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(16.dp),
                colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        val apiService = ApiClient.retrofit.create(ApiServicePokemon::class.java)
                        try {
                            val response = withContext(Dispatchers.IO) {
                                apiService.getPokemonByIdOrName(searchQuery.value).execute()
                            }
                            if (response.isSuccessful) {
                                val pokemon = response.body()
                                searchResult.value = PokemonSearchResult(
                                    name = pokemon?.name ?: "Unknown",
                                    types = pokemon?.types?.joinToString { it.type.name } ?: "Unknown",
                                    imageUrl = pokemon?.sprites?.front_default ?: ""
                                )
                            } else {
                                searchResult.value = null
                            }
                        } catch (e: Exception) {
                            searchResult.value = null
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow, // Azul del TopAppBar
                    contentColor = Color.White // Texto blanco
                )
            ) {
                Text(
                    text = "Buscar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            searchResult.value?.let { result ->
                ImageCard(
                    image = result.imageUrl,
                    title = "Name: ${result.name}\nTypes: ${result.types}",
                    modifier = Modifier.padding(16.dp)
                )
            } ?: Text(
                text = "No Pokémon Found",
                color = Color.White, // Texto blanco
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

data class PokemonSearchResult(
    val name: String,
    val types: String,
    val imageUrl: String
)
