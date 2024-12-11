package com.example.pokeapi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar() {
    val backgroundColor = Color.Yellow // Amarillo estilo Pikachu
    val contentColor = Color.Black    // Negro para el texto del título

    val pokemonFont = FontFamily(
        Font(R.font.pokemon_solid) // Fuente personalizada
    )

    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = contentColor,
                fontSize = 24.sp, // Tamaño de fuente grande
                fontWeight = FontWeight.Bold, // Negrita para resaltar
                letterSpacing = 1.5.sp, // Espaciado entre letras
                fontFamily = pokemonFont // Aplica la fuente personalizada
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = contentColor
        )
    )
}

@Composable
fun BottomNavBar(navController: NavController) {
    BottomAppBar(
        containerColor = Color.Yellow, // Azul adecuado
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {
                    navController.navigate("home_screen")
                }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(onClick = {
                    navController.navigate("profile_screen")
                }) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
                }
                IconButton(onClick = {
                    navController.navigate("character_screen")
                }) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Characters")
                }
            }
        }
    )
}
