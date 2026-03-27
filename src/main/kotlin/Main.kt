/**
 * Integrantes:
 *              Katya Maria Hernandez Perez -  HP221350
 *              David Roberto Ferrer Coto - FC243112
 *              Melvin Eduardo Robles Rodas - RR191220
 *              Juan Carlos Ramirez Chavez - RC231487
 * Para esta tarea se dividió el trabajo de la siguiente manera:
 *   - Integrante 1: modelo (Producto, Inventario, CarritoDeCompras)
 *   - Integrante 2: vista (MenuPrincipal, VistaProductos, VistaCarrito)
 *   - Integrante 3: facturacion (Factura, ServicioCorreo, VistaFactura)
 *   - Integrante 4: util (Logger, ManejadorExcepciones) + este Main
 */
import modelo.CarritoDeCompras
import modelo.Inventario
import util.Logger
import util.ManejadorExcepciones
import vista.MenuPrincipal

fun main() {
    ManejadorExcepciones.registrar()
    Logger.info("Aplicacion iniciada")

    try {
        Logger.info("Cargando inventario...")
        val inventario = Inventario()
        Logger.info("Inventario cargado: ${inventario.obtenerTodos().size} productos")

        val carrito = CarritoDeCompras()
        Logger.info("Carrito inicializado")

        val menu = MenuPrincipal(inventario, carrito)
        Logger.info("Iniciando interfaz de usuario")
        menu.iniciar()

    } catch (e: Exception) {
        Logger.error("Error critico al iniciar la aplicacion", e)
        println("\n  Error al iniciar GameZone Store.")
        println("  Revisa el archivo gamezone_log.txt para mas detalles.\n")
    } finally {
        Logger.info("Aplicacion cerrada por el usuario")
        Logger.cerrar()
    }
}