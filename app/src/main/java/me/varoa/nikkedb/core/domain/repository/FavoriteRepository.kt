package me.varoa.nikkedb.core.domain.repository

import kotlinx.coroutines.flow.Flow
import me.varoa.nikkedb.core.domain.model.Nikke

interface FavoriteRepository {
    fun loadFavorites(): Flow<List<Nikke>>
    suspend fun addToFavorite(nikke: Nikke): Long
    suspend fun removeFromFavorite(nikke: Nikke): Int
    fun isNikkeInFavorites(name: String): Flow<Int>
}
