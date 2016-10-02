import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Created by Vladyslav Voloshyn
 * on 29.08.2016.
 */

public class UDPServer {

    private UDPServer() throws IOException {
        System.out.println("UDP Server Started");
        Date date;
        double message;

        try (DatagramSocket serverSocket = new DatagramSocket(9003)) {
            boolean flag = true;

            while (flag) {

                byte[] sendMessage = new byte[10];
                byte[] receiveMessage = new byte[10];
                DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);
                serverSocket.receive(receivePacket);
                date = new Date();

                message = ByteBuffer.wrap(receiveMessage).getDouble();
                System.out.println("Received from client: [" + message + "]\nFrom: " + receivePacket.getAddress()
                + "\n" + date.toString());

                InetAddress inetAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                ByteBuffer.wrap(sendMessage).putDouble(message);

                DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length, inetAddress, port);
                serverSocket.send(sendPacket);

            }

         serverSocket.close();

        } catch (IOException e) {
            System.out.println("UDP Server Terminating");
            System.exit(0);
        }
    }

    public static void main(String[] args) throws IOException {
        new UDPServer();
    }
}
