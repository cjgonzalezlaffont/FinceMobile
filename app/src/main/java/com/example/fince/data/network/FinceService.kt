package com.example.fince.data.network


import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.ActivoModel
import com.example.fince.data.model.DataEntry
import com.example.fince.data.model.PortfolioModel
import com.example.fince.data.model.StockModel
import com.example.fince.data.model.Transaccion
import com.example.fince.data.model.TransaccionModel
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import com.example.fince.data.model.Venta
import com.example.fince.data.model.userLoginModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FinceService @Inject constructor(private val service: FinceApiClient) {

    suspend fun userRegister(user: UserRegisterModel): UserModel {
        return withContext(Dispatchers.IO) {
            val response = service.userRegister(user)

            response.body() ?: UserModel("","", "", "", "", 0,0,0)
        }
    }

    suspend fun userLogin(user: userLoginModel): UserModel {
        return withContext(Dispatchers.IO) {
            val response = service.userLogin(user)

            response.body() ?: UserModel("","", "", "", "", 0,0,0)
        }
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
    suspend fun buyAsset(userId: String, activo: ActivoModel): Int {
        return withContext(Dispatchers.IO) {
            val response = service.buyAsset(userId, activo)
            response.code()
        }
    }

    suspend fun sellAsset(userId: String, venta : Venta): Int {
        return withContext(Dispatchers.IO) {
            val response = service.buyAsset(userId, venta)
            response.code()
        }
    }
    /*
    const assetId = req.body.activoId;
    const quantity = req.body.cantidad;
    const salePrice = req.body.precioDeVenta;
    const userId = req.params.userId;
    */


    suspend fun getUserById(userId: String):UserModel{
        return withContext(Dispatchers.IO){
            val response = service.getUserById(userId)
            response.body() ?: UserModel("", "", "", "", "", 0,0,0)
        }
    }

    suspend fun getDataGraph(userId: String): List<DataEntry>{
        return withContext(Dispatchers.IO){
            val response = service.getDataGraph(userId)
            response.body() ?: emptyList<DataEntry>()
        }
    }

    suspend fun createTrtansaction(userId: String): Transaccion {
        return withContext(Dispatchers.IO){
            val response = service.createTransaction(userId)
            response.body() ?: Transaccion("","",0.toFloat(),0,"","",false,"")
            }
    }

    suspend fun getAllCategories(userId: String): List<CategoriaModel> {
        return withContext(Dispatchers.IO){
            val response = service.getAllCategories(userId)
            response.body() ?: emptyList<CategoriaModel>()
        }
    }


}
