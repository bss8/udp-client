/*
 * Copyright (c) 2020. Borislav S. Sabotinov
 * https://github.com/bss8
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.txstate.bss64;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * @author Borislav S. Sabotinov
 * This client will prompt the user one time for a host, then continuously prompt
 * for a new message. The message will be sent to the server, which must be running.
 * First parameter args[0] passed from the CLI is the server hostname or IP address.
 */
public class UDPClientIterative implements ClientBehavior {
    public static void main(String...args) {
        try (DatagramSocket aSocket = new DatagramSocket()) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\nEnter message: ");
                String usrMsg = scanner.nextLine();
                ClientBehavior.sendMessage(aSocket, usrMsg, args[0]);
                ClientBehavior.receiveReply(aSocket);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
} // end class UDPClientIterative
