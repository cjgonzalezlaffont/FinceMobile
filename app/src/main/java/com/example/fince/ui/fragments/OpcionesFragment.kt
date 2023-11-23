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
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R
import com.example.fince.*
import com.example.fince.core.Config
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserResponse
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
    private var userResp= UserResponse("","","",0,0,0,"")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpcionesBinding.inflate(inflater, container, false)
        view = binding.root

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        var user = sharedPreferences.getString("userId", "")

        userViewModel.onCreate(user.toString());

        userViewModel.response.observe(viewLifecycleOwner){
            userResp=it
            binding.editTextNombre.setText(userResp.nombre)
            binding.editTextApellido.setText(userResp.apellido)
        }

        binding.btnGuardar.setOnClickListener {
            Config.dark_mode=binding.switchModoOscuro.isChecked
            Config.setDarkMode();

            Toast.makeText(
                requireContext(),
                "Datos modifcados con exito!",
                Toast.LENGTH_SHORT
            ).show()
        };

        return view
    }
}