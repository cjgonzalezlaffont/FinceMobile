package com.example.fince.data.network

import com.example.fince.data.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FinceService @Inject constructor(private val service:FinceApiClient) {

    suspend fun userRegister() : UserModel {
        return withContext(Dispatchers.IO) {
            val response = service.userRegister()
            response.body() ?: UserModel("","","") //ACA NO ME SUENA HACER ESTO, PREGUNTAR
        }
    }
}