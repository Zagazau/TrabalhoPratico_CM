package com.example.api.interfaces

import com.example.api.core.RequestResult
import com.example.api.models.responses.users.DeleteUserResponse
import com.example.api.models.responses.users.GetAllUsersResponse
import com.example.api.models.responses.users.GetUserByIDResponse
import com.example.api.models.responses.users.PatchUserResponse
import requests.user.FindUserRequest
import requests.user.PatchUserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface IUserService {

    @GET("users")
    fun getUsers(): Call<RequestResult.Success<com.example.api.models.responses.users.GetAllUsersResponse>>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: String): Call<RequestResult.Success<com.example.api.models.responses.users.GetUserByIDResponse>>

    @PATCH("users/{id}")
    fun patchUser(@Path("id") id: String, @Body body: PatchUserRequest): Call<RequestResult.Success<com.example.api.models.responses.users.PatchUserResponse>>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: String): Call<RequestResult.Success<com.example.api.models.responses.users.DeleteUserResponse>>
}
