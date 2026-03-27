package util

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {

    private val archivo = File("gamezone_log.txt")
    private val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    init {
        escribir(".".repeat(60))
        escribir("  GAMEZONE STORE — LOG DE SESION")
        escribir("  Inicio: ${LocalDateTime.now().format(formato)}")
        escribir(".".repeat(60))
    }

    fun info(mensaje: String) = log("INFO ", mensaje)
    fun accion(mensaje: String) = log("ACCION", mensaje)
    fun advertencia(mensaje: String) = log("WARN ", mensaje)
    fun error(mensaje: String) = log("ERROR", mensaje)
    fun error(mensaje: String, excepcion: Exception) {
        log("ERROR", "$mensaje → ${excepcion.message}")
        escribir("  Stack: ${excepcion.stackTraceToString().lines().take(5).joinToString("\n  ")}")
    }

    fun cerrar() {
        escribir(".".repeat(60))
        escribir("  Cierre: ${LocalDateTime.now().format(formato)}")
        escribir(".".repeat(60))
        escribir("")
    }

    private fun log(nivel: String, mensaje: String) {
        val timestamp = LocalDateTime.now().format(formato)
        val linea = "[$timestamp] [$nivel] $mensaje"
        escribir(linea)
        if (nivel == "ERROR") println("  [LOG] $linea")
    }

    private fun escribir(linea: String) {
        try {
            archivo.appendText("$linea\n")
        } catch (e: Exception) {
            println("  [LOG WARNING] No se pudo escribir en el log: ${e.message}")
        }
    }
}