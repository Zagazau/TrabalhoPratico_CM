package com.example.api.interfaces

import com.example.api.core.RequestResult
import com.example.api.models.responses.jogos.CreateJogosResponse
import com.example.api.models.responses.jogos.DeleteJogosResponse
import com.example.api.models.responses.jogos.GetAllJogosResponse
import com.example.api.models.responses.jogos.GetJogosByIDResponse
import com.example.api.models.responses.jogos.UpdateJogosResponse
import requests.jogo.CreateJogoRequest
import requests.jogo.UpdateJogoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IJogoService {

    @GET("jogos")
    fun getJogos(): Call<RequestResult.Success<com.example.api.models.responses.jogos.GetAllJogosResponse>>

    @POST("jogos")
    fun createJogo(@Body body: CreateJogoRequest): Call<RequestResult.Success<com.example.api.models.responses.jogos.CreateJogosResponse>>

    @DELETE("jogos/{id}")
    fun deleteJogo(@Path("id") id: String): Call<RequestResult.Success<com.example.api.models.responses.jogos.DeleteJogosResponse>>

    @PUT("jogos/{id}")
    fun updateJogo(@Path("id") id: String, @Body body: UpdateJogoRequest): Call<RequestResult.Success<com.example.api.models.responses.jogos.UpdateJogosResponse>>

    @GET("jogos/{id}")
    fun getJogoById(@Path("id") id: String): Call<RequestResult.Success<com.example.api.models.responses.jogos.GetJogosByIDResponse>>
}
