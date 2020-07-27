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

import java.net.*;
import java.io.*;

/**
 * @author Borislav S. Sabotinov
 * Standard UDP client, which will send a message and receive a reply.
 * May be used by Main to send a command instead, for server to execute.
 */
public class UDPClient implements ClientBehavior {
    public static void main(String...args) {
        // args give message contents and server hostname
        try (DatagramSocket aSocket = new DatagramSocket()) {
            ClientBehavior.sendMessage(aSocket, args);
            ClientBehavior.receiveReply(aSocket);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
} // end class UDPClient

