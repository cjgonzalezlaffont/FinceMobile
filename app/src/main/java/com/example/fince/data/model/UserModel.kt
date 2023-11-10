package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("userId") val userId: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("token") val token: String
)
