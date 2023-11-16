package com.example.fince.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R

data class ElementoAccionnes(val empresa:String,val iniciales:String,val cantidad:String,val precio:String);

class AccionesAdapter (private val elementos: ArrayList<ElementoAccionnes>) : RecyclerView.Adapter<AccionesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccionesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_acciones, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return elementos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val elemento = elementos[position]
        holder.titulo.text = elemento.empresa
        holder.subtitulo.text = elemento.iniciales
        holder.numero.text = elemento.cantidad
        holder.precio.text = elemento.precio
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.fragCatTxtViewTitulo)
        val subtitulo: TextView = itemView.findViewById(R.id.subtitulo)
        val numero: TextView = itemView.findViewById(R.id.numero)
        val precio: TextView = itemView.findViewById(R.id.precio)
    }
}