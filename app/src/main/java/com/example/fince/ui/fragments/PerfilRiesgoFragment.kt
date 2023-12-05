package com.example.fince.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.fince.data.model.UserModel
import com.example.fince.databinding.FragmentPerfilRiesgoBinding
import com.example.fince.ui.viewmodel.PerfilRiesgoViewModdel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfilRiesgoFragment : Fragment() {

    private var _binding: FragmentPerfilRiesgoBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private var user : UserModel = UserModel("", "", "", "", "","", 0, 0, 0)
    private val perfilRiesgoViewModdel: PerfilRiesgoViewModdel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilRiesgoBinding.inflate(inflater, container, false)
        view = binding.root

        val args: PerfilRiesgoFragmentArgs by navArgs()

        if (args.user != null) {
            user = args.user!!
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        binding.fragRiegBtnInfoConservador.setOnClickListener {
            // Crear y mostrar el diálogo de información
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Perfil Conservador")
                .setMessage("Este perfil es adecuado para inversionistas que desean minimizar el riesgo y buscan un crecimiento estable a lo largo del tiempo.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .create()

            dialog.show()
        }

        binding.fragRiegBtnInfoModerado.setOnClickListener {
            // Crear y mostrar el diálogo de información
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Perfil Moderado")
                .setMessage("El perfil moderado ofrece un equilibrio entre riesgo y recompensa, adecuado para inversionistas que buscan un crecimiento moderado con cierto nivel de riesgo.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .create()

            dialog.show()
        }

        binding.fragRiegBtnInfoAgresivo.setOnClickListener {
            // Crear y mostrar el diálogo de información
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Perfil Agresivo")
                .setMessage("Los inversionistas arriesgados buscan altas recompensas y están dispuestos a asumir un riesgo significativo. Este perfil es adecuado para aquellos que pueden tolerar la volatilidad en sus inversiones.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .create()

            dialog.show()
        }

        binding.fragRiegBtnConservador.setOnClickListener {
            user.perfil = 0
            perfilRiesgoViewModdel.actualizar(user)
        }

        binding.fragRiegBtnModerado.setOnClickListener {
            user.perfil = 1
            perfilRiesgoViewModdel.actualizar(user)
        }

        binding.fragRiegBtnAgresivo.setOnClickListener {
            user.perfil = 2
            perfilRiesgoViewModdel.actualizar(user)
        }

        perfilRiesgoViewModdel.responseLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), "Perfil actualizado con exito!", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }

    }
}