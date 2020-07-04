package com.ap8.appcriptomoedas.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface MoedaService {
    @GET("api/{moeda}/ticker")
    fun getMoeda(@Path("moeda") moeda: String): Call<MoedaAPI>
}