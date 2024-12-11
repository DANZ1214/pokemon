package com.example.pokeapi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    // estado para el cuadro de búsqueda
    val searchQuery = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Este pal cuadro de búsqueda xd
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text(text = "Buscar Pokémon") },
            modifier = Modifier.padding(16.dp)
        )

        // muestra el texto de búsqueda actual
        Text(text = "Buscando: ${searchQuery.value}", modifier = Modifier.padding(8.dp))

        // boton para navegar a la pantalla de perfil
        Button(onClick = {
            navController.navigate("profile_screen")
        }) {
            Text("Gogogo to Profile Screen")
        }
    }
}