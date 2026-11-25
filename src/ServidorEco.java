import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServidorEco {
    static void main() {
        final int puerto = 5000;

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            IO.println("Servidor eco escuchando en puerto %s".formatted(puerto));

            try (Socket socket = serverSocket.accept();
                 BufferedReader bfLector   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter    pwEscritor = new PrintWriter(socket.getOutputStream(), true)
            ) {
                IO.println("Cliente conectado: %s".formatted(socket.getInetAddress().getHostAddress()));
                String mensajeCliente;
                while ((mensajeCliente = bfLector.readLine()) != null) {
                    IO.println("Recibido: %s".formatted(mensajeCliente));
                    String mensajeMayusculas = mensajeCliente.toUpperCase();
                    pwEscritor.println(mensajeMayusculas);
                }
                IO.println("Cliente solicita cierre.");
            }
            IO.println("Esperando nuevo cliente...");
        } catch (IOException exception) {
                IO.println("Error de conexión: " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            IO.println("Puerto fuera de rango: %s".formatted(exception.getMessage()));
        }
    }
}