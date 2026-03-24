package modelo

data class Producto(
    val id: Int,
    val nombre: String,
    val plataforma: String,
    val genero: String,
    val precio: Double,
    var stock: Int
) {

    init {
        require(id > 0) { "El ID del producto debe ser mayor a 0." }
        require(nombre.isNotBlank()) { "El nombre del producto no puede estar vacio." }
        require(plataforma.isNotBlank()) { "La plataforma no puede estar vacia." }
        require(precio > 0) { "El precio debe ser mayor a 0." }
        require(stock >= 0) { "El stock no puede ser negativo." }
    }

    fun hayStock(cantidad: Int): Boolean = stock >= cantidad

    fun reducirStock(cantidad: Int) {
        require(hayStock(cantidad)) {
            "Stock insuficiente para '$nombre'. Disponible: $stock, solicitado: $cantidad."
        }
        stock -= cantidad
    }

    fun mostrarEnLista(numero: Int) {
        println(
            "  %-4d %-35s %-10s %-12s \$%-8.2f %-5d"
                .format(numero, nombre, plataforma, genero, precio, stock)
        )
    }

    override fun toString(): String =
        "[$id] $nombre | $plataforma | $genero | \$${"%.2f".format(precio)} | Stock: $stock"
}