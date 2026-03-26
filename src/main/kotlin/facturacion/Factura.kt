package facturacion

import modelo.CarritoDeCompras
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Factura(
    val carrito: CarritoDeCompras,
    val correoCliente: String,
    val nombreCliente: String
) {
    val numero: String = generarNumero()
    val fechaHora: String = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))

    val subtotal: Double = carrito.calcularSubtotal()
    val iva: Double      = carrito.calcularIVA()
    val total: Double    = carrito.calcularTotal()

    private fun generarNumero(): String {
        val timestamp = System.currentTimeMillis().toString().takeLast(8)
        return "GZ-$timestamp"
    }

    fun generarTexto(): String {
        val sb = StringBuilder()
        sb.appendLine("═".repeat(55))
        sb.appendLine("         GAMEZONE STORE — FACTURA ELECTRÓNICA")
        sb.appendLine("═".repeat(55))
        sb.appendLine("  N° Factura : $numero")
        sb.appendLine("  Fecha      : $fechaHora")
        sb.appendLine("  Cliente    : $nombreCliente")
        sb.appendLine("  Correo     : $correoCliente")
        sb.appendLine("─".repeat(55))
        sb.appendLine(
            "  %-28s %6s %10s %10s"
                .format("Producto", "Cant.", "P.Unit.", "Subtotal")
        )
        sb.appendLine("─".repeat(55))

        carrito.obtenerItems().forEach { item ->
            sb.appendLine(
                "  %-28s %6d %10s %10s"
                    .format(
                        item.producto.nombre.take(28),
                        item.cantidad,
                        "\$%.2f".format(item.producto.precio),
                        "\$%.2f".format(item.subtotal)
                    )
            )
        }

        sb.appendLine("─".repeat(55))
        sb.appendLine("  %-44s %10s".format("Subtotal:", "\$%.2f".format(subtotal)))
        sb.appendLine("  %-44s %10s".format("IVA (13%):", "\$%.2f".format(iva)))
        sb.appendLine("═".repeat(55))
        sb.appendLine("  %-44s %10s".format("TOTAL A PAGAR:", "\$%.2f".format(total)))
        sb.appendLine("═".repeat(55))
        sb.appendLine("  ¡Gracias por tu compra en GameZone Store!")
        sb.appendLine("  Este documento es su comprobante de compra.")
        sb.appendLine("═".repeat(55))
        return sb.toString()
    }
}