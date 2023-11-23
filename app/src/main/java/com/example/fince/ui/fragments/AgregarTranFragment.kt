package com.example.fince.ui.fragments

import android.R
import android.content.Context
import android.os.Bundle
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

        //spCategorias.setSelection(0, false) // evita la primer falsa entrada si existe validación

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
            val monto = binding.fragAddTranEditTextMonto.text.toString().toFloat()
            val fecha = binding.fragAddTranEditTextFecha.text.toString()
            val categoria  = listaCategorias[posicion]

            val tipo = categoria.tipo
            val nombreCategoria = categoria.nombre
            val categoriaId = categoria.id

            val nuevaTransaccion = Transaccion("", fecha, monto, tipo, nombreCategoria, titulo, false, categoriaId)

            agregarTranViewModel.nuevaTransaccion(usuarioId, nuevaTransaccion)

        }

        agregarTranViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        agregarTranViewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
            Snackbar.make(view, "Transaccion agregada con exito", Snackbar.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }
    }

    fun populateSpinner (spinner: Spinner, list : List<CategoriaModel>, context : Context)
    {
        val aa = ArrayAdapter<CategoriaModel>(context, R.layout.simple_spinner_dropdown_item, list)

        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter = aa
    }

}