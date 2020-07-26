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
 * This interface specifies common behavior for clients.
 * They send messages and receive replies. The intent is to improve code readability and minimize duplication
 */
public interface ClientBehavior {
    int SERVER_PORT = 2587;

    static void receiveReply(DatagramSocket aSocket) throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
        aSocket.receive(reply);
        String stringReply = new String(reply.getData());
        System.out.println("Reply from " + reply.getAddress().getHostAddress() + ": " + stringReply);
    }

    static void sendMessage(DatagramSocket aSocket, String... args) throws IOException {
        byte[] m = args[0].getBytes();
        InetAddress aHost = InetAddress.getByName(args[1]);
        DatagramPacket request = new DatagramPacket(m, m.length, aHost, SERVER_PORT);
        aSocket.send(request);
    }
}
