import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender {
    public static void main(String[] args) throws IOException {
        int port = 12345; // port number
        InetAddress group = InetAddress.getByName("224.0.0.1"); // IP address of multicast group
        String message = "Hello! Multicasting.";

        MulticastSocket socket = new MulticastSocket();
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), group, port);
        socket.send(packet);
        System.out.println("Message sent: " + message);
        socket.close();
    }
}