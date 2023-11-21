package com.example.fince.data.model
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName


data class CategoriaModel(

    @SerializedName("id") val id: String,
    @SerializedName("nombre") val nombre:String,
    @SerializedName("montoMax") val montoMax: Float,
    @SerializedName("tipo") val tipo: Int,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("montoConsumido") val montoConsumido: Float,
    @SerializedName("financiera")  val financiera : Boolean,

    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readBoolean(),

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeFloat(montoMax)
        parcel.writeInt(tipo)
        parcel.writeString(descripcion)
        parcel.writeFloat(montoConsumido)
        parcel.writeBoolean(financiera)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoriaModel> {
        override fun createFromParcel(parcel: Parcel): CategoriaModel {
            return CategoriaModel(parcel)
        }

        override fun newArray(size: Int): Array<CategoriaModel?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString() : String{
        return nombre
    }


}
