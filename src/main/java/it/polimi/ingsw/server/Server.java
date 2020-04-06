package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    ServerSocket serverSocket = new ServerSocket(60000);
    Socket connection = serverSocket.accept();

    public Server() throws IOException {



    }


}
