package com.example.pokeapi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.pokeapi.ImagenCard.ImageCard
import com.example.pokeapi.pokemons.CharacterViewModel

@Composable
fun CharacterScreen(navController: NavController, viewModel: CharacterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val pokemonList = viewModel.pokemonList

    LaunchedEffect(Unit) {
        viewModel.fetchPokemonData()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                            modifier = Modifier.clickable {
                                navController.navigate("pokemon_detail/$id")
                            }
                        )
                    }
                }
            }
        }
    }
}
