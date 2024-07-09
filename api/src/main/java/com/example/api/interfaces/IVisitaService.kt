package com.example.api.interfaces

import com.example.api.core.RequestResult
import com.example.api.models.responses.visitas.CreateVisitaResponse
import com.example.api.models.responses.visitas.DeleteVisitaResponse
import com.example.api.models.responses.visitas.GetAllVisitasResponse
import com.example.api.models.responses.visitas.GetVisitaByIDResponse
import com.example.api.models.responses.visitas.UpdateVisitaResponse
import requests.visitas.CreateVisitaRequest
import requests.visitas.UpdateVisitaRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IVisitaService {

    @GET("visitas")
    fun getVisitas(): Call<RequestResult.Success<com.example.api.models.responses.visitas.GetAllVisitasResponse>>

    @POST("visitas")
    fun createVisita(@Body body: CreateVisitaRequest): Call<RequestResult.Success<com.example.api.models.responses.visitas.CreateVisitaResponse>>

    @DELETE("visitas/{id}")
    fun deleteVisita(@Path("id") id: String): Call<RequestResult.Success<com.example.api.models.responses.visitas.DeleteVisitaResponse>>

    @PUT("visitas/{id}")
    fun updateVisita(@Path("id") id: String, @Body body: UpdateVisitaRequest): Call<RequestResult.Success<com.example.api.models.responses.visitas.UpdateVisitaResponse>>

    @GET("visitas/{id}")
    fun getVisitaById(@Path("id") id: String): Call<RequestResult.Success<com.example.api.models.responses.visitas.GetVisitaByIDResponse>>
}
