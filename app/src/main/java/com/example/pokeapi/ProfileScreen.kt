package com.example.pokeapi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.pokeapi.ImagenCard.ImageCard
import com.example.pokeapi.pokemons.CharacterViewModel
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

val customFontFamily2 = FontFamily(
    Font(R.font.modular_amplitude)
)



@Composable
fun ProfileScreen(navController: NavController, viewModel: CharacterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val pokemonList = viewModel.pokemonListShyny

    LaunchedEffect(Unit) {
        viewModel.fetchPokemonDataShyny()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AsyncImage(
            model = "https://cdn.leonardo.ai/users/7226c195-c506-4153-88d6-a0b77a400353/generations/8a3bcccf-f3e0-43df-add7-9dfad94a8bed/Leonardo_Phoenix_a_dark_and_blurred_background_with_a_predomin_2.jpg",
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
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
                            title = "Pokemon ID: $id",
                            modifier = Modifier
                                .clickable {
                                    navController.navigate("pokemon_detail_shyny/$id")
                                }
                                .padding(8.dp)
                                .fillMaxWidth()
                                .background(Color.Yellow),
                            titleColor = Color.Black,
                            titleFont = customFontFamily
                        )
                    }
                }
            }
        }
    }
}


