package com.example.fince.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.example.fince.R
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import com.example.fince.data.model.userLoginModel
import com.example.fince.databinding.FragmentLoginBinding
import com.example.fince.ui.activities.InterfazActivity
import com.example.fince.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private val loginViewModel: LoginViewModel by viewModels()
    private var userResponse = UserModel("", "", "", "", "", 0,0,0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        view = binding.root

        binding.btnLogin.setOnClickListener {

            val correo = binding.txtCorreo.text.toString()
            val pass = binding.txtPass.text.toString()
            val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            if(correo != "" && pass != "") {

                val user = userLoginModel(correo, pass)

                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val userResponse = loginViewModel.login(user)
                        if (!userResponse.token.isEmpty()) {
                            Log.i("algo", "Entre")
                            editor.putString("token", userResponse.token)
                            editor.putString("userId", userResponse.userId)
                            editor.apply()
                            val intent = Intent(activity, InterfazActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(requireContext(), "Error al iniciar sesion", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
                        // Manejar el error (por ejemplo, mostrar un mensaje de error)
                        Log.e("TAG", "Error al iniciar sesión", e)
                    }
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