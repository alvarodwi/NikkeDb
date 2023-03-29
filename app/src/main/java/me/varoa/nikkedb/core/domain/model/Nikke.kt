package me.varoa.nikkedb.core.domain.model

import me.varoa.nikkedb.core.data.remote.api.ApiConfig

data class Nikke(
    val id: String,
    val name: String,
    val url: String,
    val rarity: String,
    val classType: String,
    val weapon: String,
    val burst: String,
    val manufacturer: String,
    val squad: String,
    val description: String
)

fun Nikke.generateSmallImageUrl() = "${ApiConfig.IMG_URL}/characters/si_c${id}_00_s.webp"

fun Nikke.generateFullImageUrl() = "${ApiConfig.IMG_URL}/characters/c${id}_00.webp"
