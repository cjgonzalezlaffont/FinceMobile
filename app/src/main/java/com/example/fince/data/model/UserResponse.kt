package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("correo") val correo: String,
    @SerializedName("ingreso") val ingreso: Int,
    @SerializedName("egreso") val egreso: Int,
    @SerializedName("perfil") val perfil: Int,
    @SerializedName("contrasena") val contrasena: String
)
