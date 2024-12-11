package com.example.pokeapi.ImagenCard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImageCard(image: String, title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            if (image.isNotEmpty()) {
                AsyncImage(
                    model = image,
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Tamaño fijo para evitar problemas
                )
            } else {
                Text(
                    text = "No Image Available",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = title,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

