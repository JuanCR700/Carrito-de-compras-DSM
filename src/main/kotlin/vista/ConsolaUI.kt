package vista

object ConsolaUI {
    const val RESET   = "\u001B[0m"
    const val BOLD    = "\u001B[1m"
    const val CYAN    = "\u001B[36m"
    const val GREEN   = "\u001B[32m"
    const val YELLOW  = "\u001B[33m"
    const val RED     = "\u001B[31m"
    const val MAGENTA = "\u001B[35m"
    const val BLUE    = "\u001B[34m"
    const val WHITE   = "\u001B[37m"

    fun lineaDoble()   = println("$CYAN${".".repeat(60)}$RESET")
    fun lineaSimple()  = println("$CYAN${".".repeat(60)}$RESET")
    fun lineaFina()    = println("$CYAN${"·".repeat(60)}$RESET")

    fun titulo(texto: String) {
        lineaDoble()
        println("$BOLD$CYAN  $texto$RESET")
        lineaDoble()
    }

    fun subtitulo(texto: String) {
        println("\n$BOLD$YELLOW  ▸ $texto$RESET")
        lineaSimple()
    }

    fun exito(msg: String)      = println("$GREEN  ✔  $msg$RESET")
    fun error(msg: String)      = println("$RED  ✖  $msg$RESET")
    fun advertencia(msg: String)= println("$YELLOW  ⚠  $msg$RESET")
    fun info(msg: String)       = println("$CYAN  ℹ  $msg$RESET")

    fun mostrarBanner() {
        println()
        println("$MAGENTA$BOLD")
        println("        ______    ___    __  ___   ______   ____   ____    _   __   ______\n" +
                "       / ____/   /   |  /  |/  /  / ____/  /_  /  / __ \\  / | / /  / ____/\n" +
                "      / / __    / /| | / /|_/ /  / __/    / __/  / / / / /  |/ /  / __/   \n" +
                "     / /_/ /   / ___ |/ /  / /  / /___   /____/ / /_/ / / /|  /  / /___   \n" +
                "     \\____/   /_/  |_|/_/  /_/  \\____/          \\____/  /_/ |_/  \\____/   \n" +
                "                                                                          ")
        println("$RESET")
        println("$CYAN$BOLD                         ==== GAME ZONE ====  $RESET")
        println("$WHITE                   Tu tienda de videojuegos favorita$RESET")
        println()
    }

    fun leerEntrada(prompt: String): String {
        print("$BOLD$CYAN  $prompt$RESET ")
        return readLine()?.trim() ?: ""
    }

    fun leerEnteroONull(prompt: String): Int? {
        val entrada = leerEntrada(prompt)
        return entrada.toIntOrNull()
    }

    fun pausar() {
        leerEntrada("\nPresiona Enter para continuar...")
    }

    fun limpiarPantalla() {
        print("\u001B[H\u001B[2J")
        System.out.flush()
    }
}