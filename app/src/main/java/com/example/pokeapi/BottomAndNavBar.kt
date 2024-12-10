package com.example.pokeapi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(){
    TopAppBar({ Text(stringResource(R.string.app_name)) })
}

@Composable
fun BottomNavBar(navController: NavController) {
    BottomAppBar(
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