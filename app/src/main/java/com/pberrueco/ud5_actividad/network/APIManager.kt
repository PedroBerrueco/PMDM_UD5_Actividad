package com.pberrueco.ud5_actividad.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIManager {

    //Parsea los Json a data class
    private val converter = GsonConverterFactory.create()

    //Asigna el nivel de detalle que queremos por consola de las peticiones
    private val logginIntercerptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    //Carga el Interceptor
    private val client = OkHttpClient.Builder().addInterceptor(logginIntercerptor).build()

    //Instancia de Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cheapshark.com/api/1.0/") //Tiene que terminar siempre en /
        .client(client)
        .addConverterFactory(converter)
        .build()
    //Para Llamar a las peticiones de red del servicio
    val service: GamesService by lazy {
        retrofit.create(GamesService::class.java)
    }
}