package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;


public class ServerApp {

    public static void main(String[] args) {

        Server server = new Server();

        try {
            if(args.length==0) {
                server.startServer("45000");
            } else {
                server.startServer(args[0]);
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
