package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;


public class ServerApp {

    public static void main(String[] args) {

        Server server;

        try {
            server = new Server(45000);
            server.startServer();

        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
