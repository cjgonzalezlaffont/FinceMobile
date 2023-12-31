package com.example.fince.data.network


import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.ActivoModel
import com.example.fince.data.model.DataEntry
import com.example.fince.data.model.ObjetivoModel
import com.example.fince.data.model.ObjetivoResponse
import com.example.fince.data.model.PortfolioModel
import com.example.fince.data.model.StockModel
import com.example.fince.data.model.SuccessfulModel
import com.example.fince.data.model.Transaccion
import com.example.fince.data.model.TransaccionModel
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import com.example.fince.data.model.Venta
import com.example.fince.data.model.userLoginModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FinceApiClient {

    @POST("/api/users")
    suspend fun userRegister(@Body user : UserModel): Response<UserModel>

    @POST("/api/users/login")
    suspend fun userLogin(@Body user : userLoginModel): Response<UserModel>

    @PUT("/api/users/{userId}")
    suspend fun updateUser(@Path("userId") userId : String?, @Body user : UserModel): Response<UserModel>

    @GET("/api/users/verifyEmail/{email}")
    suspend fun validateMail(@Path("email") email : String) : Response<String>

    @POST("/api/users/sendAuthCode/{email}")
    suspend fun sendAuthCode(@Path("email") email : String) : Response<SuccessfulModel>

    @GET("/api/users/findUserByMail/{email}")
    suspend fun findUserByMail(@Path("email") email : String) : Response<UserModel>

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
    suspend fun buyAsset(@Path("userId") userId : String, @Body activo : ActivoModel) : Response<String>

    @PUT("api/portfolio/sellAsset/{userId}")
    suspend fun sellAsset(@Path("userId") userId : String, @Body venta : Venta) : Response<String>

    @GET("/api/users/{userId}")
    suspend fun getUserById(@Path("userId") userId : String) : Response<UserModel>

    @GET("/api/transactions/getDataGraph/{userId}")
    suspend fun getDataGraph(@Path("userId") userId : String) : Response<List<DataEntry>>

    @POST("/api/transactions/createTransaction/{userId}")
    suspend fun createTransaction(@Path("userId") userId: String, @Body transaccion : Transaccion): Response<Transaccion>

    @GET("/api/categories/{userId}")
    suspend fun getAllCategories(@Path("userId") userId : String) : Response<List<CategoriaModel>>

    @POST("/api/categories/{userId}")
    suspend fun createCategorie(@Path("userId") userId: String, @Body categoria : CategoriaModel): Response<SuccessfulModel>

    @POST("/api/transactions/deleteTransaction/{userId}")
    suspend fun deleteTransaction( @Path("userId") userId: String, @Body transaccion: Transaccion) : Response<SuccessfulModel>

    @DELETE("/api/categories/delete/{userId}/{categoryId}")
    suspend fun deleteCategorie(@Path("userId") userId : String, @Path("categoryId") categoryId : String): Response<SuccessfulModel>

    @PUT("/api/categories/update/{userId}/{categoryId}")
    suspend fun editCategorie(@Path("userId") userId : String, @Path("categoryId") categoryId : String, @Body categoria : CategoriaModel) : Response<SuccessfulModel>

    @GET("/api/objectives/{userId}")
    suspend fun getAllObjetives(@Path("userId") userId : String) : Response<ObjetivoResponse>

    @POST("/api/objectives/newObjective/{userId}")
    suspend fun createObjetive(@Path("userId") userId : String, @Body objetive : ObjetivoModel) : Response<String>

    @PUT("/api/objectives/updateObjective/{userId}")
    suspend fun editObjetive(@Path("userId") userId : String, @Body objetive : ObjetivoModel) : Response<String>

    @DELETE("/api/objectives/deleteObjective/{userId}/{objectiveId}")
    suspend fun deleteObjective(@Path("userId") userId : String, @Path("objectiveId") objectiveId : String?) : Response<SuccessfulModel>

    @GET("/api/transactions/getPrediction/{userId}")
    suspend fun getPrediction(@Path("userId") userId : String) : Response<List<DataEntry>>
}