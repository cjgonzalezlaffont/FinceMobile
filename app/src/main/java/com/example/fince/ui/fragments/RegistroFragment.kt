package com.example.fince.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
    private var user : UserModel = UserModel("", "", "", "", "","", 0, 0,0)
    private var isPasswordVisible = false
    private var isPasswordConfirmVisible = false

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
                    user = UserModel("", "", nombre, apellido, mail, password, 0,0,0)

                    registerViewModel.verificar(mail)
                } else {
                    Toast.makeText(requireContext(), "Debe ingresar un email valido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.fragRegVisible.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            val icon = if (isPasswordVisible) R.drawable.ic_no_visible else R.drawable.ic_visible
            binding.fragRegVisible.setImageResource(icon)

            binding.txtPassword.transformationMethod = if (isPasswordVisible) null else PasswordTransformationMethod.getInstance()
        }

        binding.fragRegVisibleConfirm.setOnClickListener {
            isPasswordConfirmVisible = !isPasswordConfirmVisible
            val icon = if (isPasswordConfirmVisible) R.drawable.ic_no_visible else R.drawable.ic_visible
            binding.fragRegVisible.setImageResource(icon)

            binding.txtConfirmPassword.transformationMethod = if (isPasswordConfirmVisible) null else PasswordTransformationMethod.getInstance()
        }

        registerViewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
            val action = RegistroFragmentDirections.actionRegistroFragmentToCodigoVerificacionFragment(user)
            findNavController().navigate(action)
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

        binding.txtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                if (password.length < 6) {
                    binding.txtPassword.error = "La contraseña debe tener al menos 6 caracteres"
                } else {
                    binding.txtPassword.error = null
                }
            }
        })

        return view
    }
}
