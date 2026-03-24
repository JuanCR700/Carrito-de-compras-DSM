package modelo

class Inventario {

    private val productos: MutableMap<Int, Producto> = mutableMapOf()

    init {
        cargarProductosEjemplo()
    }

    private fun cargarProductosEjemplo() {
        val catalogo = listOf(
            Producto(1,  "God of War Ragnarok",              "PS5",    "Accion/Aventura", 59.99, 10),
            Producto(2,  "Spider-Man 2",                     "PS5",    "Accion",          54.99,  8),
            Producto(3,  "Zelda: Tears of the Kingdom",      "Switch", "Aventura/RPG",    59.99,  6),
            Producto(4,  "Mario Kart 8 Deluxe",              "Switch", "Carreras",        49.99, 12),
            Producto(5,  "Halo Infinite",                    "Xbox",   "FPS",             39.99,  7),
            Producto(6,  "Forza Horizon 5",                  "Xbox",   "Carreras",        44.99,  9),
            Producto(7,  "Elden Ring",                       "PC",     "RPG",             39.99,  5),
            Producto(8,  "Minecraft",                        "PC",     "Sandbox",         26.99, 99),
            Producto(9,  "FIFA 25",                          "PS5",    "Deportes",        49.99, 15),
            Producto(10, "Cyberpunk 2077: Phantom Liberty",  "PC",     "RPG/Accion",      29.99,  4)
        )
        catalogo.forEach { productos[it.id] = it }
    }

    fun obtenerTodos(): List<Producto> = productos.values.toList()

    fun buscarPorId(id: Int): Producto? = productos[id]

    fun obtenerDisponibles(): List<Producto> =
        productos.values.filter { it.stock > 0 }

    fun filtrarPorPlataforma(plataforma: String): List<Producto> =
        productos.values.filter {
            it.plataforma.equals(plataforma, ignoreCase = true)
        }

    // solo se actualiza el stock cuando se confirma la compra
    fun descontarStock(items: List<ItemCarrito>) {
        items.forEach { item ->
            val producto = buscarPorId(item.producto.id)
                ?: throw IllegalArgumentException("Producto ID ${item.producto.id} no encontrado en inventario.")
            require(producto.hayStock(item.cantidad)) {
                "Stock insuficiente para '${producto.nombre}'. Disponible: ${producto.stock}."
            }
        }
        items.forEach { item ->
            productos[item.producto.id]?.reducirStock(item.cantidad)
        }
    }

    fun agregarProducto(producto: Producto) {
        require(!productos.containsKey(producto.id)) {
            "Ya existe un producto con el ID ${producto.id}."
        }
        productos[producto.id] = producto
    }

    fun mostrarEncabezado() {
        println("\n  ${"=".repeat(82)}")
        println("  %-4s %-35s %-10s %-12s %-10s %-5s"
            .format("#", "Juego", "Plataforma", "Genero", "Precio", "Stock"))
        println("  ${"-".repeat(82)}")
    }

    override fun toString(): String {
        val sb = StringBuilder(" - Inventario GameZone Store - \n")
        productos.values.forEach { sb.appendLine("  $it") }
        return sb.toString()
    }
}