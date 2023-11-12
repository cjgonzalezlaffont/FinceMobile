package com.example.fince.data

import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import com.example.fince.data.network.FinceService
import javax.inject.Inject

class FinceRepository @Inject constructor(
    private val remote: FinceService
) {
    suspend fun userRegister(user : UserRegisterModel) : UserModel {
        val response = remote.userRegister(user)
        return response
    }
}