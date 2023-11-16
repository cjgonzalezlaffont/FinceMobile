package com.example.fince.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.fince.R
import com.example.fince.data.model.Transaccion
import com.example.fince.databinding.FragmentPresupuestoBinding
import com.example.fince.databinding.FragmentTransaccionDialogBinding

class TransaccionDialogFragment : DialogFragment() {

    private var _binding: FragmentTransaccionDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    companion object {
        const val NOMBRE = "titulo"
        const val MONTO = "detalle"
        const val CATEGORIA = "categoria"
        const val FECHA = "fecha"
        const val TIPO : Int = 0

        // Método para crear una nueva instancia del fragmento con los detalles
        fun newInstance(transaccion : Transaccion): TransaccionDialogFragment {
            val fragment = TransaccionDialogFragment()
            val args = Bundle()
            args.putString(NOMBRE, transaccion.titulo)
            args.putString(MONTO, transaccion.montoConsumido.toString())
            args.putString(CATEGORIA, transaccion.categoriaNombre)
            args.putString(FECHA, transaccion.fecha)
            args.putInt(TIPO.toString(), transaccion.tipo)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransaccionDialogBinding.inflate(inflater, container, false)
        view = binding.root

        val tipo = arguments?.getInt(TIPO.toString())

        binding.textViewTitulo.text = arguments?.getString(NOMBRE)
        binding.textViewMonto.text = arguments?.getString(MONTO)
        binding.textViewCategoria.text = arguments?.getString(CATEGORIA)
        binding.textViewFecha.text = arguments?.getString(FECHA)

        if (tipo == 1) {
            binding.textViewMonto.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        } else {
            binding.textViewMonto.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
        binding.fragDiagTranBtnCerrar.setOnClickListener{
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