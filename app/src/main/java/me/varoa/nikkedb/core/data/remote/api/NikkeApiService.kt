package me.varoa.nikkedb.core.data.remote.api

import me.varoa.nikkedb.core.data.remote.json.NikkeJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NikkeApiService {
    @GET("characters")
    suspend fun getCharacters(): Response<List<NikkeJson>>

    @GET("characters/:name")
    suspend fun getCharacter(
        @Path("name") name: String
    ): Response<NikkeJson>
}
