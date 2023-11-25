package com.example.fince.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.fince.R
import com.example.fince.core.Config
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import com.example.fince.databinding.FragmentRegistroBinding
import com.example.fince.ui.activities.MainActivity
import com.example.fince.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistroFragment : Fragment() {

    private var _binding: FragmentRegistroBinding ? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private val registerViewModel: RegisterViewModel by viewModels()

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

            if(!nombre.equals("") && !mail.equals("") && !password.equals("") && !confirmPassword.equals("")){

                if (Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    val user = UserRegisterModel(nombre, apellido, mail, password)

                    registerViewModel.registrar(user)
                } else {
                    Toast.makeText(requireContext(), "Debe ingresar un email valido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
            val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("token", "Bearer " + response.token)
            Config.apiKey = ("Bearer " + response.token)
            editor.putString("userId", response.userId)
            editor.apply()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        registerViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.fragRegIsLoading.visibility = if (it) View.VISIBLE else View.GONE
            binding.fragRegCarga.visibility = if (it) View.VISIBLE else View.GONE
        }

        registerViewModel.errorLiveData.observe(viewLifecycleOwner){ error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        binding.txtMail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(editable: Editable?) {
                val email = binding.txtMail.text.toString().trim()

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.txtMail.error = "Email no válido"
                } else {
                    binding.txtMail.error = null
                }
            }
        })

        binding.btnCancelar.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }
}
