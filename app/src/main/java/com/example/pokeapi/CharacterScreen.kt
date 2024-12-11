package com.example.pokeapi.pokemons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokeapi.ImagenCard.ImageCard
import androidx.navigation.NavController

@Composable
fun CharacterScreen(navController: NavController, viewModel: CharacterViewModel = viewModel()) {
    val pokemonList = viewModel.pokemonList

    // Cargar los datos al iniciar
    LaunchedEffect(Unit) {
        viewModel.fetchPokemonData()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        when {
            viewModel.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            pokemonList.isEmpty() -> {
                Text(
                    text = "No Pokémon found!",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(pokemonList) { (id, imageUrl) ->
                        // Hacer clic en la imagen para navegar al detalle
                        ImageCard(
                            image = imageUrl,
                            title = "Pokémon ID: $id",
                            modifier = Modifier.clickable {
                                navController.navigate("pokemon_detail_screen/$id")
                            }

                        )
                    }
                }
            }
        }
    }
}