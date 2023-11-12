package com.example.fince.ui.fragmereturn

import com.example.fince.R

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.adapters.PerfilAdapter
import com.example.fince.ui.fragments.AyudaFragment
import com.example.fince.ui.fragments.NotificacionesFragment
import com.example.fince.ui.fragments.ContactenosFragment
import com.example.fince.ui.fragments.DocumentosFragment
import com.example.fince.ui.fragments.MovimientosFragment
import com.example.fince.ui.fragments.OpcionesFragment

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

        val rstListaOpciones = view.findViewById<RecyclerView>(R.id.rstListaOpciones)
        rstListaOpciones.layoutManager = LinearLayoutManager(requireContext())
        val elementos = ArrayList<ElementoLista>()

        elementos.add(ElementoLista("Notificaciones", "Ver notificaciones"))
        elementos.add(ElementoLista("Contactenos", "Informe de algun problema"))
        elementos.add(ElementoLista("Opciones", "Detalles personales, Seguridad y Comuniaciones"))
        elementos.add(ElementoLista("Documentos", "Declaraciones, resúmenes de impuestos, comprobantes de saldo"))
        elementos.add(ElementoLista("Movimientos", "Próximo, pasado, domiciliación bancaria, BPAY"))
        elementos.add(ElementoLista("Ayuda", "Preguntas frecuentes, consejos financieros, temas, comentarios"))

        val adapter = PerfilAdapter(elementos)
        rstListaOpciones.adapter = adapter

        adapter.onItemClick = { opcion ->
            when (opcion){
                "Notificaciones"->{
                    val fragment = NotificacionesFragment()
                    abrirConfigFragment(fragment);
                }
                "Contactenos"->{
                    val fragment = ContactenosFragment()
                    abrirConfigFragment(fragment);
                }
                "Opciones"->{
                    val fragment = OpcionesFragment()
                    abrirConfigFragment(fragment);
                }
                "Documentos"->{
                    val fragment = DocumentosFragment()
                    abrirConfigFragment(fragment);
                }
                "Movimientos"->{
                    val fragment = MovimientosFragment()
                    abrirConfigFragment(fragment);
                }
                "Ayuda"->{
                    val fragment = AyudaFragment()
                    abrirConfigFragment(fragment);
                }
            }
        }
        return view
    }
}
