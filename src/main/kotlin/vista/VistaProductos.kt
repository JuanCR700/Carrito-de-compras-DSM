package vista

import modelo.Inventario
import modelo.Producto
import vista.ConsolaUI.BOLD
import vista.ConsolaUI.CYAN
import vista.ConsolaUI.GREEN
import vista.ConsolaUI.RESET
import vista.ConsolaUI.WHITE
import vista.ConsolaUI.YELLOW

object VistaProductos {

    fun mostrarCatalogo(inventario: Inventario) {
        ConsolaUI.subtitulo("Catalogo de Videojuegos")

        val productos = inventario.obtenerDisponibles()

        if (productos.isEmpty()) {
            ConsolaUI.advertencia("No hay productos disponibles en este momento.")
            return
        }

        println(
            "$BOLD$CYAN  %-4s %-35s %-10s %-14s %-10s %s$RESET"
                .format("#", "Juego", "Plataforma", "Genero", "Precio", "Stock")
        )
        ConsolaUI.lineaSimple()

        productos.forEachIndexed { index, producto ->
            val stockColor = if (producto.stock <= 3) ConsolaUI.RED else GREEN
            println(
                "  $CYAN%-4d$RESET %-35s $YELLOW%-10s$RESET %-14s $GREEN\$%-9.2f$RESET $stockColor%-5d$RESET"
                    .format(
                        index + 1,
                        producto.nombre,
                        producto.plataforma,
                        producto.genero,
                        producto.precio,
                        producto.stock
                    )
            )
        }

        ConsolaUI.lineaSimple()
        println("$WHITE  * Stock en rojo = ultimas unidades$RESET\n")
    }

    fun seleccionarProducto(inventario: Inventario): Producto? {
        val productos = inventario.obtenerDisponibles()

        val numero = ConsolaUI.leerEnteroONull("Ingresa el numero del producto (0 para cancelar):")

        if (numero == null) {
            ConsolaUI.error("Entrada invalida. Debes ingresar un numero.")
            return null
        }
        if (numero == 0) return null
        if (numero < 1 || numero > productos.size) {
            ConsolaUI.error("Numero fuera de rango. Elige entre 1 y ${productos.size}.")
            return null
        }

        return productos[numero - 1]
    }

    fun pedirCantidad(producto: Producto): Int? {
        println("\n$BOLD  Producto: ${producto.nombre}$RESET")
        println("  Precio  : $GREEN\$${"%.2f".format(producto.precio)}$RESET")
        println("  Stock   : ${producto.stock} unidades disponibles")

        val cantidad = ConsolaUI.leerEnteroONull("¿Cuantas unidades deseas agregar?")

        if (cantidad == null || cantidad <= 0) {
            ConsolaUI.error("Cantidad invalida. Debe ser un numero mayor a 0.")
            return null
        }
        if (cantidad > producto.stock) {
            ConsolaUI.error("No hay suficiente stock. Maximo disponible: ${producto.stock}.")
            return null
        }

        return cantidad
    }
}