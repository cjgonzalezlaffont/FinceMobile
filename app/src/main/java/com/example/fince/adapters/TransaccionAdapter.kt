package com.example.fince.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R
import com.example.fince.data.model.Transaccion
import com.example.fince.data.model.TransaccionModel
import com.example.fince.databinding.ItemTransaccionBinding
import com.example.fince.holders.TransaccionHolder
import com.example.fince.listeners.OnViewItemClickedListenerTran

class TransaccionAdapter(

    private val transaccionList: MutableList<Transaccion>,
    private val onItemClick: OnViewItemClickedListenerTran,

    ) : RecyclerView.Adapter<TransaccionHolder>() {

    fun setTransactionList(transaccionList: List<Transaccion>) {
        (this.transaccionList as ArrayList).clear()
        this.transaccionList.addAll(transaccionList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaccionHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransaccionBinding.inflate(inflater, parent, false)
        return TransaccionHolder(binding)
    }

    private var transaccionCount : Int = 0
    override fun getItemCount(): Int {
        transaccionCount = transaccionList.size
        return transaccionCount
    }

    override fun onBindViewHolder(holder: TransaccionHolder, position: Int) {
        val transaccion = transaccionList.get(position)
        holder.setNombre(transaccion.titulo)
        holder.setCategoria(transaccion.categoriaNombre)
        holder.setFecha(transaccion.fecha)
        holder.setMonto(transaccion.montoConsumido)
        holder.getFrameLayout().setOnClickListener{
            if (transaccion != null) {
                onItemClick.onViewItemDetail(transaccion)
            }
        }
    }

}