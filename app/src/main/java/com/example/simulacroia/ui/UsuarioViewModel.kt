package com.example.simulacroia.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavBackStackEntry
import com.example.simulacroia.SimulacroAplicacion
import com.example.simulacroia.datos.UsuarioRepositorio
import com.example.simulacroia.modelo.Tarea
import com.example.simulacroia.modelo.Usuario
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface UsuarioUIState {
    data class ObtenerExito(val usuarios: List<Usuario>) : UsuarioUIState
    data class CrearExito(val usuario: Usuario) : UsuarioUIState
    data class ActualizarExito(val usuario: Usuario) : UsuarioUIState
    data class EliminarExito(val id: String) : UsuarioUIState

    object Error : UsuarioUIState
    object Cargando : UsuarioUIState
}

class UsuarioViewModel(
    private val usuarioRepositorio: UsuarioRepositorio
) : ViewModel() {

    var usuarioUIState: UsuarioUIState by mutableStateOf(UsuarioUIState.Cargando)
        private set

    var usuarioPulsado: Usuario by mutableStateOf(
        Usuario(id = "", nombre = "", telefono = "")
    )
        private set

    init {
        obtenerUsuarios()
    }

    fun actualizarUsuarioPulsado(usuario: NavBackStackEntry) {
        usuarioPulsado = usuario
    }

    fun obtenerUsuarios() {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                val listaUsuarios = usuarioRepositorio.obtenerUsuarios()
                UsuarioUIState.ObtenerExito(listaUsuarios)
            } catch (e: IOException) {
                UsuarioUIState.Error
            } catch (e: HttpException) {
                UsuarioUIState.Error
            }
        }
    }

    fun insertarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                val usuarioInsertado = usuarioRepositorio.insertarUsuario(usuario)
                obtenerUsuarios() // refrescar lista
                UsuarioUIState.CrearExito(usuarioInsertado)
            } catch (e: IOException) {
                UsuarioUIState.Error
            } catch (e: HttpException) {
                UsuarioUIState.Error
            }
        }
    }

    fun anadirTarea(texto: String, fecha: String) {
        viewModelScope.launch {
            try {
                val nuevaTarea = usuarioPulsado.tareas + Tarea(
                    completada = false,
                    descripcion = texto,
                    fecha = fecha
                )

                val usuarioActualizado = usuarioPulsado.copy(
                    tareas = nuevaTarea
                )

                val resultado = usuarioRepositorio.actualizarUsuario(
                    usuarioActualizado.id,
                    usuarioActualizado
                )

                usuarioPulsado = resultado
                usuarioUIState = UsuarioUIState.ActualizarExito(resultado)

            } catch (e: Exception) {
                usuarioUIState = UsuarioUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as SimulacroAplicacion)
                val usuarioRepositorio = aplicacion.contenedor.usuarioRepositorio
                UsuarioViewModel(usuarioRepositorio = usuarioRepositorio)
            }
        }
    }
}