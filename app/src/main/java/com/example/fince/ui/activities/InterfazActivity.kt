package com.example.fince.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fince.ui.fragments.CarteraFragment
import com.example.fince.ui.fragmereturn.PerfilFragment
import com.example.fince.ui.fragments.PresupuestoFragment
import com.example.fince.ui.fragments.PrincipalFragment
import com.example.fince.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class InterfazActivity : AppCompatActivity() {
    fun abrirFargment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz)
        abrirFargment(PrincipalFragment());

        // Agrega aquí el código del BottomNavigationView
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.inicio -> {
                    val fragment = PrincipalFragment()
                    abrirFargment(fragment);
                }
                R.id.cartera -> {
                    val fragment = CarteraFragment()
                    abrirFargment(fragment);
                }
                R.id.presupuesto -> {
                    val fragment = PresupuestoFragment()
                    abrirFargment(fragment);
                }
                R.id.perfil -> {
                    val fragment = PerfilFragment()
                    abrirFargment(fragment);
                }
            }
            true
        }
    }
}

