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

    private boolean set;
    private String playAgain;


    public void start() {

        do {
            this.scanner = new Scanner(System.in);
            set = false;

            printLogo();

            while (!set) {
                setIPAndPort();
            }

            this.sender = new Sender();
            sender.addObservers(client);

            Thread read = new Thread(() -> {
                while(client.isActive()) {
                    String input = scanner.nextLine();
                    sender.sendInput(input);
                }
            });
            read.start();

            client.run();
            read.interrupt();
            finalRequest();

        } while (playAgain.equalsIgnoreCase("yes"));
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


    private void finalRequest() {
        scanner = new Scanner(System.in);
        System.out.println("Press ENTER first to close the match");
        System.out.println("Do you want to play again? (YES/NO)");
        playAgain = scanner.nextLine();

        if(playAgain.equalsIgnoreCase("yes")) {
            System.out.println("\n" + "Previous IP Address: " + ip);
            System.out.println("Previous Port Number: " + port + "\n");
        }
    }


    private void setIPAndPort() {
        System.out.print("Server IP: ");
        ip = scanner.nextLine();
        System.out.print("Server port: ");
        port = scanner.nextLine();
        System.out.println("Connecting...");

        try {
            Socket socket = new Socket(ip, Integer.parseInt(port));
            set = true;
            this.client = new Client();
            client.setupClient("cli", socket);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
