package com.example.fince.ui.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R
import com.example.fince.adapters.AccionesAdapter
import com.example.fince.adapters.ElementoAccionnes
import com.example.fince.adapters.ElementoFondo
import com.example.fince.adapters.FondosAdapter

class CarteraFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cartera, container, false)

        val rctAcciones = view.findViewById<RecyclerView>(R.id.rctAcciones)
        rctAcciones.layoutManager = LinearLayoutManager(requireContext())
        val elementosAcciones = ArrayList<ElementoAccionnes>()

        val rctFondos = view.findViewById<RecyclerView>(R.id.rctFondos)
        rctFondos.layoutManager = LinearLayoutManager(requireContext())
        val elementosFondo = ArrayList<ElementoFondo>()
        /*
        ACA VA EL LLAMADO A LA API QUE TRAE LOS DATOS DE ACCIONES E INVERSION
        Para mostrar una accion:
        */
        elementosAcciones.add(ElementoAccionnes("Walmart","WMT","10","2000"))
        /*
        Para mostrar un fondo de inversion
        */
        elementosFondo.add(ElementoFondo("FONDO A","FND","1002"))

        val adapterAcciones = AccionesAdapter(elementosAcciones)
        rctAcciones.adapter = adapterAcciones

        val adapterFondos = FondosAdapter(elementosFondo)
        rctFondos.adapter = adapterFondos

        return view
    }
}