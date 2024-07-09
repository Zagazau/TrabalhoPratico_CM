package com.example.api.interfaces

import com.example.api.core.RequestResult
import requests.torneio.CreateTorneioRequest
import requests.torneio.UpdateTorneioRequest
import responses.torneios.CreateTorneiosResponse
import responses.torneios.DeleteTorneioResponse
import responses.torneios.GetAllTorneiosResponse
import responses.torneios.GetTorneioByIDResponse
import responses.torneios.UpdateTorneioResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ITorneioService {

    @GET("torneios")
    fun getTorneios(): Call<RequestResult.Success<GetAllTorneiosResponse>>

    @POST("torneios")
    fun createTorneio(@Body body: CreateTorneioRequest): Call<RequestResult.Success<CreateTorneiosResponse>>

    @DELETE("torneios/{id}")
    fun deleteTorneio(@Path("id") id: String): Call<RequestResult.Success<DeleteTorneioResponse>>

    @PUT("torneios/{id}")
    fun updateTorneio(@Path("id") id: String, @Body body: UpdateTorneioRequest): Call<RequestResult.Success<UpdateTorneioResponse>>

    @GET("torneios/{id}")
    fun getTorneioById(@Path("id") id: String): Call<RequestResult.Success<GetTorneioByIDResponse>>
}
