package com.example.pokeapi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokeapi.ImagenCard.ImageCard
import com.example.pokeapi.apiconfig.ApiClient
import com.example.pokeapi.pokemons.ApiServicePokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(navController: NavController) {
    val searchQuery = remember { mutableStateOf("") }
    val searchResult = remember { mutableStateOf<PokemonSearchResult?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text(text = "Buscar Pokémon") },
            modifier = Modifier.padding(16.dp)
        )


        Button(onClick = {
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
        }) {
            Text("Buscar")
        }

        searchResult.value?.let { result ->
            ImageCard(
                image = result.imageUrl,
                title = "Name: ${result.name}\nTypes: ${result.types}",
                modifier = Modifier.padding(16.dp)
            )
        } ?: Text(
            text = "No Pokémon Found",
            modifier = Modifier.padding(16.dp)
        )
    }
}

data class PokemonSearchResult(
    val name: String,
    val types: String,
    val imageUrl: String
)
