package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class DataEntry(
    @SerializedName("year") val year: String,
    @SerializedName("month") val month: String,
    @SerializedName("ingresos") var ingresos: Float,
    @SerializedName("egresos") var egresos: Float
)
