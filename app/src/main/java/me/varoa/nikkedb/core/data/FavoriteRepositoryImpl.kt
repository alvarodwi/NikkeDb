package me.varoa.nikkedb.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.varoa.nikkedb.core.data.local.dao.FavoriteDao
import me.varoa.nikkedb.core.data.local.entity.FavoriteEntity
import me.varoa.nikkedb.core.domain.model.Nikke
import me.varoa.nikkedb.core.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao
) : FavoriteRepository {
    override fun loadFavorites(): Flow<List<Nikke>> =
        dao.getAll().map { list -> list.map(FavoriteEntity::toModel) }

    override suspend fun addToFavorite(nikke: Nikke) =
        dao.insert(nikke.toEntity())

    override suspend fun removeFromFavorite(nikke: Nikke) =
        dao.delete(nikke.toEntity())

    override fun isNikkeInFavorites(url: String): Flow<Int> =
        dao.isItemWithUrlExists(url)
}
