package facturacion

import modelo.CarritoDeCompras
import modelo.Inventario
import vista.ConsolaUI

object VistaFactura {

    fun procesarFacturacion(carrito: CarritoDeCompras, inventario: Inventario): Boolean {
        ConsolaUI.limpiarPantalla()
        ConsolaUI.titulo("CONFIRMAR COMPRA")

        if (carrito.estaVacio()) {
            ConsolaUI.advertencia("El carrito esta vacio.")
            ConsolaUI.pausar()
            return false
        }

        println()
        val nombre = ConsolaUI.leerEntrada("Tu nombre completo:")
        if (nombre.isBlank()) {
            ConsolaUI.error("El nombre no puede estar vacio.")
            ConsolaUI.pausar()
            return false
        }

        val correo = ConsolaUI.leerEntrada("Tu correo electronico:")
        if (!correoValido(correo)) {
            ConsolaUI.error("Correo electronico invalido.")
            ConsolaUI.pausar()
            return false
        }

        println()
        ConsolaUI.info("Total a pagar: \$${"%.2f".format(carrito.calcularTotal())}")
        val confirmar = ConsolaUI.leerEntrada("¿Confirmar compra? (s/n):")
        if (confirmar.lowercase() != "s") {
            ConsolaUI.info("Compra cancelada. Tu carrito sigue guardado.")
            ConsolaUI.pausar()
            return false
        }

        val factura = Factura(carrito, correo, nombre)

        try {
            inventario.descontarStock(carrito.obtenerItems())
        } catch (e: IllegalArgumentException) {
            ConsolaUI.error("Error al procesar el stock: ${e.message}")
            ConsolaUI.pausar()
            return false
        }

        ConsolaUI.limpiarPantalla()
        println()
        println(factura.generarTexto())

        ConsolaUI.info("Enviando factura a $correo...")
        val enviado = ServicioCorreo.enviarFactura(factura)

        if (enviado) {
            ConsolaUI.exito("¡Factura enviada exitosamente a $correo!")
        } else {
            ConsolaUI.advertencia("No se pudo enviar el correo, pero tu compra fue procesada.")
            ConsolaUI.info("Guarda el numero de factura: ${factura.numero}")
        }
        carrito.vaciar()

        println()
        ConsolaUI.pausar()
        return true
    }

    private fun correoValido(correo: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return correo.matches(regex)
    }
}