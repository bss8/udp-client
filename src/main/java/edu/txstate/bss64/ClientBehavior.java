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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author Borislav S. Sabotinov
 * This interface specifies common behavior for clients.
 * They send messages and receive replies. The intent is to improve code readability and minimize duplication
 */
public interface ClientBehavior {
    int SERVER_PORT = 2587;

    /**
     * Defines a standard method for how all clients should receive a reply from a UDP server.
     * @param aSocket DatagramSocket created by caller (Main)
     * @throws IOException
     */
    static void receiveReply(DatagramSocket aSocket) throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
        aSocket.receive(reply);
        String stringReply = new String(reply.getData());
        System.out.println("Reply from " + reply.getAddress().getHostAddress() + ": " + stringReply);
    }

    /**
     * Defines a standard method for how all clients should send a message to a UDP server.
     * @param aSocket DatagramSocket created by caller (Main)
     * @param args args[0] is the user message, args[1] is the hostname or IP address.
     * @throws IOException
     */
    static void sendMessage(DatagramSocket aSocket, String... args) throws IOException {
        byte[] message = args[0].getBytes();
        InetAddress aHost = InetAddress.getByName(args[1]);
        DatagramPacket request = new DatagramPacket(message, message.length, aHost, SERVER_PORT);
        aSocket.send(request);
    }
} // end interface ClientBehavior
