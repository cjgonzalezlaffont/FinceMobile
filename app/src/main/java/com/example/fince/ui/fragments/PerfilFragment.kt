package com.example.fince.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.fince.core.Config
import com.example.fince.data.model.UserModel
import com.example.fince.databinding.FragmentPerfilBinding
import com.example.fince.ui.activities.LoginActivity
import com.example.fince.ui.viewmodel.PerfilViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private val perfilViewModel: PerfilViewModel by viewModels()
    private lateinit var usuario : UserModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        view = binding.root

        return view
    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        perfilViewModel.getUser(usuarioId)

        perfilViewModel.response.observe(viewLifecycleOwner) { user ->
            usuario = user
            binding.fragPerfilEditTextApellido.setText(user.apellido)
            binding.fragPerfilEditTextNombre.setText(user.nombre)
            binding.fragPerfilEditTextCorreo.setText(user.correo)
            binding.fragPerfilId.setText("ID: " + user.userId)
            binding.fragPerfilEditTextPerfil.setText(if (user.perfil == 0) "Conservador"
                                                     else if (user.perfil == 1) "Moderado"
                                                     else if (user.perfil == 2) "Arriesgado"
                                                     else "No tiene perfil seleccionado"
                                                     )
        }

        binding.fragPerfilBtnPass.setOnClickListener {
            val accion = PerfilFragmentDirections.actionPerfilToCodigoVerifCambioPassFragmentMain(usuario)
            view.findNavController().navigate(accion)
        }

        binding.fragPerfilEditTextPerfil.setOnClickListener {
            val accion = PerfilFragmentDirections.actionPerfilToPerfilRiesgoFragment(usuario)
            view.findNavController().navigate(accion)
        }

        binding.fragPerfilBtnGuardar.setOnClickListener {
            usuario.nombre = binding.fragPerfilEditTextNombre.text.toString()
            usuario.apellido = binding.fragPerfilEditTextApellido.text.toString()

            perfilViewModel.updateUser(usuario)
        }

        perfilViewModel.updateResponse.observe(viewLifecycleOwner){
            Toast.makeText(requireActivity(), "Usuario actualizado correctamente!", Toast.LENGTH_SHORT).show()
        }

        binding.fragPerfilBtnSesion.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("token", "")
            Config.apiKey = ("")
            editor.putString("userId", "")
            editor.apply()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
    }
}
