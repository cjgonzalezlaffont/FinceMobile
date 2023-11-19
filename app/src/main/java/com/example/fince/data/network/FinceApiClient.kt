package com.example.fince.data.network


import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.ActivoModel
import com.example.fince.data.model.PortfolioModel
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
    @POST("/api/instruments/cedears")
    suspend fun  getCedears(): Response<List<StockModel>>
    @POST("/api/instruments/acciones")
    suspend fun  getStocks(): Response<List<StockModel>>
    @POST("/api/instruments/titulosPublicos")
    suspend fun  getGovernmentBonds(): Response<List<StockModel>>
    @POST("/api/instruments/obligacionesNegociables")
    suspend fun  getCorporateBonds(): Response<List<StockModel>>
    @POST("/api/instruments/FCI")
    suspend fun  getInvestmentFunds(): Response<List<StockModel>>

    @GET("/api/transactions/getTransactions/{userId}")
    suspend fun getAllTransactions(@Path("userId") userId : String) : Response<TransaccionModel>

    @GET("/api/portfolio/getPortfolio/{userId}")
    suspend fun getPortfolio(@Path("userId") userId: String) : Response<PortfolioModel>

    @POST("api/portfolio/buyAsset/{userId}")
    suspend fun buyAsset(@Path("userId") userId : String, @Body activo : ActivoModel) : Response<Void>

    @GET("/api/users/{userId}")
    suspend fun getUserById(@Path("userId") userId : String) : Response<UserModel>

    @GET("/api/categories/{userId}")
    suspend fun getAllCategories(@Path("userId") userId : String) : Response<List<CategoriaModel>>

}