package com.pberrueco.ud5_actividad.network

import com.pberrueco.ud5_actividad.network.model.AllGamesResponse
import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Query


interface GamesService {

    @GET("deals")
    suspend fun getAllGames(@Query("storeID") storeID: Int) : Response<AllGamesResponse>
}