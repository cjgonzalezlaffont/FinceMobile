package com.example.fince.data

import com.example.fince.data.model.ActivoModel
import com.example.fince.data.model.PortfolioModel
import com.example.fince.data.model.StockModel
import com.example.fince.data.model.TransaccionModel
import com.example.fince.data.model.User
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import com.example.fince.data.model.userLoginModel
import com.example.fince.data.network.FinceService
import javax.inject.Inject

class FinceRepository @Inject constructor(
    private val remote: FinceService
) {
    suspend fun userRegister(user : UserRegisterModel) : UserModel {
        val response = remote.userRegister(user)
        return response
    }

    suspend fun userLogin(user : userLoginModel) : UserModel {
        val response = remote.userLogin(user)
        return response
    }

    suspend fun getAllInstruments() : List<StockModel>{
        val response = remote.getAllInstruments()
        return response
    }

    suspend fun getAllTransactions(userId : String) : TransaccionModel{
        val response = remote.getAllTransactions(userId)
        return response
    }

    suspend fun getPortfolio(userId: String) : PortfolioModel {
        return remote.getPortfolio(userId)
    }

    suspend fun buyAsset(userId: String, activo: ActivoModel): Int {
        return remote.buyAsset(userId, activo)
    }

    suspend fun sellAsset(userId: String, activo: ActivoModel): Int {
        return remote.buyAsset(userId, activo)
    }

    suspend fun getUserById(userId: String): UserModel{
        val response = remote.getUserById(userId)
        return response
    }
}