package streamingserver;

import java.net.*;
import java.io.*;

public class TransformacionCliente {

    static DataInputStream input;
    static BufferedInputStream bufferEntrada;
    static BufferedOutputStream bufferSalida;
    static int in;
    static byte[] byteArray;
    static String directorioCancion;
    static String rutaAGuardar;

    public static void main(String[] args) {
        //Fichero a transferir
        directorioCancion = "/home/esmeralda/TequilaMusic/Repositorio/Los_Angeles_Azules_De_Plaza_en_Plaza/La_Cumbia_del_Infinito.mp3";
        rutaAGuardar = "Los_Angeles_Azules/De_Plaza_en_Plaza/La_Cumbia_del_Infinito";
        try {
            final File archivoEnviar = new File(directorioCancion);
            Socket cliente = new Socket("localhost", 1234);
            bufferEntrada = new BufferedInputStream(new FileInputStream(archivoEnviar));
            bufferSalida = new BufferedOutputStream(cliente.getOutputStream());
            PrintWriter out = new PrintWriter(cliente.getOutputStream(),true);

            //Enviamos el nombre del fichero
            DataOutputStream dataOutput = new DataOutputStream(cliente.getOutputStream());
            
            out.println(rutaAGuardar);
            System.out.println(rutaAGuardar);
            //Enviamos el fichero
            byteArray = new byte[8192];
            while ((in = bufferEntrada.read(byteArray)) != -1) {
                System.out.println("Enviando cancion...");
                bufferSalida.write(byteArray, 0, in);
            }

            bufferEntrada.close();
            bufferSalida.close();
            System.out.println("Se envio la cancion");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
