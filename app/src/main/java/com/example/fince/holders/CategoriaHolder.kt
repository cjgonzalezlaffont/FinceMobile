package com.example.fince.holders
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.databinding.ItemCategoriaBinding


class CategoriaHolder(private val binding: ItemCategoriaBinding): RecyclerView.ViewHolder(binding.root){

    fun getFrameLayout(): FrameLayout {
        return binding.itemCatFrameLayout
    }

    fun setNombre(nombre: String?) {
        binding.fragCatTxtViewTitulo.text = nombre
    }

    fun setMontoMax(montoMax: Float?){
        binding.fragCatTxtViewMontoMax.text = montoMax.toString()
    }

    fun setTipo(tipo: Int?){
        var tipoString = ""

        if (tipo == 1){
            tipoString = "Ingreso"
        } else {
            tipoString = "Egreso"
        }

        binding.fragCatTxtViewTipo.text = tipoString
    }



}