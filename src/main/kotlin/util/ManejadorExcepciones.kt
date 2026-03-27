package util

object ManejadorExcepciones {

    fun registrar() {
        Thread.setDefaultUncaughtExceptionHandler { hilo, excepcion ->
            Logger.error(
                "Excepcion no capturada en hilo '${hilo.name}'",
                excepcion as? Exception ?: Exception(excepcion)
            )
            println("\n  ╔══════════════════════════════════════════╗")
            println("    ║  ¡Ocurrio un error inesperado!           ║")
            println("    ║  El detalle fue guardado en:             ║")
            println("    ║  gamezone_log.txt                        ║")
            println("    ╚══════════════════════════════════════════╝\n")
        }
    }

    fun ejecutarSeguro(contexto: String, bloque: () -> Unit): Boolean {
        return try {
            bloque()
            true
        } catch (e: IllegalArgumentException) {
            Logger.advertencia("$contexto → ${e.message}")
            false
        } catch (e: Exception) {
            Logger.error(contexto, e)
            false
        }
    }
}