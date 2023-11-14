package com.example.fince.holders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.databinding.FragmentPanelGeneralBinding
import com.example.fince.databinding.ItemPanelGeneralBinding
import org.w3c.dom.Text

class StockHolder(private val binding: ItemPanelGeneralBinding): RecyclerView.ViewHolder(binding.root) {

    fun getCardLayout():CardView{
        return binding.itemCardPanelGeneral
    }
    fun setSimbol(simbol: String?){
        binding.itemTextViewPanelGeneralSimbol.text = simbol
    }

}