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

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Capture and restore standard output for testing purposes:
 * https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientBehaviorTest {
    DatagramSocket aSocket;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeAll
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @BeforeEach
    void setUp() {
        try {
            aSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        aSocket.close();
    }

    @Test
    void receiveReply() {
        sendMessage();
        try {
            ClientBehavior.receiveReply(aSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals("Reply from 192.168.1.126: Hello", outContent.toString().trim());
    }

    @Test
    void sendMessage() {
        try {
            ClientBehavior.sendMessage(aSocket, "Hello", "192.168.1.126");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}