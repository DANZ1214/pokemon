package com.example.pokeapi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavControllerComponent(startDestination: String = "home_screen") {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopNavBar() },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable("home_screen") {
                    HomeScreen(navController)
                }
                composable("profile_screen") {
                    ProfileScreen()
                }
                composable("character_screen") {
                    CharacterScreen()
                }
            }
        }
    }
}
