package com.example.fince.holders

import android.content.Context
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R
import com.example.fince.databinding.ItemObjetivoBinding

class ObjetivoHolder(
    private val binding: ItemObjetivoBinding,
    private val context : Context
) : RecyclerView.ViewHolder(binding.root) {
    fun getFrameLayout(): FrameLayout {
        return binding.itemObjFrameLayout
    }

    fun setNombre(nombre: String?) {
        binding.fragObjTxtViewTitulo.text = nombre
    }

    fun setMonto(monto: String?){
        binding.fragObjTxtViewMonto.text = monto.toString()
    }

    fun setFecha(fecha: String?){
        binding.fragObjTxtViewFecha.text = fecha
    }

    fun setProgreso(progreso : Int){
        binding.fragObjProgressBarHorizontal.progress = progreso
        if (progreso == 100) {
            binding.fragObjTxtViewProgressLabel.text = "Completado!"
            binding.fragObjTxtViewProgressLabel.setTextColor((ContextCompat.getColor(context, R.color.green)))
        }

    }
}