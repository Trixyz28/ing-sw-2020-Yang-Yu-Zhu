package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.view.Sender;

import java.net.Socket;
import java.util.Scanner;

public class CliLauncher {

    private Scanner scanner;
    private Client client;

    private String ip;
    private String port;
    private Sender sender;


    public void start() {
        this.scanner = new Scanner(System.in);
        boolean set = false;

        printLogo();

        while (!set) {
            System.out.print("Server IP: ");
            String ip = scanner.nextLine();
            System.out.print("Server port: ");
            port = scanner.nextLine();

            try {
                Socket socket = new Socket(ip, Integer.parseInt(port));
                set = true;

                this.client = new Client();
                client.setupClient("cli", socket);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        this.sender = new Sender();
        sender.addObservers(client);

        Thread read = new Thread(() -> {

            try {
                while(client.isActive()) {
                    String input = scanner.nextLine();
                    sender.sendInput(input);
                }

            } catch(Exception e) {
                System.out.println("Writing thread stopped");
                client.setActive(false);
            }
        });
        read.start();

        client.run();
        read.interrupt();
    }

    private void printLogo() {
        System.out.println(CliPrinter.LOGO_COLOR + "      ____              _             _       _ ");
        System.out.println(CliPrinter.LOGO_COLOR + "     / ___|  __ _ _ __ | |_ ___  _ __(_)_ __ (_)");
        System.out.println(CliPrinter.LOGO_COLOR + "     \\___ \\ / _` | '_ \\| __/ _ \\| '__| | '_ \\| |");
        System.out.println(CliPrinter.LOGO_COLOR + "      ___) | (_| | | | | || (_) | |  | | | | | |");
        System.out.println(CliPrinter.LOGO_COLOR + "     |____/ \\__,_|_| |_|\\__\\___/|_|  |_|_| |_|_|");
        System.out.println(CliPrinter.INDEX_COLOR + "  =================================================");
        System.out.println(CliPrinter.INDEX_COLOR + "     =========================================== ");
        System.out.println(CliPrinter.RESET);
    }

}
