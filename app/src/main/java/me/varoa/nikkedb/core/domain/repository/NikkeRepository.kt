package me.varoa.nikkedb.core.domain.repository

import kotlinx.coroutines.flow.Flow
import me.varoa.nikkedb.core.domain.model.Nikke

interface NikkeRepository {
    fun getNikkes(): Flow<Result<List<Nikke>>>
    fun getNikke(name: String): Flow<Result<Nikke>>
}
