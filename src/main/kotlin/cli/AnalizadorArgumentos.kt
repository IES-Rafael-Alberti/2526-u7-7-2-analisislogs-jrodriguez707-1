package org.iesra.cli

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.iesra.dominio.NivelLog

data class Configuracion(
    val ficheroEntrada: String,
    val fechaDesde: LocalDateTime?,
    val fechaHasta: LocalDateTime?,
    val niveles: Set<NivelLog>?,
    val soloEstadisticas: Boolean,
    val informeCompleto: Boolean,
    val ficheroSalida: String?,
    val salidaPorConsola: Boolean,
    val ignorarInvalidas: Boolean
)

object AnalizadorArgumentos {

    fun analizar(args: Array<String>): Configuracion {

        var ficheroEntrada: String? = null
        var fechaDesde: LocalDateTime? = null
        var fechaHasta: LocalDateTime? = null
        var niveles: Set<NivelLog>? = null
        var soloEstadisticas = false
        var informeCompleto = false
        var ficheroSalida: String? = null
        var salidaPorConsola = false
        var ignorarInvalidas = false

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        var i = 0
        while (i < args.size) {

            when (args[i]) {

                "-i", "--input" -> {
                    ficheroEntrada = args.getOrNull(i + 1)
                        ?: throw IllegalArgumentException("Debe indicar un fichero tras -i")
                    i++
                }

                "-f", "--from" -> {
                    fechaDesde = LocalDateTime.parse(args[i + 1], formatter)
                    i++
                }

                "-t", "--to" -> {
                    fechaHasta = LocalDateTime.parse(args[i + 1], formatter)
                    i++
                }

                "-l", "--level" -> {
                    niveles = args[i + 1]
                        .split(",")
                        .map { NivelLog.valueOf(it.trim()) }
                        .toSet()
                    i++
                }

                "-s", "--stats" -> soloEstadisticas = true
                "-r", "--report" -> informeCompleto = true
                "-o", "--output" -> {
                    ficheroSalida = args[i + 1]
                    i++
                }
                "-p", "--stdout" -> salidaPorConsola = true
                "--ignore-invalid" -> ignorarInvalidas = true
                "-h", "--help" -> {
                    mostrarAyuda()
                    throw IllegalArgumentException("Fin de ejecución")
                }

                else -> throw IllegalArgumentException("Opción desconocida: ${args[i]}")
            }

            i++
        }

        if (ficheroEntrada == null) {
            throw IllegalArgumentException("Debe indicar al menos el fichero de entrada")
        }

        if (!salidaPorConsola && ficheroSalida == null) {
            throw IllegalArgumentException("Debe indicar --stdout o --output")
        }

        if (!soloEstadisticas && !informeCompleto) {
            informeCompleto = true
        }

        return Configuracion(
            ficheroEntrada,
            fechaDesde,
            fechaHasta,
            niveles,
            soloEstadisticas,
            informeCompleto,
            ficheroSalida,
            salidaPorConsola,
            ignorarInvalidas
        )
    }

    private fun mostrarAyuda() {
        println("Uso: logtool -i <fichero> [opciones]")
    }
}