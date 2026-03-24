package modelo

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int
) {
    init {
        require(cantidad > 0) { "La cantidad debe ser mayor a 0." }
    }

    val subtotal: Double
        get() = producto.precio * cantidad

    fun agregarCantidad(extra: Int) {
        require(extra > 0) { "La cantidad a agregar debe ser mayor a 0." }
        val nuevaCantidad = cantidad + extra
        require(producto.hayStock(nuevaCantidad)) {
            "No hay suficiente stock. Disponible: ${producto.stock}, solicitado: $nuevaCantidad."
        }
        cantidad = nuevaCantidad
    }

    override fun toString(): String =
        "${producto.nombre} x$cantidad = \$${"%.2f".format(subtotal)}"
}