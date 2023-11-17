package com.example.fince.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ActivoModel(
    @SerializedName("simbolo") val simbolo: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("cantidad") var cantidad: Int,
    @SerializedName("valorDeCompra") var valorDeCompra: Float,
    @SerializedName("fechaDeCompra") var fechaDeCompra: String,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("categoriaId") val categoriaId: String,
    @SerializedName("activoId") val activoId: String,
    @SerializedName("valorActual") var valorActual: Float,
    @SerializedName("variacion") val variacion: Float,
) : Parcelable {

    // Constructor para la creación de un objeto ActivoModel desde un Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readFloat()
    )

    // Método necesario para la creación de un objeto Parcelable
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(simbolo)
        parcel.writeString(nombre)
        parcel.writeInt(cantidad)
        parcel.writeFloat(valorDeCompra)
        parcel.writeString(fechaDeCompra)
        parcel.writeString(tipo)
        parcel.writeString(categoriaId)
        parcel.writeString(activoId)
        parcel.writeFloat(valorActual)
        parcel.writeFloat(variacion)
    }

    // Método necesario para la creación de un objeto Parcelable
    override fun describeContents(): Int {
        return 0
    }

    // Objeto companion para la creación de objetos Parcelable desde un Parcel
    companion object CREATOR : Parcelable.Creator<ActivoModel> {
        override fun createFromParcel(parcel: Parcel): ActivoModel {
            return ActivoModel(parcel)
        }

        override fun newArray(size: Int): Array<ActivoModel?> {
            return arrayOfNulls(size)
        }
    }
}