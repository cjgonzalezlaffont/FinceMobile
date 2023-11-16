package com.example.fince.holders

import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R
import com.example.fince.databinding.ItemTransaccionBinding

class TransaccionHolder(private val binding: ItemTransaccionBinding): RecyclerView.ViewHolder(binding.root) {

    private val frameLayout: FrameLayout = itemView.findViewById(R.id.itemTranFrameLayout)

    fun getFrameLayout(): FrameLayout {
        return frameLayout
    }

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