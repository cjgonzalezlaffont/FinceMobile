package com.example.fince.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.data.model.StockModel
import com.example.fince.holders.StockHolder
import com.example.fince.listeners.OnViewItemClickedListener
import com.example.fince.R


class StockListAdapter (
    private val stockList: List<StockModel>?,
    private val onItemClick: OnViewItemClickedListener,
    ): RecyclerView.Adapter<StockHolder>(){

    private var stockCount: Int = 0
    override fun getItemCount(): Int {
        if(stockCount != null){
            if (!stockList.isNullOrEmpty()) {
                stockCount = stockList.size
            }
        }
        return stockCount
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockHolder {
        //No olvidarse de hacer un import de R
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_panel_general, parent, false)
        return (StockHolder(view))
    }

    override fun onBindViewHolder(holder: StockHolder, position: Int) {
        //puntero de la stock
        val stock = stockList?.get(position)
        //seteo los datos de la stock en el holder
        holder.setSimbol(stock?.simbolo.toString())

        //set on click para pasar al detalle de la accion
        holder.getCardLayout().setOnClickListener{
            if(stock != null){
                onItemClick.onViewItemDetail(stock)
            }
        }
    }
}