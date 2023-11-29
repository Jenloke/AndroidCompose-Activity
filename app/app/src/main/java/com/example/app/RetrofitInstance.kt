package com.example.app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val localServer = "http://10.0.2.2:3000/"
    val api : ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(localServer)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}