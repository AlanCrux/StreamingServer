package streamingserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanc
 */
public class StreamingServer {

    private static final int PUERTO = 1234;
    private static ServerSocket servidor;
    private static Socket cliente;
    private static String path;
    private static BufferedInputStream bufferEntrada;
    private static BufferedOutputStream bufferSalida;
    private static Scanner entrada;
    private static byte[] byteArray;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor corriendo ...");
        } catch (IOException ex) {
            Logger.getLogger(StreamingServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (true) {
            run();
        }
    }

    private static void run() {
        try {
            cliente = servidor.accept();
            entrada = new Scanner(cliente.getInputStream());
            
            path = entrada.nextLine();
            File file = new File(path);
            bufferEntrada = new BufferedInputStream(new FileInputStream(file));
            bufferSalida = new BufferedOutputStream(cliente.getOutputStream());
            byteArray = new byte[8192];
            int in;

            while ((in = bufferEntrada.read(byteArray)) != -1) {
                System.out.println("Enviando");
                bufferSalida.write(byteArray, 0, in);
            }
            bufferEntrada.close();
            bufferSalida.close();

            System.out.println("Se termino de enviar");
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(StreamingServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
