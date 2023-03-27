package me.varoa.nikkedb.core.data.remote.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NikkeJson(
    val id: String,
    val name: String,
    val url: String,
    val rarity: String,
    @SerialName("class") val classType: String,
    val weapon: String,
    val burst: String,
    val manufacturer: String,
    val squad: String,
    val description: String
)
