package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.fince.R
import com.example.fince.data.model.CategoriaModel
import com.example.fince.databinding.FragmentCategoriaDialogBinding
import com.example.fince.databinding.FragmentTransaccionDialogBinding
import com.example.fince.listeners.OnEditCategorieListener
import com.example.fince.ui.viewmodel.CategoriaViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriaDialogFragment : DialogFragment(){

    private var _binding: FragmentCategoriaDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private  val categoriaViewModel: CategoriaViewModel by viewModels({ requireParentFragment() })
    private var listener: OnEditCategorieListener? = null
    companion object {

        private lateinit var categoria : CategoriaModel

        // Método para crear una nueva instancia del fragmento con los detalles
        fun newInstance(categoria : CategoriaModel): CategoriaDialogFragment {
            val fragment = CategoriaDialogFragment()
            this.categoria = categoria
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriaDialogBinding.inflate(inflater, container, false)
        view = binding.root

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        val tipo = categoria.tipo

        binding.textViewTitulo.text = categoria.nombre
        binding.textViewMonto.text = categoria.montoMax.toString()
        binding.textViewDescripcion.text = categoria.descripcion

        if (tipo == 1) {
            binding.textViewTipo.text = "Ingreso"
        } else {
            binding.textViewTipo.text = "Egreso"
        }

        binding.fragDiagCatBtnDelete.setOnClickListener {
            categoriaViewModel.deleteCategorie(usuarioId, categoria)
        }

        binding.fragDiagCatBtnEditar.setOnClickListener {
            listener?.onEditCategorie(categoria)
            dismiss()
        }

        categoriaViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            if (categoriaViewModel.errorLiveData.value != "") {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }

        categoriaViewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
            Snackbar.make(view, categoriaViewModel.responseLiveData.value.toString(), Snackbar.LENGTH_SHORT).show()
            dismiss()
        }

        return view
    }
    override fun onResume() {
        super.onResume()
        // Definir el ancho y alto fijo del diálogo
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // Ajusta el alto como desees
    }

    fun setListener(listener : OnEditCategorieListener){
        this.listener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        categoriaViewModel.clearError()
        categoriaViewModel.errorLiveData.removeObservers(viewLifecycleOwner)
        categoriaViewModel.responseLiveData.removeObservers(viewLifecycleOwner)
        _binding = null
    }

}
