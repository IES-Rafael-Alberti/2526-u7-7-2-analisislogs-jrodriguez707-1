package org.iesra.dominio

enum class NivelLog {
    INFO, WARNING, ERROR;

    companion object {
        fun desdeTexto(valor: String): NivelLog? =
            entries.find { it.name == valor.uppercase() }
    }
}