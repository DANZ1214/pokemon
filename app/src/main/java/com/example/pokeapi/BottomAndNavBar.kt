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
    val backgroundColor = Color.Yellow
    val contentColor = Color.Black

    val pokemonFont = FontFamily(
        Font(R.font.pokemon_solid)
    )

    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = contentColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                fontFamily = pokemonFont
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
        containerColor = Color.Yellow,
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
