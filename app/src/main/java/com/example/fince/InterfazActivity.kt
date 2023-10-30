package com.example.fince

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView



class InterfazActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz)


                // Agrega aquí el código del BottomNavigationView
                val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

                bottomNavigation.setOnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.menu_item1 -> {
                            // Cargar el primer fragment cuando se hace clic en el elemento de menú
                            val fragment = PrincipalFragment()
                            val transaction = supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_container, fragment)
                            transaction.commit()
                        }
                        R.id.menu_item2 -> {
                            // Cargar el segundo fragment cuando se hace clic en el elemento de menú 2
                            val fragment =CarteraFragment()
                            val transaction = supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_container, fragment)
                            transaction.commit()
                        }
                        R.id.menu_item3 -> {
                            // Cargar un tercer fragment cuando se hace clic en el elemento de menú 3
                            val fragment = PresupuestoFragment()
                            val transaction = supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_container, fragment)
                            transaction.commit()
                        }
                        R.id.menu_item4 -> {
                            // Cargar un tercer fragment cuando se hace clic en el elemento de menú 3
                            val fragment = PerfilFragment()
                            val transaction = supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_container, fragment)
                            transaction.commit()
                        }
                        // Agrega más casos para otros elementos de menú si es necesario
                    }
                    true
                }
            }
        }

