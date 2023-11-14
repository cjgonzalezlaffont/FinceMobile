package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class Transaccion(
    @SerializedName("transaccionId") val transaccionId: String,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("montoConsumido") val montoConsumido: Float,
    @SerializedName("tipo") val tipo: Int,
    @SerializedName("categoriaNombre") val categoriaNombre: String,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("financiera") val financiera: Boolean,
    @SerializedName("categoriaId") val categoriaId: String,
    )