package com.example.fince.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("userId") val userId: String?,
    @SerializedName("token") val token: String?,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("apellido") val apellido: String?,
    @SerializedName("correo") val correo: String?,
    @SerializedName("contrasena") var contrasena: String?,
    @SerializedName("ingreso") val ingreso: Int,
    @SerializedName("egreso") val egreso: Int,
    @SerializedName("perfil") val perfil: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(token)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeString(correo)
        parcel.writeString(contrasena)
        parcel.writeInt(ingreso)
        parcel.writeInt(egreso)
        parcel.writeInt(perfil)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}
