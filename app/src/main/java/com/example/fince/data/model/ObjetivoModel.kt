package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class ObjetivoModel(
    @SerializedName("objetivoId") val id : String,
    @SerializedName("nombre") val nombre : String,
    @SerializedName("monto") val monto: Float,
    @SerializedName("fecha") val fecha : String,
    @SerializedName("descripcion") val descripcion: String
)
