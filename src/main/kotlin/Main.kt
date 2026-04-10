package org.iesra

import org.iesra.cli.AnalizadorArgumentos
import org.iesra.dominio.EntradaLog
import org.iesra.dominio.AnalizadorLineaLog
import org.iesra.infraestructura.LectorFichero
import org.iesra.infraestructura.EscritorSalida
import org.iesra.servicio.FiltroLog
import org.iesra.servicio.EstadisticasLog
import org.iesra.servicio.GeneradorInforme

fun main(args: Array<String>) {

    try {

        // 1️⃣ Analizar argumentos
        val config = AnalizadorArgumentos.analizar(args)

        // 2️⃣ Leer fichero
        val lineas = LectorFichero.leerLineas(config.ficheroEntrada)

        val analizadorLineas = AnalizadorLineaLog()

        val entradasValidas = mutableListOf<EntradaLog>()
        var totalInvalidas = 0

        // 3️⃣ Parsear cada línea
        for (linea in lineas) {
            val resultado = analizadorLineas.analizar(linea)

            if (resultado.isSuccess) {
                entradasValidas.add(resultado.getOrThrow())
            } else {
                totalInvalidas++
                if (!config.ignorarInvalidas) {
                    println("Línea inválida detectada: $linea")
                }
            }
        }

        // 4️⃣ Aplicar filtros
        val filtro = FiltroLog()
        val entradasFiltradas = filtro.filtrar(
            entradas = entradasValidas,
            desde = config.fechaDesde,
            hasta = config.fechaHasta,
            niveles = config.niveles
        )

        // 5️⃣ Calcular estadísticas
        val servicioEstadisticas = EstadisticasLog()
        val estadisticas = servicioEstadisticas.calcular(
            totalLineas = lineas.size,
            entradasValidas = entradasFiltradas,
            totalInvalidas = totalInvalidas
        )

        // 6️⃣ Generar informe
        val generador = GeneradorInforme()
        val salida = generador.generar(
            nombreFichero = config.ficheroEntrada,
            desde = config.fechaDesde?.toString(),
            hasta = config.fechaHasta?.toString(),
            niveles = config.niveles,
            estadisticas = estadisticas
        )

        // 7️⃣ Mostrar o guardar salida
        if (config.salidaPorConsola) {
            EscritorSalida.escribirEnConsola(salida)
        }

        if (config.ficheroSalida != null) {
            EscritorSalida.escribirEnFichero(config.ficheroSalida, salida)
        }

    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}