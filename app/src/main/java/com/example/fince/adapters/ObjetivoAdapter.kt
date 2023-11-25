package com.example.fince.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.data.model.ObjetivoModel
import com.example.fince.databinding.ItemObjetivoBinding
import com.example.fince.holders.ObjetivoHolder
import com.example.fince.listeners.OnViewItemClickedListenerObj

class ObjetivoAdapter(
    private val objetivoList: MutableList<ObjetivoModel>,
    private val onItemClick: OnViewItemClickedListenerObj
) : RecyclerView.Adapter<ObjetivoHolder>() {

    fun setObjetivoList(categoriaList: List<ObjetivoModel>) {
        (this.objetivoList as ArrayList).clear()
        this.objetivoList.addAll(categoriaList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjetivoHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemObjetivoBinding.inflate(inflater, parent, false)
        return ObjetivoHolder(binding)
    }

    private var objetivoCount : Int = 0
    override fun getItemCount(): Int {
        objetivoCount = objetivoList.size
        return objetivoCount
    }

    override fun onBindViewHolder(holder: ObjetivoHolder, position: Int) {
        val objetivo = objetivoList.get(position)
        holder.setNombre(objetivo.nombre)
        holder.setMonto(objetivo.monto)
        holder.setFecha(objetivo.fecha)
        holder.getFrameLayout().setOnClickListener {
            if (objetivo != null) {
                onItemClick.onViewItemDetail(objetivo)
            }
        }
    }
}