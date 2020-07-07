package com.ap8.appcriptomoedas.api

import android.content.Context
import android.net.ConnectivityManager
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

    fun hasConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }
}