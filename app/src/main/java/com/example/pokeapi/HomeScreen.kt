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
import com.example.pokeapi.apiconfig.ApiClient
import com.example.pokeapi.pokemons.ApiServicePokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(navController: NavController) {

    val searchQuery = remember { mutableStateOf("") }
    val searchResult = remember { mutableStateOf<String>("No Pokémon Found") }
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
                        searchResult.value = """
                            Name: ${pokemon?.name ?: "Unknown"}
                            Types: ${pokemon?.types?.joinToString { it.type.name } ?: "Unknown"}
                        """.trimIndent()
                    } else {
                        searchResult.value = "Pokémon not found"
                    }
                } catch (e: Exception) {
                    searchResult.value = "Error: ${e.message}"
                }
            }
        }) {
            Text("Buscar")
        }


        Text(
            text = searchResult.value,
            modifier = Modifier.padding(16.dp)
        )


    }
}