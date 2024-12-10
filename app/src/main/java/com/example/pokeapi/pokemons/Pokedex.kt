package com.example.pokeapi.pokemons

class Pokedex {
    var id: String = ""
    var name: String = ""
    var height: String = ""
    var sprites = Sprites()
    var frontDefault = sprites.frontDefault

    fun getFrontDefault() = frontDefault
    fun setFrontDefault(frontDefault: String) { this.frontDefault = frontDefault }

    fun getSprites() = sprites
    fun setSprites(sprites: Sprites) { this.sprites = sprites }

    fun getHeight() = height
    fun setHeight(height: String) { this.height = height }

    fun getName() = name
    fun setName(name: String) { this.name = name }

    fun getId() = id
    fun setId(id: String) { this.id = id }
}