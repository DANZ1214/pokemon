package com.example.pokeapi

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapi.ImagenCard.ImageCard
import com.example.pokeapi.pokemons.CharacterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Composable
fun CharacterScreen(viewModel: CharacterViewModel = CharacterViewModel()) {
    // State to observe the list of Pokémon
    val pokemonList = viewModel.pokemonList

    // Fetch data when screen is displayed
    LaunchedEffect(Unit) {
        viewModel.fetchPokemonData()
    }

    if (viewModel.isLoading) {
        Text("Loading Pokémon...", modifier = Modifier.padding(16.dp))
    } else {
        // Display the list of Pokémon
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            pokemonList.forEach { (id, imageUrl) ->
                ImageCard(
                    image = imageUrl, // Pokémon sprite URL
                    title = "Pokémon ID: $id"
                )
            }
        }
    }
}
