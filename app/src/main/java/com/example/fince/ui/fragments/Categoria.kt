package com.example.fince.ui.fragments

import android.os.Parcel
import android.os.Parcelable

class Categoria(nombre:String?, monto:Int?, descripcion:String?, tipo:String?): Parcelable {

    var nombre: String =""
    var monto: Int = 0
    var descripcion: String =""
    var tipo: String=""



    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),


        ) {
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Categoria> {
        override fun createFromParcel(parcel: Parcel): Categoria {
            return Categoria(parcel)
        }

        override fun newArray(size: Int): Array<Categoria?> {
            return arrayOfNulls(size)
        }
    }
}