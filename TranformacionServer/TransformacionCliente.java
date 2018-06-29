import java.net.*;
import java.io.*;
 
class cliente{
	DataInputStream input;
	BufferedInputStream bufferEntrada;
	BufferedOutputStream bufferSalida;
	int in;
	byte[] byteArray;
	final String cancion
	
	public static void main (String[] args){

		 
	}

	private static void run() {
	 	//Fichero a transferir
		 cancion = "/home/esmeralda/TequilaMusic/Repositorio/Los_Angeles_Azules_De_Plaza_en_Plaza/La_Cumbia_del_Infinito.mp3"f";
		 
		try{
			final File archivoEnviar = new File( cancion );
			Socket cliente = new Socket("localhost", 1234);
			bufferEntrada = new BufferedInputStream(new FileInputStream(archivoEnviar));
			bufferSalida = new BufferedOutputStream(cliente.getOutputStream());

			bufferSalida.write();
			//Enviamos el nombre del fichero
			DataOutputStream dataOutput=new DataOutputStream(cliente.getOutputStream());
			dataOutput.writeUTF(archivoEnviar.getName());
			//Enviamos el fichero
			byteArray = new byte[8192];
			while ((in = bufferEntrada.read(byteArray)) != -1){
			bufferSalida.write(byteArray,0,in); 
		}
		 
		bufferEntrada.close();
		bufferSalida.close();
		 
		}catch ( Exception e ) {
		 System.err.println(e);
	 	}
	 }
}