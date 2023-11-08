package com.example.fince.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fince.R
import com.example.fince.InterfazActivity

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
        val txtUser: EditText = view.findViewById(R.id.txtUser);
        val txtPass: EditText = view.findViewById(R.id.txtPass);
        val btnLogin: Button = view.findViewById(R.id.btnLogin)
        val btnRegistro: Button = view.findViewById(R.id.btnRegistro)
        btnLogin.setOnClickListener {
            var usuarioCorrecto: Boolean;
            val usuario=txtUser.text.toString();
            val pass=txtPass.text.toString();
            if(!usuario.equals("") && !pass.equals("")) {
                /*
                    ACA IRIA EL CODIGO QUE VERIFICA SI EL USUARIO ES CORRECTO
                    si es correcto pone: */ usuarioCorrecto = true /*
                    si no: usuarioCorrecto=false
             */
                if (usuarioCorrecto) {
                    val intent = Intent(requireActivity(), InterfazActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish();
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Usuario y/o Contraseña incorrecto!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Complete todos los campos!",
                    Toast.LENGTH_SHORT
                ).show()
            }
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