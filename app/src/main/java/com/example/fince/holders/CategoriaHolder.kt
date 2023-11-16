package com.example.fince.holders
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.databinding.ItemCategoriaBinding


class CategoriaHolder(private val binding: ItemCategoriaBinding): RecyclerView.ViewHolder(binding.root){

    fun setNombre(nombre: String?) {
        binding.fragCatTxtViewTitulo.text = nombre
    }

    fun setMontoMax(montoMax: Int?){
        binding.fragCatTxtViewMontoMax.text = montoMax.toString()
    }

    fun setTipo(tipo: String?){
        binding.fragCatTxtViewTipo.text = tipo
    }



}