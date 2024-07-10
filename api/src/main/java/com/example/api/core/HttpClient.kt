package com.example.api.core

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

open class HttpClient<T>(service: Class<T>) {
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/api/"
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    var api: T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(service)

    suspend fun <T> request(call: Call<T>): RequestResult<*> {
        return try {
            val response = call.awaitResponse()
            if (response.isSuccessful) {
                RequestResult.Success(
                    code = response.code(),
                    message = response.message(),
                    data = response.body()
                )
            } else {
                RequestResult.Error(
                    code = response.code(),
                    message = "Request failed: ${response.message()}",
                    errors = mapOf()
                )
            }
        } catch (err: Exception) {
            RequestResult.Error(
                code = 500,
                message = "API request failed: ${err.message}",
                errors = mapOf()
            )
        }
    }

}