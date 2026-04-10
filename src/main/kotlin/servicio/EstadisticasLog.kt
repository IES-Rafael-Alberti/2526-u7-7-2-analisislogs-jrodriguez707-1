package org.iesra.servicio

import org.iesra.dominio.*

class EstadisticasLog {

    data class ResultadoEstadisticas(
        val totalProcesadas: Int,
        val totalValidas: Int,
        val totalInvalidas: Int,
        val cantidadInfo: Int,
        val cantidadWarning: Int,
        val cantidadError: Int,
        val primeraFecha: String?,
        val ultimaFecha: String?
    )

    fun calcular(
        totalLineas: Int,
        entradasValidas: List<EntradaLog>,
        totalInvalidas: Int
    ): ResultadoEstadisticas {

        val ordenadas = entradasValidas.sortedBy { it.fechaHora }

        return ResultadoEstadisticas(
            totalProcesadas = totalLineas,
            totalValidas = entradasValidas.size,
            totalInvalidas = totalInvalidas,
            cantidadInfo = entradasValidas.count { it.nivel == NivelLog.INFO },
            cantidadWarning = entradasValidas.count { it.nivel == NivelLog.WARNING },
            cantidadError = entradasValidas.count { it.nivel == NivelLog.ERROR },
            primeraFecha = ordenadas.firstOrNull()?.fechaHora?.toString(),
            ultimaFecha = ordenadas.lastOrNull()?.fechaHora?.toString()
        )
    }
}