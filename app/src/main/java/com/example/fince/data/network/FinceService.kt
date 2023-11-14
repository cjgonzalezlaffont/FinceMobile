package com.example.fince.data.network

import com.example.fince.data.model.StockModel
import com.example.fince.data.model.Transaccion
import com.example.fince.data.model.TransaccionModel
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
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

    suspend fun getAllTransactions(userId : String): TransaccionModel {
        return withContext(Dispatchers.IO){
            val response = service.getAllTransactions(userId)
            response.body() ?: TransaccionModel(emptyList<Transaccion>(), 0.toFloat(),0.toFloat())
        }
    }
}
