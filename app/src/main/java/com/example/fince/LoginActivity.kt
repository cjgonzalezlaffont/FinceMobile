package com.example.fince

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var estaLogueado=false;
        /*
            Aca iria el codigo para verificar si esta logueado ya
            si ese es el caso pone estaLogueado como true
         */
        if(estaLogueado){
            val intent = Intent(this, InterfazActivity::class.java)
            startActivity(intent)
            this.finish();
        } else {
            supportFragmentManager.beginTransaction().add(R.id.container, LoginFragment()).commit();
        }
    }
}