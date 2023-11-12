package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("userId") val userId: String,
    @SerializedName("token") val token: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("correo") val correo: String,
    @SerializedName("ingreso") val ingreso: Int,
    @SerializedName("egreso") val egreso: Int,
    @SerializedName("perfil") val perfil: Int,
)
