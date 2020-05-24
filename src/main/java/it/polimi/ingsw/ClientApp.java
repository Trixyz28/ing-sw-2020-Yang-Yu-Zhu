package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.io.IOException;


public class ClientApp {

    public static void main(String[] args) {

        Client client = new Client();

        try {
            if(args.length==0) {
                client.startClient("CLI","127.0.0.1","45000");
            } else {
                client.startClient(args[0],args[1],args[2]);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
