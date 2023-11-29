package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class SuccessfulModel(
    @SerializedName("message") val message: String,
    @SerializedName("category") val category: CategoriaModel,
    @SerializedName("authCode") val authCode : Int
)
