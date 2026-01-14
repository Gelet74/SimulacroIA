package com.example.simulacroia.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Usuario (
    @SerialName(value="id")
    val id: String="",
    @SerialName(value="nombre")
    val nombre: String="",
    @SerialName(value="telefono")
    val telefono: String="",
    @SerialName (value ="tareas")
    val tareas: List<Tarea> = emptyList()
)