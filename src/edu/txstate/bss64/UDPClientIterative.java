package edu.txstate.bss64;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 * @author Borislav S. Sabotinov
 * This client will prompt the user one time for a host, then
 *  continuously prompt for a new message. The message will be sent to the server, which must be running.
 */
public class UDPClientIterative {
    public static void main(String...args) {
        try (DatagramSocket aSocket = new DatagramSocket()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nEnter host IP / name (one time request): ");
            String host = scanner.next();

            while (true) {
                System.out.print("\nEnter message: ");
                String usrMsg = scanner.nextLine();

                byte[] m = usrMsg.getBytes();
                InetAddress aHost = InetAddress.getByName(host);
                int serverPort = 6789;
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
                aSocket.send(request);
                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);
                System.out.println("Reply from " + reply.getAddress().getHostAddress() + ": " + new String(reply.getData()));
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}

