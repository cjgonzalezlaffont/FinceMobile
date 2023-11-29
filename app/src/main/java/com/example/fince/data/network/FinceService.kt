package com.example.fince.data.network


import android.util.Log
import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.ActivoModel
import com.example.fince.data.model.ErrorResponse
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
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class FinceService @Inject constructor(private val service: FinceApiClient) {

    suspend fun userRegister(user: UserModel): UserModel {
        return handleAPICall { service.userRegister(user) }
    }

    suspend fun userLogin(user: userLoginModel): UserModel {
        return handleAPICall { service.userLogin(user) }
    }

    suspend fun updateUser(user : UserModel): UserModel {
        return handleAPICall { service.updateUser(user.userId, user) }
    }

    suspend fun validateMail(mail: String): String {
        return handleAPICall { service.validateMail(mail) }
    }

    suspend fun sendAuthCode(mail: String): SuccessfulModel {
        return handleAPICall { service.sendAuthCode(mail) }
    }

    suspend fun findUserByMail(mail: String): UserModel {
        return handleAPICall { service.findUserByMail(mail) }
    }

    suspend fun  getAllInstruments(): List<StockModel> {
        return withContext(Dispatchers.IO){
            val response = service.getAllInstruments()
            response.body() ?: emptyList<StockModel>()
        }
    }

    suspend fun getCedears(): List<StockModel>{
        return withContext(Dispatchers.IO){
            val response = service.getCedears()
            response.body() ?: emptyList<StockModel>()
        }
    }
    suspend fun getStocks(): List<StockModel>{
        return withContext(Dispatchers.IO){
            val response = service.getStocks()
            response.body() ?: emptyList<StockModel>()
        }
    }
    suspend fun getGovernmentBonds(): List<StockModel>{
        return withContext(Dispatchers.IO){
            val response = service.getGovernmentBonds()
            response.body() ?: emptyList<StockModel>()
        }
    }
    suspend fun getCorporateBonds(): List<StockModel>{
        return withContext(Dispatchers.IO){
            val response = service.getCorporateBonds()
            response.body() ?: emptyList<StockModel>()
        }
    }
    suspend fun getInvestmentFund(): List<StockModel>{
        return withContext(Dispatchers.IO){
            val response = service.getInvestmentFunds()
            response.body() ?: emptyList<StockModel>()
        }
    }

    suspend fun getAllTransactions(userId : String): TransaccionModel {
        return withContext(Dispatchers.IO){
            val response = service.getAllTransactions(userId)
            response.body() ?: TransaccionModel(emptyList<Transaccion>(), 0.toFloat(),0.toFloat())
        }
    }

    suspend fun getPortfolio(userId : String): PortfolioModel {
        return withContext(Dispatchers.IO){
            val response = service.getPortfolio(userId)
            response.body() ?: PortfolioModel(emptyList<ActivoModel>(),0.toFloat())
        }
    }
    suspend fun buyAsset(userId: String, activo: ActivoModel): String {
        return handleAPICall { service.buyAsset(userId, activo) }
    }

    suspend fun sellAsset(userId: String, venta : Venta): String {
        return handleAPICall { service.sellAsset(userId, venta) }
    }

    suspend fun getUserById(userId: String): UserModel {
        return handleAPICall { service.getUserById(userId) }
    }

    suspend fun createTransaction(userId: String, transaccion: Transaccion): Transaccion {
        return handleAPICall { service.createTransaction(userId, transaccion) }
    }

    suspend fun getDataGraph(userId: String): List<DataEntry>{
        return withContext(Dispatchers.IO){
            val response = service.getDataGraph(userId)
            response.body() ?: emptyList<DataEntry>()
        }
    }

    suspend fun getAllCategories(userId: String): List<CategoriaModel> {
        return withContext(Dispatchers.IO){
            val response = service.getAllCategories(userId)
            response.body() ?: emptyList<CategoriaModel>()
        }
    }

    suspend fun createCategorie(userId: String, categoria : CategoriaModel): SuccessfulModel? {
        return handleAPICall { service.createCategorie(userId, categoria) }
    }

    suspend fun deleteTransaction(userId: String, tranId: Transaccion): SuccessfulModel? {
        return handleAPICall { service.deleteTransaction(userId, tranId) }
    }

    suspend fun deleteCategorie(userId: String, catId: String): SuccessfulModel? {
        return handleAPICall { service.deleteCategorie(userId, catId) }
    }

    suspend fun editCategorie(userId : String, categoriaReq: CategoriaModel): SuccessfulModel? {
        return handleAPICall { service.editCategorie(userId, categoriaReq.id, categoriaReq) }
    }

    suspend fun getAllObjetives(userId: String): ObjetivoResponse {
        return handleAPICall { service.getAllObjetives(userId) }
    }

    suspend fun createObjetive(userId: String, objetive: ObjetivoModel): String {
        return handleAPICall { service.createObjetive(userId, objetive) }
    }

    suspend fun editObjetive(userId: String, objetive: ObjetivoModel): String? {
        return handleAPICall { service.editObjetive(userId, objetive) }
    }

    suspend fun deleteObjective(userId: String, objetivoId: String?): SuccessfulModel? {
        return handleAPICall { service.deleteObjective(userId, objetivoId) }
    }

    suspend fun <T> handleAPICall(call: suspend () -> Response<T>): T {
        return try {
            val response = call.invoke()

            if (response.isSuccessful) {
                response.body() ?: throw IOException("Empty response body")
            } else {
                val errorBody = response.errorBody()?.string()

                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)

                Log.e("API Error", "Error code: ${response.code()}, Message: ${errorResponse.error}")

                throw IOException(errorResponse.error ?: "Unknown error")
            }
        } catch (e: Exception) {
            Log.e("API Error", "Message: ${e.message}")
            throw IOException("${e.message}")
        }
    }


}
