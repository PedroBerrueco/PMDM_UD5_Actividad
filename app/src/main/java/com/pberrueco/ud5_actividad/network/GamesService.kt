package com.pberrueco.ud5_actividad.network

import com.pberrueco.ud5_actividad.network.model.AllGamesResponse
import com.pberrueco.ud5_actividad.network.model.AllGamesResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface GamesService {

    @GET("deals?storeID=1")
    suspend fun getAllGames() : Response<AllGamesResponse>
}