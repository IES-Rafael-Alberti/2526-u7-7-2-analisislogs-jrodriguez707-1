package org.iesra.servicio

import org.iesra.dominio.*
import java.time.LocalDateTime

class FiltroLog {

    fun filtrar(
        entradas: List<EntradaLog>,
        desde: LocalDateTime?,
        hasta: LocalDateTime?,
        niveles: Set<NivelLog>?
    ): List<EntradaLog> {

        return entradas.filter { entrada ->

            val dentroRango =
                (desde == null || !entrada.fechaHora.isBefore(desde)) &&
                        (hasta == null || !entrada.fechaHora.isAfter(hasta))

            val nivelPermitido =
                niveles == null || niveles.contains(entrada.nivel)

            dentroRango && nivelPermitido
        }
    }
}