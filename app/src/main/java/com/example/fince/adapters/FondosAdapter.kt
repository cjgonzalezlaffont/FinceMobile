package com.example.fince.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R

data class ElementoFondo(val fondo:String,val iniciales:String,val precio:String)

class FondosAdapter (private val elementos: ArrayList<ElementoFondo>) : RecyclerView.Adapter<FondosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FondosAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_fondos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FondosAdapter.ViewHolder, position: Int) {
        val elemento = elementos[position]
        holder.titulo.text = elemento.fondo
        holder.subtitulo.text = elemento.iniciales
        holder.precio.text = elemento.precio
    }
    override fun getItemCount(): Int {
        return elementos.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.titulo)
        val subtitulo: TextView = itemView.findViewById(R.id.subtitulo)
        val precio: TextView = itemView.findViewById(R.id.precio)
    }
}
