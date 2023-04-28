import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver {
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
        int port = 12345; // port number
        InetAddress group = InetAddress.getByName("224.0.0.1"); // IP address of multicast group

        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);

        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        String message = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Received message: " + message);
        System.out.println("Multicast group IP address: " + group.getHostAddress());
        System.out.println("Port number: " + port);
        System.out.println("Length of message: " + message.length());

        socket.leaveGroup(group);
        socket.close();
    }
}