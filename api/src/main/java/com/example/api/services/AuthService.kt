package com.example.api.services

import com.example.api.core.HttpClient
import com.example.api.core.RequestResult
import com.example.api.core.ResponseParser
import com.example.api.interfaces.IAuthService
import com.example.api.models.responses.auth.LoginResponse
import com.example.api.models.responses.auth.RegisterResponse
import okhttp3.Call
import requests.auth.LoginRequest
import requests.auth.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

class AuthService: HttpClient<IAuthService>(IAuthService::class.java) {
    suspend fun login(body: LoginRequest): RequestResult<LoginResponse> {
        return when (val response = request(api.login(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<LoginResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun register(body: RegisterRequest): RequestResult<RegisterResponse> {
        return when (val response = request(api.register(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<RegisterResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}
