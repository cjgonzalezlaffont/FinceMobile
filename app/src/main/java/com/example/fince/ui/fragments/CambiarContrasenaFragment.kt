package com.example.fince.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
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
import com.example.fince.databinding.FragmentCambiarContrasenaBinding
import com.example.fince.ui.viewmodel.CambioContrasenaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CambiarContrasenaFragment : Fragment() {

   private var _binding: FragmentCambiarContrasenaBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private val cambioContrasenaViewModel: CambioContrasenaViewModel by viewModels()
    private var user : UserModel = UserModel("", "", "", "", "","", 0, 0, 0)
    private var fromMainGraph : Boolean = false
    private var isPasswordVisible = false
    private var isPasswordConfirmVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCambiarContrasenaBinding.inflate(inflater, container, false)
        view = binding.root

        val args: CambiarContrasenaFragmentArgs by navArgs()

        if (args.user != null) {
            user = args.user!!
        }

        fromMainGraph = args.fromMainGraph!!


        return view
    }

    override fun onStart() {
        super.onStart()

        binding.fragPassBtnConfirm.setOnClickListener {
            if (arePasswordsMatching()) {
                val contrasena = binding.fragPassNewPwd.text.toString()
                user.contrasena = contrasena
                cambioContrasenaViewModel.actualizar(user)
            } else {
                Toast.makeText(requireContext(), "Las contraseñas no coinciden!", Toast.LENGTH_SHORT).show()
            }
        }

        cambioContrasenaViewModel.responseLiveData.observe(viewLifecycleOwner){
            val navController = view.findNavController()

            Toast.makeText(requireActivity(), "Contraseña cambiada correctamente!", Toast.LENGTH_SHORT).show()

            if (fromMainGraph) {
                navController.navigate(R.id.action_cambiarContrasenaFragment_to_perfil)
            } else {
                navController.navigate(R.id.action_cambiarContrasenaFragment_to_loginFragment)
            }

        }

        cambioContrasenaViewModel.errorLiveData.observe(viewLifecycleOwner){ error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        binding.fragPassNewPwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                if (password.length < 6) {
                    binding.fragPassNewPwd.error = "La contraseña debe tener al menos 6 caracteres"
                } else {
                    binding.fragPassNewPwd.error = null
                }
            }
        })

        binding.fragPassNewVisible.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            val icon = if (isPasswordVisible) R.drawable.ic_no_visible else R.drawable.ic_visible
            binding.fragPassNewVisible.setImageResource(icon)

            binding.fragPassNewPwd.transformationMethod = if (isPasswordVisible) null else PasswordTransformationMethod.getInstance()
        }

        binding.fragPassNewVisibleConfirm.setOnClickListener {
            isPasswordConfirmVisible = !isPasswordConfirmVisible
            val icon = if (isPasswordConfirmVisible) R.drawable.ic_no_visible else R.drawable.ic_visible
            binding.fragPassNewVisibleConfirm.setImageResource(icon)

            binding.fragPassNewPwdConfirm.transformationMethod = if (isPasswordConfirmVisible) null else PasswordTransformationMethod.getInstance()
        }
    }

    fun arePasswordsMatching(): Boolean {
        val password = binding.fragPassNewPwd.text.toString()
        val confirmPassword = binding.fragPassNewPwdConfirm.text.toString()

        return password == confirmPassword
    }

}