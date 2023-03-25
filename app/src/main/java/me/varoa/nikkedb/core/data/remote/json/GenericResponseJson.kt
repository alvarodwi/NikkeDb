package me.varoa.nikkedb.core.data.remote.json

import kotlinx.serialization.Serializable

@Serializable
data class GenericResponseJson(
    val error: Boolean,
    val message: String
)
