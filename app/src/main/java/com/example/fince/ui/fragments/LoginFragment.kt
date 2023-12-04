package com.example.fince.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.fince.R
import com.example.fince.core.Config
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.userLoginModel
import com.example.fince.databinding.FragmentLoginBinding
import com.example.fince.ui.activities.MainActivity
import com.example.fince.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private val loginViewModel: LoginViewModel by viewModels()
    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        view = binding.root

        binding.btnLogin.setOnClickListener {

            val correo = binding.txtCorreo.text.toString()
            val pass = binding.txtPass.text.toString()

            if(correo != "" && pass != "") {

                val user = userLoginModel(correo, pass)

                binding.btnLogin.isEnabled = false
                loginViewModel.login(user)

            } else {
                Toast.makeText(
                    requireContext(),
                    "Complete todos los campos!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        loginViewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
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

        loginViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.fragLogIsLoading.visibility = if (it) View.VISIBLE else View.GONE
            binding.fragLogCarga.visibility = if (it) View.VISIBLE else View.GONE
        }

        loginViewModel.errorLiveData.observe(viewLifecycleOwner){ error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            binding.btnLogin.isEnabled = true
        }

        binding.btnRegistro.setOnClickListener {
            val accion = LoginFragmentDirections.actionLoginFragmentToRegistroFragment()
            view.findNavController().navigate(accion)
        }

        binding.fragLogTextViewLostPwd.setOnClickListener {
            val accion = LoginFragmentDirections.actionLoginFragmentToOlvideContrasenaFragment()
            view.findNavController().navigate(accion)
        }

        binding.fragLogVisible.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            val icon = if (isPasswordVisible) R.drawable.ic_no_visible else R.drawable.ic_visible
            binding.fragLogVisible.setImageResource(icon)

            binding.txtPass.transformationMethod = if (isPasswordVisible) null else PasswordTransformationMethod.getInstance()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        binding.btnLogin.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}