package com.example.fince.holders
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.databinding.ItemCarteraBinding
class CarteraHolder (private val binding: ItemCarteraBinding): RecyclerView.ViewHolder(binding.root) {

    fun getCardLayout():CardView{
        return binding.itemCardCartera
    }
    fun setSimbol(simbol: String?){
        binding.itemCarteraTxtViewCardSimbol.text = simbol
    }

    fun setCantidad(cantidad: String?){
        binding.itemCarteraTxtViewCardCantidad.text = cantidad
    }

    fun setPrecioDeCompra(precioDeCompra: String?){
        binding.itemCarteraTxtViewCardPrecioDeCompra.text = precioDeCompra
    }

    fun setPrecioActual(precioActual: String?){
        binding.itemCarteraTxtViewCardPrecioActual.text = precioActual
    }

}