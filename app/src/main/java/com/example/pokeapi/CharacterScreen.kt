package com.example.pokeapi

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
import com.example.pokeapi.pokemons.CharacterViewModel

@Composable
fun CharacterScreen(viewModel: CharacterViewModel = viewModel()) {
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
                // Mostrar un indicador de carga en el centro de la pantalla
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            pokemonList.isEmpty() -> {
                // Mostrar un mensaje si no hay datos
                Text(
                    text = "No Pokémon found!",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                // Mostrar la lista de Pokémon usando LazyColumn
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(pokemonList) { (id, imageUrl) ->
                        ImageCard(
                            image = imageUrl,
                            title = "Pokémon ID: $id"
                        )
                    }
                }
            }
        }
    }
}
