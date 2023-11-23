package com.example.fince

import android.app.Application
import com.example.fince.core.Config
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppFince: Application() {
    override fun onCreate() {
        super.onCreate()
        Config.apiKey = "algo"
        //IPCONFIG /ALL CAMBIAR IP PARA CADA MAQUINA IPV4
        //Config.baseUrl = "http://192.168.0.8:8080/" //NAHUEL
        Config.baseUrl="http://192.168.0.24:8080" //Martin
        //Config.baseUrl="http://192.168.1.5:8080" //Fede
       //Config.baseUrl = "http://192.168.0.78:8080" // Tais
        //Config.baseUrl="http://192.168.0.14:8080" //Javier

    }
}