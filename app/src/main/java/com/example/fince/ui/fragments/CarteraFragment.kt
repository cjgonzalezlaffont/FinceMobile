package com.example.fince.ui.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.fince.R

fun getElementoAccion(empresa: String, cantidad:String,precio:String): String {
    return "Empresa: "+empresa+" Cantidad: "+cantidad+"\nPrecio: $"+precio;
}

fun getElementoFondo(fondo: String, posicion:String): String {
    return "Fondo: "+fondo+"\nPosicion: $"+posicion;
}

class CarteraFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cartera, container, false)
        var lstAcciones = rootView.findViewById<ListView>(R.id.listViewAcciones)
        var lstFonndos = rootView.findViewById<ListView>(R.id.listViewFondos)

        val acciones = ArrayList<String>()
        val fondos = ArrayList<String>()
        /*
            ACA VA EL LLAMADO A LA API QUE TRAE LOS DATOS DE ACCIONES E INVERSION
            Para agregar acciones
            */
            acciones.add(getElementoAccion("Walmart","10","2000"));
            /*
            Para agregar fondos de inversion
            */
            fondos.add(getElementoFondo("FONDO A","2500"))
            /*
     */

        val adaptadorAcciones = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, acciones)
        val adaptadorFondos = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, fondos)
        lstAcciones.adapter=adaptadorAcciones;
        lstFonndos.adapter=adaptadorFondos;
        return rootView
    }
}