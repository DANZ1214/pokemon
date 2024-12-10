package com.example.pokeapi.apiconfig

import com.example.pokeapi.pokemons.Pokedex
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



// Clase para consultar la API
class ConsultarApi {

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"

        // Configuración de Retrofit
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Creación de la instancia de la API
        private val api = retrofit.create(Peticiones::class.java)

        // Función para realizar la consulta
        fun respuesta(id: String): modeloRetorno {
            val modelo = modeloRetorno() // Crear una instancia de modeloRetorno

            val call: Call<Pokedex> = api.consultar(id)

            call.enqueue(object : Callback<Pokedex> {
                override fun onResponse(call: Call<Pokedex>, response: Response<Pokedex>) {
                    if (response.isSuccessful) {
                        val pokedex = response.body()!!
                        modelo.id = pokedex.id.toString() // Asegúrate de que `id` es una propiedad en Pokedex
                        modelo.name = pokedex.name ?: ""
                        modelo.height = pokedex.height.toString() // Si `height` es un entero
                        modelo.frontDefault = pokedex.sprites?.frontDefault ?: ""
                    } else {
                        println("Error en la respuesta: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Pokedex>, t: Throwable) {
                    println("Error en la consulta: ${t.message}")
                }
            })

            return modelo // Retorna el modelo actualizado
        }
    }
}

