package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.client.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.IllegalFormatException;
import java.util.Scanner;

public class CliLauncher {

    private Scanner scanner;
    private Client client;
    private boolean set;

    private String ip;
    private String port;


    public void start() {
        this.scanner = new Scanner(System.in);
        this.set = false;

        while (!set) {
            System.out.println("Server IP: ");
            ip = scanner.nextLine();
            System.out.println("Server port: ");
            port = scanner.nextLine();

            try {
                Socket socket = new Socket(ip, Integer.parseInt(port));
                this.set = true;

                this.client = new Client();
                client.setupClient("cli", socket);
                client.run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
