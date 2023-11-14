package com.example.fince.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.fince.databinding.ItemTransaccionBinding

class TransaccionHolder(private val binding: ItemTransaccionBinding): RecyclerView.ViewHolder(binding.root) {


    fun setNombre(nombre: String?) {
        binding.fragTranTxtViewNombre.text = nombre
    }

    fun setCategoria(categoria: String?) {
        binding.fragTranTxtViewCategoria.text = categoria
    }

    fun setMonto(monto: Float) {
        binding.fragTranTxtViewMonto.text = monto.toString()
    }

    fun setFecha(fecha: String?) {
        binding.fragTranTxtViewFecha.text = fecha
    }
}