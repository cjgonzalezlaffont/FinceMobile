package com.example.fince.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fince.R
import com.example.fince.data.model.UserModel
import com.example.fince.databinding.FragmentCodigoVerifCambioPassBinding
import com.example.fince.databinding.FragmentCodigoVerificacionBinding
import com.example.fince.ui.viewmodel.CodigoVerificacionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CodigoVerifCambioPassFragment : Fragment() {

    private var _binding: FragmentCodigoVerifCambioPassBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private val codigoVerificacionViewModel: CodigoVerificacionViewModel by viewModels()
    private var user : UserModel = UserModel("", "", "", "", "","", 0, 0, 0)
    private var codigoBack : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCodigoVerifCambioPassBinding.inflate(inflater, container, false)
        view = binding.root

        val args: CodigoVerifCambioPassFragmentArgs by navArgs()

        if (args.user != null) {
            user = args.user!!
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        user.correo?.let { codigoVerificacionViewModel.codigoVerificacion(it) }

        codigoVerificacionViewModel.codeLiveData.observe(viewLifecycleOwner){ code ->
            codigoBack = code
        }

        codigoVerificacionViewModel.errorLiveData.observe(viewLifecycleOwner){ error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        binding.fragCodVerifBtnConfirm.setOnClickListener {
            val code = if (binding.fragCodVerifCodeInput.text.isNotEmpty()) {
                binding.fragCodVerifCodeInput.text.toString().toInt()
            } else {
                0
            }

            if (code != 0) {
                if (code == codigoBack) {
                    val accion = CodigoVerifCambioPassFragmentDirections.actionCodigoVerifCambioPassFragmentToCambiarContrasenaFragment(user)
                    view.findNavController().navigate(accion)
                } else {
                    Toast.makeText(requireContext(), "El codigo no coincide!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Debe ingresar un codigo!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}