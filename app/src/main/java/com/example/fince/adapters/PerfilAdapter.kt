package com.example.fince.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R
import com.example.fince.ui.fragmereturn.ElementoLista

class PerfilAdapter(private val elementos: ArrayList<ElementoLista>) : RecyclerView.Adapter<PerfilAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_perfil, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val elemento = elementos[position]
        holder.opcion.text = elemento.opcion
        holder.descripcion.text = elemento.descripcion
    }

    override fun getItemCount(): Int {
        return elementos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val opcion: TextView = itemView.findViewById(R.id.lblTitulo)
        val descripcion: TextView = itemView.findViewById(R.id.lblDescripcion)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(elementos[position].opcion)
                }
            }
        }
    }

    var onItemClick: ((String) -> Unit)? = null
}