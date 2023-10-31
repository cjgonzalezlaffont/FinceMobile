package com.example.fince

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class RegistroFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_registro, container, false)
        val btn_registrarse: Button = view.findViewById(R.id.btn_registrarse)
        val btn_cancelar: Button = view.findViewById(R.id.btn_cancelar)
        btn_registrarse.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, PerfilRiesgoFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        };
        btn_cancelar.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, LoginFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        };
        return view
    }
}
