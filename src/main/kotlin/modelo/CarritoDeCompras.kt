package modelo

class CarritoDeCompras {

    private val items: MutableList<ItemCarrito> = mutableListOf()

    fun obtenerItems(): List<ItemCarrito> = items.toList()

    fun estaVacio(): Boolean = items.isEmpty()

    fun cantidadDeItems(): Int = items.size

    fun agregarProducto(producto: Producto, cantidad: Int) {
        require(cantidad > 0) { "La cantidad debe ser mayor a 0." }

        val itemExistente = items.find { it.producto.id == producto.id }

        if (itemExistente != null) {
            itemExistente.agregarCantidad(cantidad)
        } else {
            require(producto.hayStock(cantidad)) {
                "Stock insuficiente para '${producto.nombre}'. Disponible: ${producto.stock}."
            }
            items.add(ItemCarrito(producto, cantidad))
        }
    }

    fun eliminarProducto(productoId: Int): Boolean {
        val item = items.find { it.producto.id == productoId }
        return if (item != null) {
            items.remove(item)
            true
        } else {
            false
        }
    }

    fun vaciar() = items.clear()

    fun calcularSubtotal(): Double = items.sumOf { it.subtotal }

    fun calcularIVA(): Double = calcularSubtotal() * 0.13

    fun calcularTotal(): Double = calcularSubtotal() + calcularIVA()

    fun buscarItem(productoId: Int): ItemCarrito? =
        items.find { it.producto.id == productoId }

    override fun toString(): String {
        if (estaVacio()) return "El carrito esta vacio."
        val sb = StringBuilder()
        items.forEachIndexed { i, item ->
            sb.appendLine("  ${i + 1}. $item")
        }
        sb.appendLine("  Subtotal : \$${"%.2f".format(calcularSubtotal())}")
        sb.appendLine("  IVA (13%): \$${"%.2f".format(calcularIVA())}")
        sb.appendLine("  TOTAL    : \$${"%.2f".format(calcularTotal())}")
        return sb.toString()
    }
}