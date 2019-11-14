import java.net.*;

/*  DRS 14/nov/2019
 *  This is a server, receiving messages and answering with an echo.
 *  It creates a datagram socket type listening on UDP port 7 waiting for client data.
* 
*/

public class servidorudp {

	private static final int UDP_PORT = 7;

	public static void main(String argv[]) {
		DatagramSocket socket = null;
		boolean fin = false;
		String mensaje = "";

		System.out.println("Starting echo Server on port " + UDP_PORT + " ...");

		try {
			// Prepare socket on given port
			socket = new DatagramSocket(UDP_PORT);
			// Continue loop until we receive end
			do {

				System.out.println("Server waiting for request on port " + UDP_PORT + " ...");

				byte[] mensaje_bytes = new byte[256];
				// establish buffer size
				DatagramPacket paqueteRecibido = new DatagramPacket(mensaje_bytes, 256);
				// read and extract message from the socket
				socket.receive(paqueteRecibido);
				mensaje = new String(mensaje_bytes);

				// Build DatagramPacket for sending response
				DatagramPacket paqueteRespuesta = new DatagramPacket(paqueteRecibido.getData(),
						paqueteRecibido.getLength(), paqueteRecibido.getAddress(), paqueteRecibido.getPort());

				// Sending echo response
				System.out.println("Responding to " + mensaje);
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
