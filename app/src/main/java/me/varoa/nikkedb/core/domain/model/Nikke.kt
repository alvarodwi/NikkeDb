package me.varoa.nikkedb.core.domain.model

data class Nikke(
    val id: String,
    val name: String,
    val rarity: String,
    val classType: String,
    val weapon: String,
    val burst: String,
    val manufacturer: String,
    val squad: String,
    val description: String
)
