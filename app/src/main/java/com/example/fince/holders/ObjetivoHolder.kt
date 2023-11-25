package com.example.fince.holders

import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.databinding.ItemObjetivoBinding

class ObjetivoHolder(private val binding: ItemObjetivoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun getFrameLayout(): FrameLayout {
        return binding.itemObjFrameLayout
    }

    fun setNombre(nombre: String?) {
        binding.fragObjTxtViewTitulo.text = nombre
    }

    fun setMonto(monto: Float?){
        binding.fragObjTxtViewMonto.text = monto.toString()
    }

    fun setFecha(fecha: String){
        binding.fragObjTxtViewFecha.text = fecha
    }
}