package com.example.api.interfaces

import com.example.api.core.RequestResult
import com.example.api.models.responses.auth.LoginResponse
import com.example.api.models.responses.auth.RegisterResponse
import retrofit2.Call
import requests.auth.LoginRequest
import requests.auth.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<RequestResult.Success<LoginResponse>>

    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RequestResult.Success<RegisterResponse>>
}