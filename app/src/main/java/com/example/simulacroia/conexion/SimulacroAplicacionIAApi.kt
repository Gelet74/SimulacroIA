package com.example.simulacroia.conexion

import com.example.simulacroia.modelo.Usuario
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SimulacroAplicacionIAApi {

    @GET(value= "usuario")
    suspend fun obtenerUsuarios(): List<Usuario>

    @POST(value = "usuario")
    suspend fun insertarUsuario (
       @Body usuario: Usuario
    ): Usuario

    @PUT (value = "usuario/{id}")
    suspend fun actualizarUsuario(
        @Path (value="id") id:String,
        @Body usuario: Usuario
    ):Usuario

    @DELETE(value = "usuario/{id}")
    suspend fun eliminarUsuario(
        @Path (value="id") id:String
    ): Usuario
}