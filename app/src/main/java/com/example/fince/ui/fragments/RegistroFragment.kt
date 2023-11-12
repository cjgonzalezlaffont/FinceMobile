package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.fince.R
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import com.example.fince.databinding.FragmentLoginBinding
import com.example.fince.databinding.FragmentRegistroBinding
import com.example.fince.ui.viewmodel.LoginViewModel
import com.example.fince.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistroFragment : Fragment() {

    private var _binding: FragmentRegistroBinding ? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private val registerViewModel: RegisterViewModel by viewModels()
    private var userResponse = UserModel("", "", "", "", "", 0,0,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        view = binding.root

        binding.btnRegistrarse.setOnClickListener {

            val nombre = binding.txtNombre.text.toString()
            val apellido = binding.txtApellido.text.toString()
            val mail = binding.txtMail.text.toString()
            val password = binding.txtPassword.text.toString()
            val confirmPassword = binding.txtConfirmPassword.text.toString()
            val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            if(!nombre.equals("") && !mail.equals("") && !password.equals("") && !confirmPassword.equals("")){

                val user = UserRegisterModel(nombre, apellido, mail, password)

                userResponse = registerViewModel.registrar(user)
                if (!userResponse.token.isEmpty()) {
                    editor.putString("token", userResponse.token)
                    editor.putString("userId", userResponse.userId)
                    //INTENT ACTIVITY
                } else {
                    Toast.makeText(requireContext(), "Error al crear usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show()
            }
        };
        binding.btnCancelar.setOnClickListener {

        };
        return view
    }
}
