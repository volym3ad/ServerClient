import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * Created by Vladyslav Voloshyn
 * on 29.08.2016.
 */
public class UDPClient {

    private UDPClient() throws IOException {
        System.out.println("UDP Client Started");
        double messageFromServer, messageToServer;
        byte[] sendMessage, receiveMessage;

        try (DatagramSocket clientSocket = new DatagramSocket()) {

            boolean flag = true;

            while (flag) {

                InetAddress inetAddress = InetAddress.getByName("localhost");
                sendMessage = new byte[10];
                System.out.println("Message");
                messageToServer = 12.133;

                ByteBuffer.wrap(sendMessage).putDouble(messageToServer);
                DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length, inetAddress, 9003);
                clientSocket.send(sendPacket);

                receiveMessage = new byte[10];
                DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);
                clientSocket.receive(receivePacket);
                messageFromServer = ByteBuffer.wrap(receiveMessage).getDouble();
                System.out.println("Received from server [" + messageFromServer + "]\nFrom " + receivePacket.getSocketAddress());

                System.out.print("Do you want to perform operation again? ");
                Scanner scanner = new Scanner(System.in);

                if (scanner.nextLine().equals("yes")) {
                    System.out.println("OK");
                } else {
                    flag = false;
                    clientSocket.close();
                }
            }

        } catch (IOException e) {
            // handle exception
            System.out.println("IOException");
            System.exit(0);
        }

        System.out.println("UDP Client Terminating");
    }

    public static void main(String[] args) throws IOException {
        new UDPClient();
    }
}
