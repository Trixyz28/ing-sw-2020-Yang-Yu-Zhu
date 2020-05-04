package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private String ip;
    private int port;

    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
    }

    Socket socket = new Socket();
    OutputStream os = socket.getOutputStream();
    InputStream reader = socket.getInputStream();

}
