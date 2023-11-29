package com.example.fince.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.fince.core.Config
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import com.example.fince.databinding.FragmentCodigoVerificacionBinding
import com.example.fince.ui.activities.MainActivity
import com.example.fince.ui.viewmodel.CodigoVerificacionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CodigoVerificacionFragment : Fragment() {

    private var _binding: FragmentCodigoVerificacionBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private val codigoVerificacionViewModel: CodigoVerificacionViewModel by viewModels()
    private var user : UserModel = UserModel("", "", "", "", "","", 0, 0, 0)
    private var codigoBack : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCodigoVerificacionBinding.inflate(inflater, container, false)
        view = binding.root

        val args: CodigoVerificacionFragmentArgs by navArgs()

        if (args.user != null) {
            user = args.user!!
        }

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
                    codigoVerificacionViewModel.registrar(user)
                } else {
                    Toast.makeText(requireContext(), "El codigo no coincide!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Debe ingresar un codigo!", Toast.LENGTH_SHORT).show()
            }
        }

        codigoVerificacionViewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
            val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("token", "Bearer " + response.token)
            Config.apiKey = ("Bearer " + response.token)
            editor.putString("userId", response.userId)
            editor.apply()
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }

        return view
    }

}