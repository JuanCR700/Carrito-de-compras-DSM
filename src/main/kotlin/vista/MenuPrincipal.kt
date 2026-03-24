package vista

import modelo.CarritoDeCompras
import modelo.Inventario
import vista.ConsolaUI.CYAN
import vista.ConsolaUI.RESET

class MenuPrincipal(
    private val inventario: Inventario,
    private val carrito: CarritoDeCompras
) {

    fun iniciar() {
        ConsolaUI.limpiarPantalla()
        ConsolaUI.mostrarBanner()

        var corriendo = true
        while (corriendo) {
            corriendo = procesarMenuPrincipal()
        }

        despedida()
    }

    private fun mostrarOpcionesPrincipales() {
        ConsolaUI.titulo("                    MENU PRINCIPAL")
        println("  $CYAN[1]$RESET  Ver catalogo de juegos")
        println("  $CYAN[2]$RESET  Ver mi carrito ${resumenCarrito()}")
        println("  $CYAN[3]$RESET  Finalizar compra / Factura")
        println("  $CYAN[0]$RESET  Salir")
        println()
    }

    private fun procesarMenuPrincipal(): Boolean {
        mostrarOpcionesPrincipales()
        val opcion = ConsolaUI.leerEnteroONull("Elige una opcion:") ?: -1

        return when (opcion) {
            1    -> { flujoAgregarProducto(); true }
            2    -> { flujoVerCarrito(); true }
            3    -> { flujoConfirmarCompra(); true }
            0    -> false
            else -> { ConsolaUI.error("Opcion no valida. Intenta de nuevo."); true }
        }
    }

    private fun flujoAgregarProducto() {
        ConsolaUI.limpiarPantalla()
        VistaProductos.mostrarCatalogo(inventario)

        val producto = VistaProductos.seleccionarProducto(inventario) ?: run {
            ConsolaUI.pausar()
            return
        }

        val cantidad = VistaProductos.pedirCantidad(producto) ?: run {
            ConsolaUI.pausar()
            return
        }

        try {
            carrito.agregarProducto(producto, cantidad)
            val item = carrito.buscarItem(producto.id)
            VistaCarrito.confirmarAgregado(producto.nombre, cantidad, item?.subtotal ?: 0.0)
        } catch (e: IllegalArgumentException) {
            ConsolaUI.error(e.message ?: "Error al agregar el producto.")
        }

        ConsolaUI.pausar()
    }

    private fun flujoVerCarrito() {
        var enCarrito = true
        while (enCarrito) {
            ConsolaUI.limpiarPantalla()
            VistaCarrito.mostrarCarrito(carrito)

            val opcion = VistaCarrito.mostrarMenuCarrito()
            when (opcion) {
                1    -> { enCarrito = false; flujoAgregarProducto() }
                2    -> flujoEliminarDelCarrito()
                3    -> { enCarrito = false; flujoConfirmarCompra() }
                0    -> enCarrito = false
                else -> ConsolaUI.error("Opcion no valida.")
            }
        }
    }

    private fun flujoEliminarDelCarrito() {
        val productoId = VistaCarrito.pedirItemAEliminar(carrito) ?: return

        val nombreProducto = carrito.buscarItem(productoId)?.producto?.nombre ?: "Producto"
        val eliminado = carrito.eliminarProducto(productoId)

        if (eliminado) {
            VistaCarrito.confirmarEliminado(nombreProducto)
        } else {
            ConsolaUI.error("No se pudo eliminar el producto.")
        }
        ConsolaUI.pausar()
    }

    private fun flujoConfirmarCompra() {
        if (carrito.estaVacio()) {
            ConsolaUI.advertencia("Tu carrito esta vacio. Agrega productos antes de confirmar.")
            ConsolaUI.pausar()
            return
        }

        ConsolaUI.limpiarPantalla()
        VistaCarrito.mostrarCarrito(carrito)

        val confirmacion = ConsolaUI.leerEntrada("¿Confirmar compra? (s/n):")
        if (confirmacion.lowercase() == "s") {
            facturacion.VistaFactura.procesarFacturacion(carrito, inventario)
        } else {
            ConsolaUI.info("Compra cancelada. Tu carrito sigue guardado.")
        }
        ConsolaUI.pausar()
    }

    private fun resumenCarrito(): String {
        if (carrito.estaVacio()) return ""
        return "(${carrito.cantidadDeItems()} ítem(s) · \$${"%.2f".format(carrito.calcularTotal())})"
    }

    private fun despedida() {
        ConsolaUI.limpiarPantalla()
        ConsolaUI.mostrarBanner()
        ConsolaUI.titulo("Gracias por visitar GameZone Store ^_^")
        println("  Esperamos verte pronto.\n")
    }
}