
import java.net.*;
import java.io.*;

/*  DRS 14/nov/2019
 *  This is a client, it sends and receive messages from the server
*/

public class clienteudp {

	private static final int UDP_PORT = 7;

	public static void main(String argv[]) {

		System.out.println("Starting echo client on port " + UDP_PORT + " ...");
		System.out.println("Type some text to receive server echo or \"end\" to quit");

		/*
		 * Get arguments to connect to server
		 * 
		 */
		if (argv.length == 0) {
			System.err.println("Introduce your upd server address when calling this program");
			System.exit(1);
		}

		// Preparing buffer for keyboard entry
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket socket = null;
		InetAddress address;
		byte[] mensaje_bytes = new byte[256];
		String mensaje = "";
		/*
		 * Defining 2 DatagramPacket, one for sending and another for receiving
		 */
		DatagramPacket paqueteEnviado;
		DatagramPacket paqueteRespuesta;

		mensaje_bytes = mensaje.getBytes();

		try {
			// socket instance
			socket = new DatagramSocket();

			// resolve server IP
			address = InetAddress.getByName(argv[0]);

			/*
			 * Create packets to send to UDP_PORT, Here we send keyboard user's entries It
			 * finish with the "end" word.
			 */
			do {
				// The system is waiting for keyboard entries
				mensaje = in.readLine();
				mensaje_bytes = mensaje.getBytes();
				// Establishing value for DatagramPacket attributes, message, message size,
				// server and destination port
				paqueteEnviado = new DatagramPacket(mensaje_bytes, mensaje.length(), address, UDP_PORT);
				// DatagramPacket sent by socket
				socket.send(paqueteEnviado);

				// Build another DatagramPacket for the response
				// DatagramPacket
				paqueteRespuesta = new DatagramPacket(mensaje_bytes, mensaje.length());

				paqueteEnviado = new DatagramPacket(mensaje_bytes, mensaje.length(), address, UDP_PORT);

				socket.receive(paqueteRespuesta);

				// Send server response to standard output
				System.out.println("Response: " + new String(paqueteRespuesta.getData()));

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
