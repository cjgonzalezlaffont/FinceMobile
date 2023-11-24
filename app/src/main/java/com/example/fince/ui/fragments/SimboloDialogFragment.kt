package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.fince.R
import com.example.fince.data.model.ActivoModel
import com.example.fince.databinding.FragmentSimboloDialogBinding
import com.example.fince.ui.viewmodel.TradingViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimboloDialogFragment : DialogFragment() {


    private var _binding: FragmentSimboloDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private var precioVentaFlag = false
    private val tradingviewModel: TradingViewModel by viewModels()

    companion object {
        private lateinit var activo : ActivoModel

        // Método para crear una nueva instancia del fragmento con los detalles
        fun newInstance(activo : ActivoModel): SimboloDialogFragment {
            val fragment = SimboloDialogFragment()
            this.activo = activo
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSimboloDialogBinding.inflate(inflater, container, false)
        view = binding.root

        var activity = requireActivity()
        var viewActivity = activity.findViewById<View>(android.R.id.content)

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        tradingviewModel.userId = usuarioId

        tradingviewModel.setActivo(activo)
        binding.simboloFragmentTxtViewSimbolo.text = activo.simbolo
        binding.simboloFragmentTxtViewCantidad.text = activo.cantidad.toString()
        binding.simboloFragmentTxtViewNombre.text = activo.nombre
        binding.simboloFragmentTxtViewTipo.text = activo.tipo
        binding.simboloFragmentTxtViewFechaDeCompra.text = activo.fechaDeCompra
        binding.simboloFragmentTxtViewValorActual.text = activo.valorActual.toString()
        binding.simboloFragmentTxtViewValorDeCompra.text = activo.valorDeCompra.toString()
        binding.simboloFragmentTxtViewVariacion.text = activo.variacion.toString()

        binding.simboloFragmentBtnComprar.setOnClickListener {
            tradingviewModel.cantidadDeCompra.value = binding.simboloFragmentTextViewCantidadCompra.text.toString().toIntOrNull() ?: 0
            tradingviewModel.comprarActivo()
        }

        binding.simboloFragmentBtnVender.setOnClickListener {
            tradingviewModel.cantidadDeVenta.value = binding.simboloFragmentTextViewCantidadVenta.text.toString().toIntOrNull() ?: 0
            tradingviewModel.venderActivo()
        }

        tradingviewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        tradingviewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
            Snackbar.make(viewActivity, "Operacion realizada con exito!", Snackbar.LENGTH_SHORT).show()
            dismiss()
        }

        binding.simboloFragmentBtnIncrementarCantidadCompra.setOnClickListener {
            tradingviewModel.cantidadDeCompra.value = tradingviewModel.cantidadDeCompra.value?.plus(1)
            binding.simboloFragmentTextViewCantidadCompra.text = tradingviewModel.cantidadDeCompra.value.toString()
        }

        binding.simboloFragmentBtnDecrementarCantidadCompra.setOnClickListener {
            val currentCantidadCompra = tradingviewModel.cantidadDeCompra.value ?: 0
            if (currentCantidadCompra > 0) {
                tradingviewModel.cantidadDeCompra.value = currentCantidadCompra.minus(1)
                binding.simboloFragmentTextViewCantidadCompra.text = tradingviewModel.cantidadDeCompra.value.toString()
            }
        }

        binding.simboloFragmentBtnIncrementarCantidadVenta.setOnClickListener {
            tradingviewModel.cantidadDeVenta.value = tradingviewModel.cantidadDeVenta.value?.plus(1)
            binding.simboloFragmentTextViewCantidadVenta.text = tradingviewModel.cantidadDeVenta.value.toString()
        }

        binding.simboloFragmentBtnDecrementarCantidadVenta.setOnClickListener {
            val cantVentaIngresada = tradingviewModel.cantidadDeVenta.value ?: 0
            if (cantVentaIngresada > 0) {
                tradingviewModel.cantidadDeVenta.value = cantVentaIngresada.minus(1)
                binding.simboloFragmentTextViewCantidadVenta.text = tradingviewModel.cantidadDeVenta.value.toString()
            }
        }

        binding.simboloFragmentTextViewCantidadCompra.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cantidadCompra = s.toString().toIntOrNull() ?: 0
                tradingviewModel.cantidadDeCompra.value = cantidadCompra
                binding.simboloFragmentBtnComprar.isEnabled = cantidadCompra > 0
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No se necesita implementar
            }
        })

        binding.simboloFragmentTextViewCantidadVenta.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cantidadVenta = s.toString().toIntOrNull() ?: 0
                tradingviewModel.cantidadDeVenta.value = cantidadVenta
                binding.simboloFragmentBtnVender.isEnabled = cantidadVenta > 0 && precioVentaFlag
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No se necesita implementar
            }
        })
        binding.simboloFragmentEditTextPrecioVenta.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val precioDeVenta = s.toString().toDoubleOrNull() ?: 0.0
                tradingviewModel.precioDeVenta.value = precioDeVenta
                val cantidadVenta = tradingviewModel.cantidadDeVenta.value ?: 0
                binding.simboloFragmentBtnVender.isEnabled = cantidadVenta > 0 && precioDeVenta > 0
                precioVentaFlag = precioDeVenta > 0.0
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val esNumero = s.toString().toFloatOrNull() != null
                val precioVenta = s.toString().toFloatOrNull() ?: 0.0f

                if (esNumero && precioVenta > 0) {
                    tradingviewModel.precioDeVenta.value = precioVenta.toDouble()
                    precioVentaFlag = precioVenta > 0
                } else {
                    precioVentaFlag = precioVenta < 0
                }
            }
        })
        return view
    }

    override fun onResume() {
        super.onResume()
        // Definir el ancho y alto fijo del diálogo
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // Ajusta el alto como desees
    }

}