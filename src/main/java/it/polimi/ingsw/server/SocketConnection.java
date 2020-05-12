package it.polimi.ingsw.server;

import it.polimi.ingsw.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class SocketConnection extends Observable<String> implements Connection,Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;

    private boolean active = true;

    public SocketConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }


    private synchronized boolean isActive() {
        return active;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void closeConnection() {
        send("Connection closed!");

        try {
            socket.close();
        } catch(IOException e) {
            System.err.println("Error when closing socket!");
        }

        active = false;
    }

    private void close() {
        closeConnection();
        System.out.println("Deregistering client");
        server.deregisterConnection(this);
        System.out.println("Client disconnected");
    }


    @Override
    public void asyncSend(Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }


    @Override
    public void run() {
        Scanner in;
        String name;

        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            send("Welcome! What is your name?");
            String read = in.nextLine();
            name = read;
            System.out.println(name + "connected");

            //Add the connection to the lobby(?)
            server.lobby(this,name);

            //Read commands
            while(isActive()) {
                read = in.nextLine();
                notify(read);
            }
        } catch(IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        } finally {
            close();
        }
    }



}
