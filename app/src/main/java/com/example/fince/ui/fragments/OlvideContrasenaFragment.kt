package com.example.fince.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.fince.databinding.FragmentOlvideContrasenaBinding
import com.example.fince.ui.viewmodel.OlvideContrasenaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OlvideContrasenaFragment : Fragment() {

    private var _binding: FragmentOlvideContrasenaBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private val olvideContrasenaViewModel: OlvideContrasenaViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOlvideContrasenaBinding.inflate(inflater, container, false)
        view = binding.root

        return view
    }

    override fun onStart() {
        super.onStart()

        binding.fragLostPassBtnConfirm.setOnClickListener {
            val correo = binding.fragLostPassEditTextCorreo.text.toString()

            if (correo != "") {
                olvideContrasenaViewModel.buscarDatos(correo)
            }
        }

        olvideContrasenaViewModel.errorLiveData.observe(viewLifecycleOwner){ error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        olvideContrasenaViewModel.responseLiveData.observe(viewLifecycleOwner){ user ->
            val action = OlvideContrasenaFragmentDirections.actionOlvideContrasenaFragmentToCodigoVerifCambioPassFragment(user)
            view.findNavController().navigate(action)
        }
    }

}