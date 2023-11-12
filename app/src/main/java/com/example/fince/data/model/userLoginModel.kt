package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class userLoginModel(
    @SerializedName("correo") val correo: String,
    @SerializedName("contrasena") val contrasena: String
    )
