package com.ap8.appcriptomoedas.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.mercadobitcoin.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getMoedaService(): MoedaService {
        return retrofit.create(MoedaService::class.java)
    }
}