package com.example.fince.data.network

import android.util.Log
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
}
