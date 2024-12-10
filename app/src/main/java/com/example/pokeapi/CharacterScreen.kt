package com.example.pokeapi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class CharacterScreen : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var txtConsulta: EditText

    private val service: PokemonService by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ... (resto de tu código de inicialización)

        btnLogin.setOnClickListener { consultarApi() }
    }

    private fun consultarApi() {
        val pokemonName = txtConsulta.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pokemon = service.getPokemon(pokemonName)
                // Actualizar la UI con los datos del Pokémon
                withContext(Dispatchers.Main) {
                    // ... (mostrar los datos en la interfaz de usuario)
                }
            } catch (e: Exception) {
                // Manejar errores
            }
        }
    }
}

interface PokemonService {
    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Pokemon
}

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    // ... otros campos según la API
)