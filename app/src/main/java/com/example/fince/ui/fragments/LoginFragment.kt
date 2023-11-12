package com.example.fince.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.example.fince.R
import com.example.fince.databinding.FragmentLoginBinding
import com.example.fince.ui.activities.InterfazActivity
import com.example.fince.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        view = binding.root
        loginViewModel.onCreate()

        binding.btnLogin.setOnClickListener {

            var usuarioCorrecto: Boolean;
            val usuario = binding.txtUser.text
            val pass = binding.txtPass.text

            if(!usuario.equals("") && !pass.equals("")) {
                /*
                    ACA IRIA EL CODIGO QUE VERIFICA SI EL USUARIO ES CORRECTO
                    si es correcto pone: */ usuarioCorrecto = true /*
                    si no: usuarioCorrecto=false
             */
                if (usuarioCorrecto) {
                    val intent = Intent(requireActivity(), InterfazActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish();
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Usuario y/o Contraseña incorrecto!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Complete todos los campos!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        };
        binding.btnRegistro.setOnClickListener {
            val accion = LoginFragmentDirections.actionLoginFragmentToRegistroFragment()
            view.findNavController().navigate(accion)
        };
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}