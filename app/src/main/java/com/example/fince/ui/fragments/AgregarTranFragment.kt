package com.example.fince.ui.fragments

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.Transaccion
import com.example.fince.databinding.FragmentAgregarTranBinding
import com.example.fince.ui.viewmodel.AgregarTranViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AgregarTranFragment : Fragment() {

    private var _binding: FragmentAgregarTranBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    lateinit var spCategorias : Spinner
    var listaCategorias : MutableList<CategoriaModel> = ArrayList()
    private val agregarTranViewModel : AgregarTranViewModel by viewModels()
    private var posicion : Int = 0
    private lateinit var response : Response<Transaccion>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgregarTranBinding.inflate(inflater, container, false)
        view = binding.root

        spCategorias = binding.fragAddTranSpCategoria

        return view
    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        agregarTranViewModel.onCreate(usuarioId)

        agregarTranViewModel.categoriaList.observe(viewLifecycleOwner) {
            listaCategorias.addAll(it)
            populateSpinner(spCategorias,(it),requireContext())
        }

        binding.fragAddTranBtnCalendar.setOnClickListener {
            mostrarSelectorDeFecha()
        }

        spCategorias.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                posicion = position
                //Snackbar.make(view, listaCategorias[position].nombre, Snackbar.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(view, "No hay nada seleccionado", Snackbar.LENGTH_SHORT).show()
            }
        })

        binding.fragAddTranBtnConfirm.setOnClickListener {

            val titulo = binding.fragAddTranEditTextTitulo.text.toString()

            val monto = if (binding.fragAddTranEditTextMonto.text.isNotEmpty()) {
                binding.fragAddTranEditTextMonto.text.toString().toFloat()
            } else {
                0F
            }

            val fecha = binding.fragAddTranEditTextFecha.text.toString()
            val categoria  = listaCategorias[posicion]

            if (compararFechas(fecha, obtenerFechaActualEnFormato()) == 1) {

                binding.fragAddTranEditTextFecha.error = "La fecha debe ser menor o igual a la de hoy"
            } else {
                if (titulo != "" && fecha != "" && monto != 0F) {
                    val tipo = categoria.tipo
                    val nombreCategoria = categoria.nombre
                    val categoriaId = categoria.id

                    val nuevaTransaccion = Transaccion("", fecha, monto, tipo, nombreCategoria, titulo, false, categoriaId)

                    agregarTranViewModel.nuevaTransaccion(usuarioId, nuevaTransaccion)
                } else {
                    Toast.makeText(requireContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        agregarTranViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        agregarTranViewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
            Snackbar.make(view, "Transaccion agregada con exito", Snackbar.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }

        binding.fragAddTranEditTextFecha.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val inputText = s.toString()
                val regex = """\d{2}/\d{2}/\d{4}""".toRegex()

                if (!inputText.matches(regex)) {
                    binding.fragAddTranEditTextFecha.error = "Formato de fecha incorrecto"
                } else {
                    binding.fragAddTranEditTextFecha.error = null
                }
            }
        })
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

    private fun obtenerFechaActualEnFormato(): String {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaActual = Date()
        return formato.format(fechaActual)
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
            binding.fragAddTranEditTextFecha.setText(fechaSeleccionada)
        }, ano, mes, dia)
        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.show()
    }

    fun populateSpinner (spinner: Spinner, list : List<CategoriaModel>, context : Context)
    {
        val aa = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, list)
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner.adapter = aa
    }

}