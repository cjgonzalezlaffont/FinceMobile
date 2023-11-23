package com.example.fince.ui.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.fince.R
import com.example.fince.data.model.Transaccion
import com.example.fince.databinding.FragmentTransaccionDialogBinding
import com.example.fince.ui.viewmodel.TransaccionViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransaccionDialogFragment : DialogFragment() {

    private var _binding: FragmentTransaccionDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private val transaccionViewModel: TransaccionViewModel by viewModels({ requireParentFragment() })

    companion object {
        private lateinit var tran : Transaccion

        // Método para crear una nueva instancia del fragmento con los detalles
        fun newInstance(transaccion : Transaccion): TransaccionDialogFragment {
            val fragment = TransaccionDialogFragment()
            tran = transaccion
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransaccionDialogBinding.inflate(inflater, container, false)
        view = binding.root
        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        val transaccion = tran
        val tipo = transaccion.tipo

        binding.textViewTitulo.text = transaccion.titulo
        binding.textViewMonto.text = transaccion.montoConsumido.toString()
        binding.textViewCategoria.text = transaccion.categoriaNombre
        binding.textViewFecha.text = transaccion.fecha

        if (transaccion.financiera) {
            binding.fragDiagTranBtnDelete.isEnabled = false
        }

        if (tipo == 1) {
            binding.textViewMonto.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        } else {
            binding.textViewMonto.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }

        if (!binding.fragDiagTranBtnDelete.isEnabled) {
            binding.fragDiagTranBtnDelete.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grey))
        }

        binding.fragDiagTranBtnCerrar.setOnClickListener{
            dismiss()
        }

        binding.fragDiagTranBtnDelete.setOnClickListener {
            transaccionViewModel.deleteTransaction(usuarioId, transaccion)
        }

        transaccionViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        transaccionViewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
            Snackbar.make(view, transaccionViewModel.responseLiveData.toString(), Snackbar.LENGTH_SHORT).show()
            dismiss()
        }

        return view
    }
    override fun onResume() {
        super.onResume()
        // Definir el ancho y alto fijo del diálogo
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // Ajusta el alto como desees
    }

}