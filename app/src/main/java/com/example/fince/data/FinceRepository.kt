package com.example.fince.data


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
import com.example.fince.data.network.FinceService
import javax.inject.Inject

class FinceRepository @Inject constructor(
    private val remote: FinceService
) {
    suspend fun userRegister(user : UserModel) : UserModel {
        val response = remote.userRegister(user)
        return response
    }

    suspend fun userLogin(user : userLoginModel) : UserModel {
        val response = remote.userLogin(user)
        return response
    }

    suspend fun updateUser(user : UserModel) : UserModel {
        val response = remote.updateUser(user)
        return response
    }

    suspend fun getAllInstruments() : List<StockModel>{
        val response = remote.getAllInstruments()
        return response
    }

    suspend fun getCedears(): List<StockModel>{
        val response = remote.getCedears()
        return response
    }
    suspend fun getStocks(): List<StockModel>{
        val response = remote.getStocks()
        return response
    }
    suspend fun getGovernmentBonds():List<StockModel>{
        val response = remote.getGovernmentBonds()
        return response
    }

    suspend fun getCorporateBonds():List<StockModel>{
        val response = remote.getCorporateBonds()
        return response
    }
    suspend fun getInvestmentFund():List<StockModel>{
        val response = remote.getInvestmentFund()
        return response
    }


    suspend fun getAllTransactions(userId : String) : TransaccionModel{
        val response = remote.getAllTransactions(userId)
        return response
    }

    suspend fun getPortfolio(userId: String) : PortfolioModel {
        return remote.getPortfolio(userId)
    }

    suspend fun buyAsset(userId: String, activo: ActivoModel): String {
        return remote.buyAsset(userId, activo)
    }

    suspend fun sellAsset(userId: String, venta : Venta) : String {
        return remote.sellAsset(userId, venta)
    }

    suspend fun getUserById(userId: String): UserModel {
        val response = remote.getUserById(userId)
        return response
    }

    suspend fun createTransaction(userId : String, transaccion : Transaccion) : Transaccion {
        val response = remote.createTransaction(userId, transaccion)
        return response
    }

    suspend fun getDataGraph(userId: String): List<DataEntry>{
        val response = remote.getDataGraph(userId)
        return response
    }

    suspend fun getAllCategories(userId: String): List<CategoriaModel> {
        val response = remote.getAllCategories(userId)
        return response
    }

    suspend fun createCategorie(userId: String, categoria : CategoriaModel): SuccessfulModel? {
        val response = remote.createCategorie(userId, categoria)
        return response
    }

    suspend fun deleteTransaction(userId: String, tranId: Transaccion): SuccessfulModel? {
        val response = remote.deleteTransaction(userId, tranId)
        return response
    }

    suspend fun deleteCategorie(userId: String, catId: String): SuccessfulModel? {
        val response = remote.deleteCategorie(userId, catId)
        return response
    }

    suspend fun editCategorie(userId : String, categoriaReq: CategoriaModel): SuccessfulModel? {
        val response = remote.editCategorie(userId, categoriaReq)
        return response
    }

    suspend fun getAllObjetives(userId: String): ObjetivoResponse {
        val response = remote.getAllObjetives(userId)
        return response
    }

    suspend fun createObjetive(userId: String, objetive : ObjetivoModel): String {
        val response = remote.createObjetive(userId, objetive)
        return response
    }

    suspend fun editObjeetive(usuarioId: String, objetivo: ObjetivoModel): String? {
        val response = remote.editObjetive(usuarioId, objetivo)
        return response
    }

    suspend fun deleteObjective(userId: String, objetivoId: String?): SuccessfulModel? {
        val response = remote.deleteObjective(userId, objetivoId)
        return response
    }

    suspend fun validateMail(mail: String): String {
        val response = remote.validateMail(mail)
        return response
    }

    suspend fun sendAuthCode(mail : String) : SuccessfulModel? {
        val response = remote.sendAuthCode(mail)
        return response
    }

    suspend fun findUserByMail(mail: String): UserModel {
        val response = remote.findUserByMail(mail)
        return response
    }

    suspend fun getPrediction(userId: String): List<DataEntry>{
        return remote.getPrediction(userId)
    }

}