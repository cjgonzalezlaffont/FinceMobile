package com.example.fince.ui.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.fince.data.model.ObjetivoModel
import com.example.fince.databinding.FragmentAgregarObjetivoBinding
import com.example.fince.ui.viewmodel.AgregarObjetivoViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AgregarObjetivoFragment : Fragment() {

    private var _binding: FragmentAgregarObjetivoBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private val agregarObjetivoViewModel: AgregarObjetivoViewModel by viewModels()
    private var editeMode : Boolean = false
    private var objetivo = ObjetivoModel("", "", 0.toDouble(), "", "", 0F)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgregarObjetivoBinding.inflate(inflater, container, false)
        view = binding.root
        val args : AgregarObjetivoFragmentArgs by navArgs()

        if (args.objetivo != null){
            objetivo = args.objetivo!!
            editeMode = true
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        if (editeMode) {
            binding.fragAddObjTituloLabel.setText("Modificacion de objetivo")
            binding.fragAddObjEditTextTitulo.setText(objetivo.nombre)
            binding.fragAddObjEditTextFecha.setText(objetivo.fecha)
            binding.fragAddObjEditTextMonto.setText(String.format("%.0f", objetivo.monto.toDouble()))
            binding.fragAddObjEditTextDesc.setText(objetivo.descripcion)
            binding.fragAddObjBtnConfirm.text = "Aplicar cambios"
        }

        binding.fragAddObjBtnCalendar.setOnClickListener {
            mostrarSelectorDeFecha()
        }

        binding.fragAddObjBtnConfirm.setOnClickListener {
            val nombre = binding.fragAddObjEditTextTitulo.text.toString()
            val fecha = binding.fragAddObjEditTextFecha.text.toString()
            val monto = binding.fragAddObjEditTextMonto.text.toString().toDouble()
            val desc = binding.fragAddObjEditTextDesc.text.toString()

            if (compararFechas(obtenerFechaActualEnFormato(), fecha) == -1) {
                if (nombre != "" && fecha != "" && monto != 0.toDouble() && desc != "") {
                    val objetivoNuevo = ObjetivoModel("", nombre, monto, fecha, desc, 0.toFloat())

                    if (editeMode) {
                        val objetivoEditado = ObjetivoModel(objetivo.id, nombre, monto, fecha, desc, objetivo.progreso)
                        agregarObjetivoViewModel.editarObjetivo(usuarioId, objetivoEditado)
                    } else {
                        agregarObjetivoViewModel.nuevoObjetivo(usuarioId, objetivoNuevo)
                    }

                } else {
                    Toast.makeText(requireContext(), "Debe completar todos los campos!", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.fragAddObjEditTextFecha.error = "La fecha debe ser mayor a la de hoy"
            }

        }

        agregarObjetivoViewModel.responseLiveData.observe(viewLifecycleOwner){ response ->
            Snackbar.make(view, response, Snackbar.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }

        agregarObjetivoViewModel.errorLiveData.observe(viewLifecycleOwner){ error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT)
        }

        binding.fragAddObjEditTextFecha.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val inputText = s.toString()
                val regex = """\d{2}/\d{2}/\d{4}""".toRegex() // Define tu regex para el formato de fecha

                if (!inputText.matches(regex)) {
                    // El texto no coincide con el formato de fecha, puedes borrarlo o mostrar un mensaje de error
                    binding.fragAddObjEditTextFecha.error = "Formato de fecha incorrecto"
                } else {
                    // El texto coincide con el formato de fecha, no hagas nada
                    binding.fragAddObjEditTextFecha.error = null
                }
            }
        })
    }

    private fun obtenerFechaActualEnFormato(): String {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaActual = Date()
        return formato.format(fechaActual)
    }

    private fun compararFechas(fecha1: String, fecha2: String): Int {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            val date1: Date = formato.parse(fecha1)!!
            val date2: Date = formato.parse(fecha2)!!

            // Comparación de fechas
            return date1.compareTo(date2)
            // Retorna 0 si son iguales, -1 si date1 es menor que date2, 1 si date1 es mayor que date2
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    private fun mostrarSelectorDeFecha() {
        val calendario = Calendar.getInstance()
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(requireContext(), { _, year, month, day ->
            val formattedDay = String.format("%02d", day)
            val formattedMonth = String.format("%02d", month + 1)

            val fechaSeleccionada = "$formattedDay/${formattedMonth}/$year"
            binding.fragAddObjEditTextFecha.setText(fechaSeleccionada)
        }, ano, mes, dia)
        datePicker.datePicker.minDate = System.currentTimeMillis()
        datePicker.show()
    }

}