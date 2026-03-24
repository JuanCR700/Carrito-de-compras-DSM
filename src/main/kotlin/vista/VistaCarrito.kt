package vista

import modelo.CarritoDeCompras
import vista.ConsolaUI.BOLD
import vista.ConsolaUI.CYAN
import vista.ConsolaUI.GREEN
import vista.ConsolaUI.RED
import vista.ConsolaUI.RESET
import vista.ConsolaUI.WHITE
import vista.ConsolaUI.YELLOW

object VistaCarrito {

    fun mostrarCarrito(carrito: CarritoDeCompras) {
        ConsolaUI.subtitulo("                    Tu Carrito de Compras")

        if (carrito.estaVacio()) {
            ConsolaUI.advertencia("Tu carrito esta vacio :c. Agrega algunos juegos!!")
            return
        }

        println(
            "$BOLD$CYAN  %-4s %-35s %-6s %-12s %s$RESET"
                .format("#", "Juego", "Cant.", "P. Unitario", "Subtotal")
        )
        ConsolaUI.lineaSimple()

        carrito.obtenerItems().forEachIndexed { index, item ->
            println(
                "  $CYAN%-4d$RESET %-35s $YELLOW%-6d$RESET $GREEN\$%-11.2f$RESET $GREEN\$%.2f$RESET"
                    .format(
                        index + 1,
                        item.producto.nombre,
                        item.cantidad,
                        item.producto.precio,
                        item.subtotal
                    )
            )
        }

        ConsolaUI.lineaSimple()
        println("  $WHITE%-45s $GREEN\$%.2f$RESET".format("Subtotal:", carrito.calcularSubtotal()))
        println("  $WHITE%-45s $YELLOW\$%.2f$RESET".format("IVA (13%):", carrito.calcularIVA()))
        ConsolaUI.lineaFina()
        println("  $BOLD%-45s $GREEN\$%.2f$RESET".format("TOTAL:", carrito.calcularTotal()))
        println()
    }

    fun mostrarMenuCarrito(): Int {
        ConsolaUI.subtitulo("Opciones del Carrito")
        println("  $CYAN[1]$RESET Seguir comprando")
        println("  $CYAN[2]$RESET Eliminar un producto del carrito")
        println("  $CYAN[3]$RESET Confirmar compra y generar factura")
        println("  $CYAN[0]$RESET Volver al menu principal")
        println()

        return ConsolaUI.leerEnteroONull("Elige una opcion:") ?: -1
    }

    fun pedirItemAEliminar(carrito: CarritoDeCompras): Int? {
        if (carrito.estaVacio()) {
            ConsolaUI.advertencia("El carrito ya esta vacio.")
            return null
        }

        mostrarCarrito(carrito)

        val numero = ConsolaUI.leerEnteroONull("Ingresa el numero del producto a eliminar (0 para cancelar):")

        if (numero == null || numero == 0) return null

        val items = carrito.obtenerItems()
        if (numero < 1 || numero > items.size) {
            ConsolaUI.error("Numero invalido. Elige entre 1 y ${items.size}.")
            return null
        }

        return items[numero - 1].producto.id
    }

    fun confirmarAgregado(nombreProducto: String, cantidad: Int, subtotal: Double) {
        println()
        ConsolaUI.exito("¡$nombreProducto x$cantidad agregado al carrito!")
        println("  $WHITE  Subtotal del item: $GREEN\$${"%.2f".format(subtotal)}$RESET")
        println()
    }

    fun confirmarEliminado(nombreProducto: String) {
        ConsolaUI.exito("'$nombreProducto' eliminado del carrito.")
    }
}