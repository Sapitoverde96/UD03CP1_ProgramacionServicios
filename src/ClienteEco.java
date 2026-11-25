import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ClienteEco {
    static void main() {
        final String direccionIP = "localhost";
        final int puerto = 5000;

        try (Socket socket = new Socket(direccionIP, puerto);
             BufferedReader bfLector   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter    pwEscritor = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader teclado    = new BufferedReader(new InputStreamReader(System.in))
        ) {
            IO.println("Conectado al servidor eco. Escribe mensajes (FIN para salir):");
            String mensajeCliente;
            while ((mensajeCliente = teclado.readLine()) != null) {
                if (mensajeCliente.equals("FIN")) {
                    break;
                }
                pwEscritor.println(mensajeCliente);
                IO.println("Servidor: %s".formatted(bfLector.readLine()));
            }
            IO.println("Cliente finalizado.");
        } catch (UnknownHostException exception) {
            IO.println("Host desconocido: %s".formatted(exception.getMessage()));
        } catch (IOException exception) {
            IO.println("Error de conexión: %s".formatted(exception.getMessage()));
        } catch (IllegalArgumentException exception) {
            IO.println("Puerto fuera de rango: %s".formatted(exception.getMessage()));
        }
    }
}