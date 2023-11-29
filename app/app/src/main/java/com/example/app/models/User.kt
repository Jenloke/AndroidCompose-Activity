package com.example.app.models

data class User(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val favorite_food: String,
    val email: String,
    val password: String
)
