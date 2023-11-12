package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class UserRegisterModel(
    @SerializedName("nombre") val userName: String,
    @SerializedName("apellido") val userLastName: String,
    @SerializedName("correo") val mail: String,
    @SerializedName("contrasena") val password: String
)
