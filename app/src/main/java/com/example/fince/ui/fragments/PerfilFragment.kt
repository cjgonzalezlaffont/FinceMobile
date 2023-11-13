package com.example.fince.ui.fragmereturn

import com.example.fince.R

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

data class ElementoLista(val opcion: String, val descripcion: String)

class PerfilFragment : Fragment() {
    fun abrirConfigFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        //transaction.replace(R.id.container, fragment)
        //transaction.addToBackStack(null)
        //transaction.commit()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)
        val lblUsuario=view.findViewById<TextView>(R.id.lblUsuario);
        val lblId=view.findViewById<TextView>(R.id.lblId);

        /*
            ACA IRIA EL LLAMADO A LA API QUE TRAE DATOS DEL USUARIO
            Muestra usuario y id asi:
         */
        lblUsuario.text="Juan Perez"
        lblId.text="001"
        return view
    }
}
