package com.example.fince.ui.fragments
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fince.data.model.ActivoModel
import com.example.fince.databinding.FragmentSimboloBinding
import androidx.navigation.fragment.navArgs
import com.example.fince.ui.viewmodel.TradingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimboloFragment : Fragment() {

    private var _binding: FragmentSimboloBinding? = null
    private val tradingviewModel: TradingViewModel by viewModels()
    private lateinit var view: View
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSimboloBinding.inflate(inflater, container, false)
        view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        tradingviewModel.userId = usuarioId
        val args: SimboloFragmentArgs by navArgs()
        val activo: ActivoModel = args.activo
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
            val action = SimboloFragmentDirections.actionSimboloFragmentToCartera()
            this.findNavController().navigate(action)
        }

        binding.simboloFragmentBtnVender.setOnClickListener {
            tradingviewModel.cantidadDeVenta.value = binding.simboloFragmentTextViewCantidadVenta.text.toString().toIntOrNull() ?: 0
            tradingviewModel.venderActivo()
            val action = SimboloFragmentDirections.actionSimboloFragmentToCartera()
            this.findNavController().navigate(action)
        }

        binding.simboloFragmentBtnIncrementarCantidadCompra.setOnClickListener {
            tradingviewModel.cantidadDeCompra.value = tradingviewModel.cantidadDeCompra.value?.plus(1)
            binding.simboloFragmentTextViewCantidadCompra.text = tradingviewModel.cantidadDeCompra.value.toString()
        }

        binding.simboloFragmentBtnDecrementarCantidadCompra.setOnClickListener {
            tradingviewModel.cantidadDeCompra.value = tradingviewModel.cantidadDeCompra.value?.minus(1)
            binding.simboloFragmentTextViewCantidadCompra.text = tradingviewModel.cantidadDeCompra.value.toString()
        }

        binding.simboloFragmentBtnIncrementarCantidadVenta.setOnClickListener {
            tradingviewModel.cantidadDeVenta.value = tradingviewModel.cantidadDeVenta.value?.plus(1)
            binding.simboloFragmentTextViewCantidadVenta.text = tradingviewModel.cantidadDeVenta.value.toString()
        }

        binding.simboloFragmentBtnDecrementarCantidadVenta.setOnClickListener {
            tradingviewModel.cantidadDeVenta.value = tradingviewModel.cantidadDeVenta.value?.minus(1)
            binding.simboloFragmentTextViewCantidadVenta.text = tradingviewModel.cantidadDeVenta.value.toString()
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


                binding.simboloFragmentBtnVender.isEnabled = cantidadVenta > 0
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
                val precioVenta = s.toString().toFloatOrNull() ?: 0.0f
                tradingviewModel.precioDeVenta.value = precioVenta.toDouble()

                binding.simboloFragmentBtnVender.isEnabled = precioVenta > 0
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val esNumero = s.toString().toFloatOrNull() != null
                val precioVenta = s.toString().toFloatOrNull() ?: 0.0f

                if (esNumero && precioVenta > 0) {
                    tradingviewModel.precioDeVenta.value = precioVenta.toDouble()
                    binding.simboloFragmentBtnVender.isEnabled = true
                } else {
                    binding.simboloFragmentBtnVender.isEnabled = false
                }
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}