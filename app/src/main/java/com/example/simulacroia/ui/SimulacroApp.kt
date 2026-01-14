package com.example.simulacroia.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.simulacroexamen.R
import com.example.simulacroia.modelo.Usuario
import com.example.simulacroia.ui.pantallas.PantallaInicio
import com.example.simulacroia.ui.pantallas.PantallaNuevo
import com.example.simulacroia.ui.pantallas.PantallaTareas

enum class Pantallas(@StringRes val titulo: Int) {
    Listado(R.string.listado),
    Tareas(R.string.tareas),
    Nuevo(R.string.nuevo)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SimulacroApp(
    viewModel: UsuarioViewModel = viewModel(factory = UsuarioViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val pilaRetroceso by navController.currentBackStackEntryAsState()

    val pantallaActual = Pantallas.valueOf(
        pilaRetroceso?.destination?.route ?: Pantallas.Listado.name
    )

    Scaffold(
        topBar = {
            AppTopBar(
                pantallaActual = pantallaActual,
                puedeNavegarAtras = navController.previousBackStackEntry != null,
                onNavegarAtras = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            if (pantallaActual == com.example.simulacroia.ui.Pantallas.Listado) {
                FloatingActionButton(
                    onClick = { navController.navigate(com.example.simulacroia.ui.Pantallas.Nuevo.name) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.insertar_usuario)
                    )
                }
            }
        }
    ) { innerPadding ->

        val uiState = viewModel.usuarioUIState

        NavHost(
            navController = navController,
            startDestination = Pantallas.Listado.name,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Pantallas.Listado.name) {
                PantallaInicio(
                    appUIState = uiState,
                    onUsuariosObtenidos = { viewModel.obtenerUsuarios() },
                    onUsuarioPulsado = {
                        viewModel.actualizarUsuarioPulsado(it)
                        navController.navigate(Pantallas.Tareas.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(Pantallas.Tareas.name) {
                PantallaTareas(
                    usuario = viewModel.usuarioPulsado,
                    onAnadirTarea = { texto ->
                        viewModel.anadirTarea(texto)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(Pantallas.Nuevo.name) {
                PantallaNuevo(
                    onCrear = { nuevoUsuario: Usuario ->
                        viewModel.insertarUsuario(nuevoUsuario)
                        navController.popBackStack(Pantallas.Listado.name, false)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: Pantallas,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(id = pantallaActual.titulo)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (puedeNavegarAtras) {
                IconButton(onClick = onNavegarAtras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.atras)
                    )
                }
            }
        },
        modifier = modifier
    )
}