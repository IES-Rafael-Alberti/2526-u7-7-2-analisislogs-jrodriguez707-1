package org.iesra.infraestructura

import java.io.File

object EscritorSalida {

    fun escribirEnFichero(ruta: String, contenido: String) {
        File(ruta).writeText(contenido)
    }

    fun escribirEnConsola(contenido: String) {
        println(contenido)
    }
}