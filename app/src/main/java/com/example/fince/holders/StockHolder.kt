package com.example.fince.holders

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.databinding.ItemPanelGeneralBinding

class StockHolder(private val binding: ItemPanelGeneralBinding): RecyclerView.ViewHolder(binding.root) {
    private val startIndex: Int = 0
    private val endIndex: Int = 10
    fun getCardLayout():CardView{
        return binding.itemCardPanelGeneral
    }
    fun setSymbol(symbol: String?){
        binding.itemTextViewPanelGeneralSimbol.text = symbol
    }

    fun setDescription(description: String?){
        if (!description.isNullOrEmpty()){
            if (description.length > 10){
                binding.itemTextViewPanelGeneralDescripcion.text = description.substring(startIndex, endIndex)
            }else{
                binding.itemTextViewPanelGeneralDescripcion.text = description
            }
        }
    }

    fun setPercentageChange(percentageChange: Float?){
        binding.itemTextViewPanelGeneralVarPorcentual.text = percentageChange.toString() + "%"
    }

    fun setLastPrice (lastPrice: Float?){
        binding.itemTextViewPanelGeneralUltPrecio.text = lastPrice.toString()
    }

    fun setInstrumentType(instrumentType: String?){
        binding.itemTextViewPanelGeneralTipoInstrumento.text = instrumentType
    }

}