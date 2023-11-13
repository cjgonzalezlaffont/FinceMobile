package com.example.fince.holders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.databinding.FragmentPanelGeneralBinding
import com.example.fince.databinding.ItemPanelGeneralBinding
import org.w3c.dom.Text

class StockHolder(v:View): RecyclerView.ViewHolder(v) {

    private var _binding: ItemPanelGeneralBinding? = null
    private val binding get() = _binding!!
    fun getCardLayout():CardView{
        return binding.itemCardPanelGeneral
    }
    fun setSimbol(simbol: String?){
        val text: TextView = binding.itemTextViewPanelGeneralSimbol
        text.text = simbol
    }

}