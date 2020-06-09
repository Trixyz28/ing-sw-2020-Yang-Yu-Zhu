package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.client.Client;

import java.util.Scanner;

public class CliLauncher {

    private Scanner scanner;
    private Client client;

    private String ip;
    private String port;


    public void start() throws Exception {
        scanner = new Scanner(System.in);

        System.out.println("Server IP: ");
        ip = scanner.nextLine();
        System.out.println("Server port: ");
        port = scanner.nextLine();

        client = new Client();
        client.startClient("cli",ip,port);
    }

}
