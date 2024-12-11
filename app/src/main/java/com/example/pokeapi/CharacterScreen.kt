package com.example.pokeapi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokeapi.ImagenCard.ImageCard
import com.example.pokeapi.pokemons.CharacterViewModel

@Composable
fun CharacterScreen(navController: NavController, viewModel: CharacterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val pokemonList = viewModel.pokemonList

    LaunchedEffect(Unit) {
        viewModel.fetchPokemonData()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1647892591717-28c7fd63bb3f?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        when {
            viewModel.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            pokemonList.isNullOrEmpty() -> {
                Text("No Pokémon found!", modifier = Modifier.align(Alignment.Center))
            }
            else -> {
                LazyColumn {
                    items(pokemonList) { (id, imageUrl) ->
                        ImageCard(
                            image = imageUrl,
                            title = "Pokémon ID: $id",
                            modifier = Modifier
                                .clickable {
                                    navController.navigate("pokemon_detail/$id")
                                }
                                .padding(8.dp)
                                .fillMaxWidth()
                                .background(Color(0xFF1976D2)), // Fondo azul
                            titleColor = Color.White // Texto en blanco
                        )
                    }
                }
            }
        }
    }
}
