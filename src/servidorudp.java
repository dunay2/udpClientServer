import java.net.*;


public class servidorudp {
	
	private static final int UDP_PORT=7;
	
    public static void main(String argv[]) {
        DatagramSocket socket = null;
        boolean fin = false;
        String mensaje = "";

        
         /* Creamos un socket de tipo datagrama que escucha en el puerto
         * UDP 7 y espera paquetes de los clientes.
         * Este programa recibe lo que viene del socket y lo muestra
         * por pantalla.
         */
               
        System.out.println("Starting echo Server on port "+ UDP_PORT + " ...");
           
        try {
        	//preparamos el socket en el puerto indicado
            socket = new DatagramSocket(UDP_PORT);
            //Continuamos en el bucle hasta que nos llegue "end"
            do {
            
            	System.out.println("Server waiting for request on port "+ UDP_PORT + " ...");
            	
                byte[] mensaje_bytes = new byte[256];
                //establecemos el tamaño del buffer 
                DatagramPacket paqueteRecibido = new DatagramPacket(mensaje_bytes, 256);
                //leemos en el socket y extraemos el mensaje
                socket.receive(paqueteRecibido);
                mensaje = new String(mensaje_bytes);
                
               
                // Construimos el DatagramPacket para enviar la respuesta
                DatagramPacket paqueteRespuesta =
                  new DatagramPacket(paqueteRecibido.getData(), paqueteRecibido.getLength(),
                		  paqueteRecibido.getAddress(), paqueteRecibido.getPort());

                // Enviamos la respuesta, que es un eco
                System.out.println("Responding to "+ mensaje);
                socket.send(paqueteRespuesta);
                            
                if (mensaje.startsWith("end")) {
                    fin = true;
                }
            } while (!fin);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } finally {
            if (socket != null) {
                System.out.println("Closing server socket ...");
                socket.close();
            }
        }
    }
}


