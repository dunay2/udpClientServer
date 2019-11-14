

import java.net.*;
import java.io.*;

public class clienteudp {
		
  
private static final int UDP_PORT=7; 
    public static void main(String argv[]) {

    	System.out.println("Starting echo client on port "+ UDP_PORT + " ...");
    	System.out.println("Type some text to receive server echo or \"end\" to quit");
    	    	
        /*
         * Leemos los argumentos del programa para saber a qué
         * servidor hay que conectarse
         */
        if (argv.length == 0) {
            System.err.println("Introduce your upd server");
            System.exit(1);
        }

        //Preparamos un buffer de entrada de teclado
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket socket = null;
        InetAddress address;
        byte[] mensaje_bytes = new byte[256];
        String mensaje = "";
        /*
         * Definimos dos DatagramPacket uno para enviar y otro para recibir
         */
        DatagramPacket paqueteEnviado;
        DatagramPacket paqueteRespuesta;

        mensaje_bytes = mensaje.getBytes();

        try {
        	//instanciamos el socket
            socket = new DatagramSocket();
            
            //resolvemos la IP del servidor
			address = InetAddress.getByName(argv[0]);
	        
            /*
             * Creamos paquetes que enviamos al puerto UDP_PORT, 
             * enviando lo que el usuario escribe por teclado. 
			 * Acabamos con la palabra end.
             */
            do {
            	//el sistema espera una entrada de teclado
                mensaje = in.readLine();
                mensaje_bytes = mensaje.getBytes();
                //Establecemos el valor de los atributos de DatagramPacket, mensaje, tamaño de mensaje, dirección servidor y puerto destino
                paqueteEnviado = new DatagramPacket(mensaje_bytes, mensaje.length(), address, UDP_PORT);
                //enviamos el DatagramPacket por el socket 
                socket.send(paqueteEnviado);          
     
                
                // Construimos otro DatagramPacket para recibir la respuesta
                //DatagramPacket 
                paqueteRespuesta =
                  new DatagramPacket(mensaje_bytes, mensaje.length());
                
                paqueteEnviado = new DatagramPacket(mensaje_bytes, mensaje.length(), address, UDP_PORT);
                
                socket.receive(paqueteRespuesta);

                // Enviamos la respuesta del servidor a la salida estandar
                System.out.println("Respuesta: " + new String(paqueteRespuesta.getData()));

                
                
            } while (!mensaje.startsWith("end"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } finally {
            if (socket != null) {
                System.out.println("Closing client socket ...");
                socket.close();
            }
        }
    }
}
