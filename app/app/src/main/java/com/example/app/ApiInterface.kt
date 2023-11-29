package com.example.app

import com.example.app.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @POST("/login")
    fun loginUser(@Body inputs: HashMap<String, String>): Call<User>

    @GET("/users")
    fun getUsersList(): Call<List<User>>

    @GET("/users/{id}")
    fun getUniqueUser(@Path("id") id: Int): Call<User>
}