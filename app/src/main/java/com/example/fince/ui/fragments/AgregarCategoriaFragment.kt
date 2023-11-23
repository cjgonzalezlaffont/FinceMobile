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
import androidx.navigation.fragment.navArgs
import com.example.fince.data.model.ActivoModel
import com.example.fince.data.model.CategoriaModel
import com.example.fince.databinding.FragmentAgregarCategoriaBinding
import com.example.fince.ui.viewmodel.AgregarCatViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgregarCategoriaFragment : Fragment() {

    private lateinit var view : View
    private var _binding: FragmentAgregarCategoriaBinding? = null
    var listaTipos = listOf("Ingreso","Egreso")
    lateinit var spTipos : Spinner
    private val agregarCatViewModel : AgregarCatViewModel by viewModels()
    private val binding get() = _binding!!
    private var tipo : Int = 0
    private var montoMax : Float = 0F
    private var categoria : CategoriaModel = CategoriaModel("", "", 0.toFloat(), 0, "", 0.toFloat(), false)
    private var editeMode : Boolean = false
    private lateinit var categoriaReq : CategoriaModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgregarCategoriaBinding.inflate(inflater, container, false)
        view = binding.root
        val args: AgregarCategoriaFragmentArgs by navArgs()

        if (args.categoria != null) {
            categoria = args.categoria!!
            editeMode = true
        }

        spTipos = binding.fragAddCatSpTipo

        return view
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        populateSpinner(spTipos,listaTipos,requireContext())

        spTipos.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //Snackbar.make(view, listaTipos[position], Snackbar.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(view, "No hay nada seleccionado", Snackbar.LENGTH_SHORT).show()
            }
        })

        if (editeMode) {
            binding.fragAddCatEditTextTitulo.setText(categoria.nombre)
            binding.fragAddCatEditTextTitulo.isEnabled = false

            binding.fragAddCatEditTextDesc.setText(categoria.descripcion)
            binding.fragAddCatEditTextMonto.setText(categoria.montoMax.toString())
            binding.fragAddCatBtnConfirm.text = "Guardar cambios"
            binding.fragAddCatTxtLblTitulo.text = "Modificacion de Categoria"

            if (categoria.tipo == 1) {
                spTipos.setSelection(0, false)
            } else {
                spTipos.setSelection(1, false)
            }
        }

        binding.fragAddCatBtnConfirm.setOnClickListener {
            val titulo = binding.fragAddCatEditTextTitulo.text.toString()
            montoMax = binding.fragAddCatEditTextMonto.text.toString().toFloat()

            val tipoStr = spTipos.selectedItem.toString()

            if (tipoStr == "Ingreso") {
                tipo = 1
            }

            val descripcion = binding.fragAddCatEditTextDesc.text.toString()

            if (editeMode){
                categoriaReq = CategoriaModel(categoria.id, titulo, montoMax, tipo, descripcion, categoria.montoConsumido, categoria.financiera)
                agregarCatViewModel.editarCategoria(usuarioId, categoriaReq)
            } else {
                categoriaReq = CategoriaModel("", titulo, montoMax, tipo, descripcion, 0.toFloat(), false)
                agregarCatViewModel.nuevaCategoria(usuarioId, categoriaReq)
            }


        }

        agregarCatViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        agregarCatViewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
            Snackbar.make(view, agregarCatViewModel.responseLiveData.value.toString(), Snackbar.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }

    }

    fun populateSpinner (spinner: Spinner, list : List<String>, context : Context)
    {
        val aa = ArrayAdapter(context, R.layout.simple_spinner_item, list)

        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(aa)
    }

}