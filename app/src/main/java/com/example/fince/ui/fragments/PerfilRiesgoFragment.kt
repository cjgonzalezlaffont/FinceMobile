package com.example.fince.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.fince.R
import com.example.fince.ui.activities.MainActivity

enum class PerfilRiesgo {
    CONSERVADOR,MODERADO,AGRESIVO
}

fun definirPerfil(perfil: PerfilRiesgo): Boolean{
    /*
        ACA SE DEBE LLAMAR A LA API PARA COLOCAR EL PERFIL DE RIESGO QUE SELECCIONO EL USUARIO
        Si salio bien: hace */ return true /*
        si no: return false
     */
}

class PerfilRiesgoFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_perfil_riesgo, container, false)
        val intent = Intent(context, MainActivity::class.java)
        val btn_conservador: Button=view.findViewById(R.id.btn_conservador);
        val btn_moderado: Button=view.findViewById(R.id.btn_moderado);
        val btn_agresivo: Button=view.findViewById(R.id.btn_agresivo);

        btn_conservador.setOnClickListener {
            if(definirPerfil(PerfilRiesgo.CONSERVADOR)){
                startActivity(intent)
                requireActivity().finish();
            }else {
                Toast.makeText(
                    requireContext(),
                    "Error al seleccionar el perfil de riesgo",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        btn_moderado.setOnClickListener {
            if(definirPerfil(PerfilRiesgo.MODERADO)){
                startActivity(intent)
                requireActivity().finish();
            }else {
                Toast.makeText(
                    requireContext(),
                    "Error al seleccionar el perfil de riesgo",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        btn_agresivo.setOnClickListener {
            if(definirPerfil(PerfilRiesgo.AGRESIVO)){
                startActivity(intent)
                requireActivity().finish();
            }else {
                Toast.makeText(
                    requireContext(),
                    "Error al seleccionar el perfil de riesgo",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }
}