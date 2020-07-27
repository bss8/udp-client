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

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Borislav S. Sabotinov
 * Entry point providing users a CLI for creating a UDP client.
 * Options: 1) standard one-off message;
 *          2) iteratively prompt for message until killed
 *          3) send a command for server to execute
 */
public class Main {
    private static String IP_ADDR;
    private static String OS;
    private static String HOST;

    // static init block to get the IP address and OS name of the machine on which client will run
    static {
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            IP_ADDR = socket.getLocalAddress().getHostAddress();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }

        OS = System.getProperty("os.name");
    }

    /**
     * UDP is akin to sending a message in a bottle. We know we sent the message out.
     * We are not guaranteed that it will be received.
     * https://stackoverflow.com/questions/5830810/determine-if-server-is-listening-when-using-udp
     * We print instructions to the screen and prompt the user to provide us the hostname or IP of
     * the server that should receive their message. Then, the user provides an option code (1-3), regarding
     * which type of client they want to initiate.
     * @param args
     * @throws Exception
     */
    public static void main(String... args) throws Exception {
        System.out.println("Starting client on " + IP_ADDR +" running on " + OS);
        printHelp();
        HOST = getHost();

        String usrChoice = getUserOptionChoice();
        int OPT_CODE = Integer.parseInt(usrChoice);
        routeChoice(OPT_CODE);
    }

    /**
     * Helper function to route the user selected option code to the right function.
     * @param OPT_CODE code obtained from the user.
     * @throws Exception
     */
    private static void routeChoice(int OPT_CODE) throws Exception {
        switch (OPT_CODE) {
            case 1:
                System.out.println("OPT-1 selected.....");
                optionOne();
                break;
            case 2:
                System.out.println("OPT-2 selected on " + OS + "......");
                optionTwo();
                break;
            case 3:
                System.out.println("OPT-3 selected.....");
                optionThree();
                break;
            default:
                throw new Exception("Invalid option code!");
        }
    }

    /**
     * Prompts the user for an option code.
     * @return option code as selected by the user
     */
    private static String getUserOptionChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nSelect option (1, 2, or 3): ");
        return scanner.next();
    }

    /**
     * Prompts the user for the server IP or fully qualified domain name (FQDN).
     * @return host name or IP address
     */
    private static String getHost() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter server IP or hostname (FQDN): ");
        return scanner.nextLine();
    }

    /**
     * One-off message mode. Will prompt the user once for a message and send it to the server, terminating after
     * a reply is received.
     */
    private static void optionOne() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter message: ");
        String usrMsg = scanner.nextLine();

        UDPClient.main(usrMsg, HOST);
    }

    /**
     * Iterative prompt mode - the client will keep asking the user to input a message. It will keep prompting
     * for a message until the process is killed.
     */
    private static void optionTwo() {
        UDPClientIterative.main(HOST);
    }

    /**
     * Command execution option. Message sent to the UDP server should be an executable command.
     * If the server is running on a Linux environment and a Windows command is sent (or vice versa),
     * the server will not process the command.
     */
    private static void optionThree() {
        UDPClientIterative.main(HOST);
    }



    /**
     * Prints instructions for the user, explaining how to use this program.
     */
    private static void printHelp() {
        System.out.print(
                "Select one of the options below: \n" +
                        "OPTION 1: Unmodified UPDClient - accepts one message, server will reply, program terminates.\n" +
                        "\t[String w/ whitespace]: java -jar 01_client.jar \"Hello World!\" <hostname> \n" +
                        "\t[Single string]: java -jar Hello <hostname>\n\n" +

                        "OPTION 2: Interactive UDPClient - continues to prompt for a message, server will return it as-is.\n" +
                        "\tjava -jar 01_client.jar <hostname>\n" +
                        "\tThen enter a message, with or without spaces. No quotes required.\n" +
                        "\tCTRL + C to terminate the program.\n\n" +

                        "OPTION 3: Command Execution - prompts for a valid Linux or Windows cmd (depending on OS).\n" +
                        "Will attempt to execute and return any available output.\n" +
                        "\tjava -jar 01_client.jar command <args1> ...<argN> <hostname>\n\n" +
                        "Note: last argument must always be the hostname of the server."
        );
    }
} // end class Main
