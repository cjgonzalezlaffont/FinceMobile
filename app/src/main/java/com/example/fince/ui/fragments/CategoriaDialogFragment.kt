package com.example.fince.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.fince.R
import com.example.fince.data.model.CategoriaModel
import com.example.fince.databinding.FragmentCategoriaDialogBinding
import com.example.fince.databinding.FragmentTransaccionDialogBinding

class CategoriaDialogFragment : DialogFragment(){

    private var _binding: FragmentCategoriaDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    companion object {
        const val NOMBRE = "titulo"
        const val MONTO: Float = 0.0F
        const val TIPO : Int = 0
        const val DESCRIPCION = "descripcion"

        // Método para crear una nueva instancia del fragmento con los detalles
        fun newInstance(categoria : CategoriaModel): CategoriaDialogFragment {
            val fragment = CategoriaDialogFragment()
            val args = Bundle()
            args.putString(NOMBRE, categoria.nombre)
            args.putString(MONTO.toString(), categoria.montoMax.toString())
            args.putString(TIPO.toString(), categoria.tipo.toString())
            args.putString(DESCRIPCION, categoria.descripcion)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriaDialogBinding.inflate(inflater, container, false)
        view = binding.root

        val tipo = arguments?.getInt(TIPO.toString())

        binding.textViewTitulo.text = arguments?.getString(NOMBRE)
        binding.textViewMonto.text = arguments?.getString(MONTO.toString())
        binding.textViewDescripcion.text = arguments?.getString(DESCRIPCION)


        if (tipo == 1) {
            binding.textViewTipo.text = "ingreso"
        } else {
            binding.textViewMonto.text = "egreso"
        }


        return view
    }
    override fun onResume() {
        super.onResume()
        // Definir el ancho y alto fijo del diálogo
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // Ajusta el alto como desees
    }

}
