package edu.txstate.bss64;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    private static String IP_ADDR;
    private static String OS;

    static {
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            IP_ADDR = socket.getLocalAddress().getHostAddress();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }

        OS = System.getProperty("os.name");
    }

    public static void main(String... args) throws Exception {
        if (args.length != 1) {
            printHelp();
        } else {
            final int OPT_CODE = Integer.parseInt(args[0]);
            if (OPT_CODE < 1 || OPT_CODE > 3) {
                printHelp();
            } else {
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
        }
    }

    private static void optionOne() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter message: ");
        String usrMsg = scanner.nextLine();
        System.out.print("\nEnter server IP or hostname (FQDN): ");
        String host = scanner.nextLine();
        UDPClient.main(usrMsg, host);
    }

    private static void optionTwo() {
        UDPClientIterative.main();
    }

    private static void optionThree() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter command with arguments: ");
        String cmd = scanner.nextLine();
        System.out.print("\nEnter server IP or hostname (FQDN): ");
        String host = scanner.nextLine();
        String[] decomposedCommand =  cmd.split("\\s");

        boolean isValidCmd;
        if (OS.contains("Windows")) {
            isValidCmd = validateWindowsCmd(decomposedCommand[0]);
        } else if (OS.contains("Linux")) {
            isValidCmd = validateLinuxCmd(decomposedCommand[0]);
        } else {
            throw new UnsupportedOperationException("OS type is not supported!");
        }

        if (isValidCmd) {
            UDPClient.main(cmd, host);
        } else {
            throw new IllegalArgumentException("Command entered is not valid!");
        }
    }

    /**
     * @param cmd
     * @return
     */
    private static boolean validateLinuxCmd(String cmd) {
        return readCmdFile(cmd, "resources/linux_commands.txt");
    }

    private static boolean validateWindowsCmd(String cmd) {
        return readCmdFile(cmd, "resources/windows_commands.txt");
    }

    /**
     * try-with-resources block makes use of superclass Reader's implementation of the Closeable interface
     * FileReader extends InputStreamReader, which in turn extends Reader
     * BufferedReader extends Reader directly
     *
     * @param cmd
     * @param allowedCmdFile
     * @return
     */
    private static boolean readCmdFile(String cmd, String allowedCmdFile) {
        boolean isValidCmd = false;
        try (FileReader fileReader = new FileReader(allowedCmdFile); // Throws FileNotFoundException
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {  // throws IOException, which is higher than FileNotFound
                if (line.equals(cmd)) {
                    isValidCmd = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return isValidCmd;
    }

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
}
