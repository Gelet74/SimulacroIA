package com.example.simulacroia.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tarea(
    @SerialName(value="descripcion")
    val descripcion: String,
    @SerialName(value= "completada")
    val completada: Boolean,
    @SerialName(value="fecha")
    val fecha: String
)
