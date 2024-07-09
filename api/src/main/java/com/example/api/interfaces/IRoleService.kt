package com.example.api.interfaces

import com.example.api.core.RequestResult
import com.example.api.models.responses.roles.GetAllRolesResponse
import com.example.api.models.responses.roles.GetRoleByIDResponse
import requests.role.FindRoleRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IRoleService {

    @GET("roles")
    fun getRoles(): Call<RequestResult.Success<com.example.api.models.responses.roles.GetAllRolesResponse>>

    @GET("roles/{id}")
    fun getRoleById(@Path("id") id: String): Call<RequestResult.Success<com.example.api.models.responses.roles.GetRoleByIDResponse>>
}
