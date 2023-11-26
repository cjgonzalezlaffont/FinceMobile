package com.example.fince.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ObjetivoModel(
    @SerializedName("objetivoId") val id : String?,
    @SerializedName("nombre") val nombre : String?,
    @SerializedName("monto") val monto: Double,
    @SerializedName("fecha") val fecha : String?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("progreso") val progreso: Float
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeDouble(monto)
        parcel.writeString(fecha)
        parcel.writeString(descripcion)
        parcel.writeFloat(progreso)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ObjetivoModel> {
        override fun createFromParcel(parcel: Parcel): ObjetivoModel {
            return ObjetivoModel(parcel)
        }

        override fun newArray(size: Int): Array<ObjetivoModel?> {
            return arrayOfNulls(size)
        }
    }
}
