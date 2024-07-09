package com.example.api.models.responses.auth

data class LoginResponse(
    val token: String,
    val userId: String,
    val userName: String
)