package com.example.simulacroia.ui.pantallas


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.simulacroia.modelo.Tarea
import com.example.simulacroia.modelo.Usuario

@Composable
fun PantallaTareas(
    usuario: Usuario?,
    onAnadirTarea: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Si el usuario aún no está cargado, mostramos un mensaje temporal
    if (usuario == null) {
        Text(
            text = "Cargando usuario…",
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    var tareaNueva by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        // LISTA DE TAREAS
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(usuario.tareas) { tarea ->
                TareaItem(tarea)
            }
        }

        // CAMPO DE TEXTO + BOTÓN ENVIAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = tareaNueva,
                onValueChange = { tareaNueva = it },
                label = { Text("Escribe una tarea") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    onAnadirTarea(tareaNueva)
                    tareaNueva = ""
                },
                enabled = tareaNueva.isNotBlank()
            ) {
                Text("Añadir")
            }
        }
    }
}


@Composable
fun TareaItem(tarea: Tarea) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
            ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.widthIn(max = 260.dp)
        ) {
            Text(
                text = tarea.descripcion,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}