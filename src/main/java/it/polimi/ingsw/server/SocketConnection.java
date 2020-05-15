package it.polimi.ingsw.server;

import it.polimi.ingsw.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class SocketConnection extends Observable<String> implements Connection,Runnable {

    private Socket socket;
    private Server server;

    private Scanner in;
    private ObjectOutputStream out;

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
    public void asyncSend(final Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }


    @Override
    public void run() {

        String playerName;
        int lobbyID;
        int playerID;


        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());


            //Set nickname
            send("Welcome! What is your nickname?");
            String readName;
            boolean check;
            do {
                readName = in.nextLine();
                check = server.getLobbyController().canUseNickname(readName);
                if(!check) {
                    send("Nickname in use, choose another one");
                } else {
                    send("You can use this nickname!");
                }
            } while(!check);

            playerName = readName;
            server.getLobbyHandler().addPlayer(playerName);
            send("Hi, " + playerName + "!");



            //Create a lobby
            if(!server.getLobbyHandler().checkAvailableLobby()) {
                int number;

                send("You can create a lobby");
                send("How many players can join this match?");

                do {
                    send("Insert 2 or 3");
                    number = in.nextInt();
                } while(number!=2 && number!=3);

                lobbyID = server.getLobbyController().createLobby(playerName,number);
                send("Create the lobby n." + lobbyID);
            }

            //Join a lobby
            else {
                lobbyID = server.getLobbyController().joinLobby(playerName);
                send("Successfully join the lobby n." + lobbyID);
            }






            //Add the connection to the lobby(?)
            server.lobby(this,playerName);

            //Read commands
            while(isActive()) {
                String read = in.nextLine();
                System.out.println("Received: " + read);
                notify(read);
            }





        } catch(IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        } finally {
            close();
        }
    }


    public void sendPlayersList() {



    }


}
