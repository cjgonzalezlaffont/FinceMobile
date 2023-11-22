package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.fince.R
import com.example.fince.data.model.Transaccion
import com.example.fince.databinding.FragmentPresupuestoBinding
import com.example.fince.databinding.FragmentTransaccionDialogBinding
import com.example.fince.listeners.OnTransactionDeletedListener
import com.example.fince.ui.viewmodel.DialogTranViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransaccionDialogFragment : DialogFragment() {

    private var _binding: FragmentTransaccionDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private val dialogTranViewModel : DialogTranViewModel by viewModels()
    private var listener : OnTransactionDeletedListener? = null

    companion object {
        private lateinit var tran : Transaccion
        const val NOMBRE = "titulo"
        const val MONTO = "detalle"
        const val CATEGORIA = "categoria"
        const val FECHA = "fecha"
        const val TIPO : Int = 0
        const val ID = "idTran"

        // Método para crear una nueva instancia del fragmento con los detalles
        fun newInstance(transaccion : Transaccion): TransaccionDialogFragment {
            val fragment = TransaccionDialogFragment()
            val args = Bundle()
            args.putString(NOMBRE, transaccion.titulo)
            args.putString(MONTO, transaccion.montoConsumido.toString())
            args.putString(CATEGORIA, transaccion.categoriaNombre)
            args.putString(FECHA, transaccion.fecha)
            args.putInt(TIPO.toString(), transaccion.tipo)
            args.putString(ID, transaccion.transaccionId)
            tran = transaccion
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
        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        val tipo = arguments?.getInt(TIPO.toString())
        val idTran = arguments?.getString(ID)
        val transaccion = tran

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

        binding.fragDiagTranBtnDelete.setOnClickListener {
            dialogTranViewModel.borrarTransaccion(usuarioId, transaccion)
            listener?.onTransactionDeleted()
        }

        dialogTranViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        dialogTranViewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
            Snackbar.make(view, dialogTranViewModel.responseLiveData.toString(), Snackbar.LENGTH_SHORT).show()
            dismiss()
        }

        return view
    }
    override fun onResume() {
        super.onResume()
        // Definir el ancho y alto fijo del diálogo
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // Ajusta el alto como desees
    }

    fun setListener(listener: OnTransactionDeletedListener) {
        this.listener = listener
    }

}