/**
 *Para esta parte del envio de factura a los correos del "cliente"
 * utilizamos el protocolo SMTP de Gmail
 * se configuro la cuenta de uno de nosotros como servidor remitente
 * usando el host smtp.gmail.com en el puerto 587 con cifrado TLS
 * y como Gmail no permite usar contraseña normal desde codigo externo
 * por seguridad de Google, que es una clave de 16 caracteres que autoriza
 * nuestra aplicacion a enviar correos en nombre de esa cuenta sin exponer la contraseña real
 **/
package facturacion

import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.MessagingException
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.util.Properties

object ServicioCorreo {

    private const val EMAIL_REMITENTE = "hdzkatya2629@gmail.com"
    private const val APP_PASSWORD     = "wgig smqm otlj huin"

    private const val SMTP_HOST = "smtp.gmail.com"
    private const val SMTP_PORT = "587"

    fun enviarFactura(factura: Factura): Boolean {
        return try {
            val props = Properties().apply {
                put("mail.smtp.auth", "true")
                put("mail.smtp.starttls.enable", "true")
                put("mail.smtp.host", SMTP_HOST)
                put("mail.smtp.port", SMTP_PORT)
                put("mail.smtp.ssl.trust", SMTP_HOST)
            }

            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication() =
                    PasswordAuthentication(EMAIL_REMITENTE, APP_PASSWORD)
            })

            val mensaje = MimeMessage(session).apply {
                setFrom(InternetAddress(EMAIL_REMITENTE, "GameZone Store"))
                addRecipient(Message.RecipientType.TO, InternetAddress(factura.correoCliente))
                subject = "Tu factura GameZone Store — N° ${factura.numero}"
                setText(factura.generarTexto(), "UTF-8")
            }

            Transport.send(mensaje)
            true

        } catch (e: MessagingException) {
            println("  [ERROR CORREO] No se pudo enviar el correo: ${e.message}")
            false
        } catch (e: Exception) {
            println("  [ERROR CORREO] Error inesperado: ${e.message}")
            false
        }
    }
}