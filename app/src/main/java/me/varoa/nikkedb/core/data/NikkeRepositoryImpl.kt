package me.varoa.nikkedb.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import me.varoa.nikkedb.core.data.remote.SafeApiRequest
import me.varoa.nikkedb.core.data.remote.api.NikkeApiService
import me.varoa.nikkedb.core.data.remote.json.NikkeJson
import me.varoa.nikkedb.core.data.remote.wrapFlowApiCall
import me.varoa.nikkedb.core.domain.model.Nikke
import me.varoa.nikkedb.core.domain.repository.NikkeRepository
import javax.inject.Inject

class NikkeRepositoryImpl @Inject constructor(
    private val api: NikkeApiService
) : NikkeRepository, SafeApiRequest() {
    override fun getNikkes(): Flow<Result<List<Nikke>>> = wrapFlowApiCall {
        val data: List<Nikke> = apiRequest { api.getCharacters() }.map(NikkeJson::toModel)
        Result.success(data)
    }

    override fun getNikke(name: String): Flow<Result<Nikke>> = wrapFlowApiCall {
        val data: Nikke = apiRequest { api.getCharacter(name) }.toModel()
        Result.success(data)
    }
}
