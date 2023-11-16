package com.example.fince.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.data.model.StockModel
import com.example.fince.holders.StockHolder
import com.example.fince.listeners.OnViewItemClickedListener
import com.example.fince.databinding.ItemPanelGeneralBinding


class StockListAdapter (
    private val stockList: List<StockModel>,
    private val onItemClick: OnViewItemClickedListener,
    ): RecyclerView.Adapter<StockHolder>(){

    private var stockCount: Int = 0
    override fun getItemCount(): Int {
        stockCount = stockList.size
        return stockCount
    }

    fun setStockList(stockList: List<StockModel>) {
        (this.stockList as ArrayList).clear()
        this.stockList.addAll(stockList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockHolder {
        //No olvidarse de hacer un import de R
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPanelGeneralBinding.inflate(inflater, parent, false)
        return (StockHolder(binding))
    }

    override fun onBindViewHolder(holder: StockHolder, position: Int) {
        //puntero de la stock
        val stock = stockList.get(position)
        //seteo los datos de la stock en el holder
        holder.setSymbol(stock.simbolo)
        holder.setDescription(stock.descripcion)
        holder.setPercentageChange(stock.variacionPorcentual)
        holder.setLastPrice(stock.ultimoPrecio)
        holder.setInstrumentType(stock.tipo_instrumento)
        //set on click para pasar al detalle de la accion
        holder.getCardLayout().setOnClickListener{
            if(stock != null){
                onItemClick.onViewItemDetail(stock)
            }
        }
    }
}