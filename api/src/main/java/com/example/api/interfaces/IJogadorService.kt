package com.example.api.interfaces

import Jogador
import com.example.api.core.RequestResult
import com.example.api.models.responses.jogadores.CreateJogadorResponse
import requests.jogador.CreateJogadorRequest
import requests.jogador.UpdateJogadorRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IJogadorService {

    @GET("jogadores")
    fun getJogadores(): Call<List<Jogador>>

    @POST("jogadores")
    fun createJogador(@Body body: CreateJogadorRequest): Call<RequestResult.Success<CreateJogadorResponse>>

    @DELETE("jogadores/{id}")
    fun deleteJogador(@Path("id") id: String): Call<RequestResult.Success<com.example.api.models.responses.jogadores.DeleteJogadorResponse>>

    @PUT("jogadores/{id}")
    fun updateJogador(@Path("id") id: String, @Body body: UpdateJogadorRequest): Call<RequestResult.Success<com.example.api.models.responses.jogadores.UpdateJogadorResponse>>

    @GET("jogadores/{id}")
    fun getJogadorById(@Path("id") id: String): Call<RequestResult.Success<com.example.api.models.responses.jogadores.GetJogadorByIDResponse>>
}
