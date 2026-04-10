package org.iesra.servicio

import org.iesra.dominio.*

class GeneradorInforme {

    fun generar(
        nombreFichero: String,
        desde: String?,
        hasta: String?,
        niveles: Set<NivelLog>?,
        estadisticas: EstadisticasLog.ResultadoEstadisticas
    ): String {

        return buildString {

            appendLine("INFORME DE LOGS")
            appendLine("===============")
            appendLine("Fichero analizado: $nombreFichero")
            appendLine("Rango aplicado: ${desde ?: "Sin límite"} -> ${hasta ?: "Sin límite"}")
            appendLine("Niveles incluidos: ${niveles?.joinToString() ?: "Todos"}")
            appendLine()
            appendLine("Resumen:")
            appendLine("- Líneas procesadas: ${estadisticas.totalProcesadas}")
            appendLine("- Líneas válidas: ${estadisticas.totalValidas}")
            appendLine("- Líneas inválidas: ${estadisticas.totalInvalidas}")
            appendLine()
            appendLine("Conteo por nivel:")
            appendLine("- INFO: ${estadisticas.cantidadInfo}")
            appendLine("- WARNING: ${estadisticas.cantidadWarning}")
            appendLine("- ERROR: ${estadisticas.cantidadError}")
            appendLine()
            appendLine("Periodo detectado en los logs:")
            appendLine("- Primera entrada: ${estadisticas.primeraFecha ?: "N/A"}")
            appendLine("- Última entrada: ${estadisticas.ultimaFecha ?: "N/A"}")
        }
    }
}