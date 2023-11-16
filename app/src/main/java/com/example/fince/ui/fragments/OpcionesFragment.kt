package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R
import com.example.fince.*
import com.example.fince.core.dark_mode
import com.example.fince.databinding.FragmentOpcionesBinding
import com.example.fince.databinding.FragmentPanelGeneralBinding
import com.example.fince.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpcionesFragment : Fragment() {
    private var _binding: FragmentOpcionesBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpcionesBinding.inflate(inflater, container, false)
        view = binding.root

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        var user = sharedPreferences.getString("userId", "")

        val datosUsuario= userViewModel.onCreate(user.toString());

        userViewModel.response.observe(viewLifecycleOwner){
            Log.i("Prueba", datosUsuario.nombre.toString());
            Log.i("Prueba", datosUsuario.toString());
            binding.editTextNombre.setText(datosUsuario.nombre)
            binding.editTextApellido.setText(datosUsuario.apellido)
        }


        binding.btnGuardar.setOnClickListener {
            dark_mode=binding.switchModoOscuro.isChecked
            Toast.makeText(
                requireContext(),
                dark_mode.toString(),
                Toast.LENGTH_SHORT
            ).show()
        };

        return view
    }
}