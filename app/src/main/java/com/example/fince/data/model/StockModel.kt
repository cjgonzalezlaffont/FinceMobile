package com.example.fince.data.model
import com.google.gson.annotations.SerializedName
data class StockModel(
    @SerializedName("simbolo") val simbolo: String,
    @SerializedName("ultimoPrecio") val ultimoPrecio: Float,
    @SerializedName("variacionPorcentual") val variacionPorcentual: Float,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("tipo_instrumento") val tipo_instrumento: String,
    )

/*
Estructura response stocks
        "simbolo": "AAL",
        "ultimoPrecio": 5204.5,
        "variacionPorcentual": 3.24,
        "descripcion": "AMERICAN AIRLINES GROUP INC.",
        "tipo_instrumento": "cedears"*/