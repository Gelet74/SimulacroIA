package com.example.simulacroia.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simulacroia.modelo.Tarea
import com.example.simulacroia.modelo.Usuario

@Composable
fun PantallaNuevo(
    onCrear: (Usuario) -> Unit,
    modifier: Modifier = Modifier
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var tarea by remember { mutableStateOf("") }

    val formularioValido = nombre.isNotBlank() && telefono.isNotBlank() && tarea.isNotBlank()

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Móvil del usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        OutlinedTextField(
            value = tarea,
            onValueChange = { tarea = it },
            label = { Text("Escribe una tarea") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        Button(
            onClick = {
                val nuevoUsuario = Usuario(
                    id = "",
                    nombre = nombre,
                    telefono = telefono,
                    tareas = listOf(
                        Tarea(
                            completada = false,
                            fecha = "",
                            descripcion = ""

                        )
                    )
                )
                onCrear(nuevoUsuario)
            },
            enabled = formularioValido,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Text("Añadir")
        }
    }
}