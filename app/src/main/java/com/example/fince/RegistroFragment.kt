package com.example.fince

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class RegistroFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_registro, container, false)
        val btn_registrarse: Button = view.findViewById(R.id.btn_registrarse)
        val btn_cancelar: Button = view.findViewById(R.id.btn_cancelar)

        val txtNombre : EditText = view.findViewById(R.id.txtNombre)
        val txtMail : EditText = view.findViewById(R.id.txtMail)
        val txtPassword : EditText = view.findViewById(R.id.txtPassword)
        val txtConfirmPassword : EditText = view.findViewById(R.id.txtConfirmPassword)
        val txtFecha : EditText = view.findViewById(R.id.txtFecha)
        val txtDireccion : EditText = view.findViewById(R.id.txtDireccion)
        val txtTelefono : EditText = view.findViewById(R.id.txtTelefono)



        btn_registrarse.setOnClickListener {
            val nombre= txtNombre.text.toString()
            val mail= txtMail.text.toString()
            val password= txtPassword.text.toString()
            val confirmPassword= txtConfirmPassword.text.toString()
            val fecha= txtFecha.text.toString()
            val direccion= txtDireccion.text.toString()
            val teleofno= txtTelefono.text.toString()

            if(!nombre.equals("") && !mail.equals("") && !password.equals("") && !confirmPassword.equals("") && !fecha.equals("") && !direccion.equals("") && !teleofno.equals("")){
                var registroExitoso =false;
                var errorRegistro=""
                /*
                       ACA IRIA LA CONECCION A LA API QUE HACE EL REGISTRO:
                       si el registro fue exitoso debe hacer: */registroExitoso=true/*
                       y de fallar debe colocar en error registro el motivo del error al registrarse
                 */
                if(registroExitoso) {
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container, PerfilRiesgoFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }else {
                    Toast.makeText(requireContext(), "Error al hacer el registro "+errorRegistro, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show()
            }
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
