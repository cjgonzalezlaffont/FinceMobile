package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.fince.core.Config
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserResponse
import com.example.fince.databinding.FragmentOpcionesBinding
import com.example.fince.ui.viewmodel.SharedViewModel
import com.example.fince.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpcionesFragment : Fragment() {
    private var _binding: FragmentOpcionesBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private val userViewModel: UserViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var userResp= UserModel("","","","","","",0,0, 0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpcionesBinding.inflate(inflater, container, false)
        view = binding.root

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        var user = sharedPreferences.getString("userId", "")
        var darkMode = sharedPreferences.getBoolean("darkMode", false)

        binding.switchModoOscuro.isChecked = darkMode

        userViewModel.onCreate(user.toString());

        binding.switchModoOscuro.setOnCheckedChangeListener { _, isChecked ->
            sharedViewModel.setDarkMode(isChecked)
            val editor = sharedPreferences.edit()
            editor.putBoolean("darkMode", isChecked)
            editor.apply()
        }

        return view
    }
}