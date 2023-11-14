package com.example.fince.data.network

import com.example.fince.data.model.StockModel
import com.example.fince.data.model.TransaccionModel
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import com.example.fince.data.model.userLoginModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FinceApiClient {

    @POST("/api/users")
    suspend fun userRegister(@Body user : UserRegisterModel): Response<UserModel>

    @POST("/api/users/login")
    suspend fun userLogin(@Body user : userLoginModel): Response<UserModel>

    //Stocks
    @POST("/api/instruments/TODOS")
    suspend fun getAllInstruments() : Response<List<StockModel>>
    @POST("api/instruments/cedears")
    suspend fun  getCedears()
    @POST("api/instruments/acciones")
    suspend fun  getStocks()
    @POST("api/instruments/titulosPublicos")
    suspend fun  getGovernmentBonds()
    @POST("api/instruments/obligacionesNegociables")
    suspend fun  getCorporateBonds()
    @POST("api/instruments/FCI")
    suspend fun  getInvestmentFunds()

    @GET("/api/transactions/getTransactions/{userId}")
    suspend fun getAllTransactions(@Path("userId") userId : String) : Response<TransaccionModel>
}