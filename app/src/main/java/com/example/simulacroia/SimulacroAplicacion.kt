package com.example.simulacroia

import android.app.Application
import com.example.simulacroia.datos.ContenedorApp
import com.example.simulacroia.datos.UsuarioContenedorApp


class SimulacroAplicacion : Application() {
    lateinit var contenedor : ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = UsuarioContenedorApp()
    }
}