package com.ap8.appcriptomoedas.api

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitConfig {
    lateinit var retrofit: Retrofit

    fun RetrofitConfig() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://www.mercadobitcoin.net/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    fun getMoedaService(): MoedaService {
        return retrofit.create(MoedaService::class.java)
    }
}