package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;

/**
 * Main class for the server application
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class ServerApp {

    /**
     * Main class which starts the server on the designated port.
     * @param args Variable from command line indicating the port number of the server.
     */
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
