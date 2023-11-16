package com.example.fince.data.model
import com.google.gson.annotations.SerializedName


data class CategoriaModel(

    @SerializedName("nombre") val nombre:String,
    @SerializedName("montoMax") val montoMax: Int,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("montoConsumido") val montoConsumido: String,


    )