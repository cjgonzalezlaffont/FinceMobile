package com.example.fince.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.Transaccion
import com.example.fince.databinding.ItemCategoriaBinding
import com.example.fince.holders.CategoriaHolder
import com.example.fince.listeners.OnViewItemClickedListenerCat

class CategoriaAdapter (

    private val categoriaList: MutableList<CategoriaModel>,
    private val onItemClick: OnViewItemClickedListenerCat

    ) : RecyclerView.Adapter<CategoriaHolder>() {

    fun setCategoriaList(categoriaList: List<CategoriaModel>) {
        (this.categoriaList as ArrayList).clear()
        this.categoriaList.addAll(categoriaList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoriaBinding.inflate(inflater, parent, false)
        return CategoriaHolder(binding)
    }


    private var categoriaCount : Int = 0
    override fun getItemCount(): Int {
        categoriaCount = categoriaList.size
        return categoriaCount

    }

    override fun onBindViewHolder(holder: CategoriaHolder, position: Int) {
        val categoria = categoriaList.get(position)
        holder.setNombre(categoria.nombre)
        holder.setMontoMax(categoria.montoMax)
        holder.setTipo(categoria.tipo)
        holder.getFrameLayout().setOnClickListener {
            if (categoria != null) {
                onItemClick.onViewItemDetail(categoria)
            }
        }
    }


}













