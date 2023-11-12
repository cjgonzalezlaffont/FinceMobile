package com.example.fince.data.network

import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import com.example.fince.data.model.userLoginModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FinceApiClient {

    @POST("/api/users")
    suspend fun userRegister(@Body user : UserRegisterModel): Response<UserModel>

    @POST("/api/users/login")
    suspend fun userLogin(@Body user : userLoginModel): Response<UserModel>

}