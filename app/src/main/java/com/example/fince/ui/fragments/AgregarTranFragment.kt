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
import com.example.fince.databinding.FragmentAgregarTranBinding
import com.google.android.material.snackbar.Snackbar


class AgregarTranFragment : Fragment() {

    private var _binding: FragmentAgregarTranBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    lateinit var spMeses : Spinner
    var listaCategorias = listOf("enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgregarTranBinding.inflate(inflater, container, false)
        view = binding.root

        spMeses = binding.fragAddTranEditTextCategoria

        return view
    }

    override fun onStart() {
        super.onStart()

        populateSpinner(spMeses,listaCategorias,requireContext())

        spMeses.setSelection(0, false) // evita la primer falsa entrada si existe validación

        spMeses.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                Snackbar.make(view, listaCategorias[position], Snackbar.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(view, "No hay nada seleccionado", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    fun populateSpinner (spinner: Spinner, list : List<String>, context : Context)
    {
        //   val aa = ArrayAdapter( context!!, android.R.layout.simple_spinner_item, list)
        val aa = ArrayAdapter(context, R.layout.simple_spinner_item, list)

        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.setAdapter(aa)
    }

}