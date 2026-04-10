package org.iesra.infraestructura

import java.io.File

object LectorFichero {

    fun leerLineas(ruta: String): List<String> {
        val fichero = File(ruta)

        if (!fichero.exists()) {
            throw IllegalArgumentException("El fichero no existe: $ruta")
        }

        return fichero.readLines()
    }
}