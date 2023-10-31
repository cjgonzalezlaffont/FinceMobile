package com.example.fince

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class LoginFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_login, container, false)
        val btnLogin: Button = view.findViewById(R.id.btnLogin)
        val btnRegistro: Button = view.findViewById(R.id.btnRegistro)
        btnLogin.setOnClickListener {
            val intent = Intent(requireActivity(), InterfazActivity::class.java)
            startActivity(intent)
        };
        btnRegistro.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, RegistroFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        };
        return view
    }
}