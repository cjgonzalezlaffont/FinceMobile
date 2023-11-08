package com.example.fince.ui.fragments
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fince.R

class PresupuestoFragment : Fragment() {

    private var presupuesto = 0.0

    @SuppressLint("StringFormatMatches")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_presupuesto, container, false)


        val textViewPresupuesto = rootView.findViewById<TextView>(R.id.textViewPresupuesto)
        val presupuesto = 0.0

        // Inicializar el valor del monto
        textViewPresupuesto.text = "$$presupuesto"


// Formatear la cadena usando getString con el valor del presupuesto
        val presupuestoText = getString(R.string.presupuesto_text, presupuesto)
        textViewPresupuesto.text = presupuestoText

        return rootView

    }


   override fun onResume() {
       super.onResume()
       // Mostrar el TextView cuando el fragmento se reanuda
        val textViewPresupuesto = view?.findViewById<TextView>(R.id.textViewPresupuesto)
        textViewPresupuesto?.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        // Ocultar el TextView cuando el fragmento se pausa
        val textViewPresupuesto = view?.findViewById<TextView>(R.id.textViewPresupuesto)
        textViewPresupuesto?.visibility = View.GONE
    }


}




