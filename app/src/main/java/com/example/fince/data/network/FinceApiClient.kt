package com.example.fince.data.network

import com.example.fince.data.model.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface FinceApiClient {
    @GET("/api/users")
    suspend fun userRegister(): Response<UserModel>

    @GET("/api/users/login")
    suspend fun userLogin(): Response<UserModel>
}