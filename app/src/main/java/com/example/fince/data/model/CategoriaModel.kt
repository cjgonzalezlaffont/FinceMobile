package com.example.fince.data.model
import com.google.gson.annotations.SerializedName


data class CategoriaModel(

    @SerializedName("nombre") val nombre:String,
    @SerializedName("montoMax") val montoMax: Float,
    @SerializedName("tipo") val tipo: Int,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("montoConsumido") val montoConsumido: Float,


    )