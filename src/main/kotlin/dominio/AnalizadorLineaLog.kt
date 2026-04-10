package org.iesra.dominio

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AnalizadorLineaLog {

    private val formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val patron = Regex("""\[(.*?)\]\s+(INFO|WARNING|ERROR)\s+(.*)""")

    fun analizar(linea: String): Result<EntradaLog> {

        val coincidencia = patron.matchEntire(linea)
            ?: return Result.failure(IllegalArgumentException("Línea mal formada"))

        return try {
            val fecha = LocalDateTime.parse(coincidencia.groupValues[1], formateador)

            val nivel = NivelLog.desdeTexto(coincidencia.groupValues[2])
                ?: return Result.failure(IllegalArgumentException("Nivel no reconocido"))

            val mensaje = coincidencia.groupValues[3]

            Result.success(
                EntradaLog(
                    fechaHora = fecha,
                    nivel = nivel,
                    mensaje = mensaje
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}