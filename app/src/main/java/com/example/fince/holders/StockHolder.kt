package com.example.fince.holders

import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R
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
        binding.itemTextViewPanelGeneralDescripcion.text = description
    }

    fun setPercentageChange(percentageChange: Float?){
        percentageChange?.let {
            binding.itemTextViewPanelGeneralVarPorcentual.text = "$percentageChange%"

            val colorRes = when {
                percentageChange > 0 -> R.color.green
                percentageChange < 0 -> R.color.red
                else -> android.R.color.black
            }

            val textColor = ContextCompat.getColor(binding.root.context, colorRes)
            binding.itemTextViewPanelGeneralVarPorcentual.setTextColor(textColor)
        }
    }

    fun setLastPrice (lastPrice: Float?){
        binding.itemTextViewPanelGeneralUltPrecio.text = lastPrice.toString()
    }

    fun setInstrumentType(instrumentType: String?){
        binding.itemTextViewPanelGeneralTipoInstrumento.text = instrumentType
    }

}