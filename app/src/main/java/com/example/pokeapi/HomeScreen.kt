package com.example.pokeapi

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokeapi.apiconfig.ApiClient
import com.example.pokeapi.pokemons.ApiServicePokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(navController: NavController) {
    // Estados para búsqueda y resultados
    val searchQuery = remember { mutableStateOf("") }
    val searchResult = remember { mutableStateOf("No Pokémon Found") }
    val pokemonImage = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo de Pokébola
        AsyncImage(
            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png",
            contentDescription = "Pokéball Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp)
        )

        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Buscar Pokémon") },
            modifier = Modifier.padding(16.dp)
        )

        // Botón de búsqueda
        Button(onClick = {
            coroutineScope.launch {
                searchResult.value = "Buscando: ${searchQuery.value}..."
                pokemonImage.value = null
                val apiService = ApiClient.retrofit.create(ApiServicePokemon::class.java)
                val response = withContext(Dispatchers.IO) {
                    apiService.getPokemonByIdOrName(searchQuery.value.lowercase()).execute()
                }
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    searchResult.value = "Name: ${pokemon?.name}\nTypes: ${pokemon?.types?.joinToString { it.type.name } ?: "Unknown"}"
                    pokemonImage.value = pokemon?.sprites?.other?.official_artwork?.front_default
                } else {
                    searchResult.value = "No Pokémon Found"
                }
            }
        }) {
            Text("Buscar")
        }

        // Mostrar resultado de búsqueda
        Text(
            text = searchResult.value,
            modifier = Modifier.padding(8.dp)
        )

        // Mostrar imagen del Pokémon (si está disponible)
        pokemonImage.value?.let {
            AsyncImage(
                model = it,
                contentDescription = "Pokémon Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 16.dp)
            )
        }

        // Botón para ir a la pantalla de perfil
        Button(
            onClick = { navController.navigate("profile_screen") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Go to Profile Screen")
        }
    }
}
