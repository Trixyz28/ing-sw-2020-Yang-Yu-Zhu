package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observers.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class SocketConnection extends Observable implements Runnable {

    private Socket socket;
    private Server server;

    private Scanner in;
    private ObjectOutputStream out;

    private int lobbyID;
    private Player player;

    private boolean active = true;
    private boolean lost = false;

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


    public void closeConnection() {
        active = false;
        try {
            send("Connection closed!");
            socket.close();
        } catch(IOException e) {
            System.err.println("Error when closing socket!");
        }

    }

    public void deregisterPlayer() {
        server.lostPlayerQuit(this);
    }


    public void closeMatch() {
        closeConnection();
        System.out.println("Deregistering player " + player.getPlayerNickname() + " of lobby n." + lobbyID);
        server.deregisterMatch(this);
        System.out.println("Client disconnected");
    }



    public void asyncSend(final Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    public synchronized void syncSend(Object message) {
        send(message);
    }


    @Override
    public void run() {

        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            setNickname();

            //Create a lobby
            if(!server.getLobbyHandler().checkAvailableLobby()) {
                int number;

                send("You can create a lobby\nHow many players can join this match? (2-3)");

                do {
                    try {
                        String str = in.nextLine();
                        number = Integer.parseInt(str);
                    } catch (NumberFormatException e) {
                        send("Insert 2 or 3");
                        number = 0;
                    }
                } while(number!=2 && number!=3);

                lobbyID = server.getLobbyController().createLobby(player.getPlayerNickname(),number);
                send("Create the lobby n." + lobbyID);
            }

            //Join a lobby
            else {
                lobbyID = server.getLobbyController().joinLobby(player.getPlayerNickname());
                send("Successfully join the lobby n." + lobbyID);
            }



            //Add the connection to the connection list & setup match
            server.match(lobbyID,this);



            //Read commands
            while(isActive()) {
                String read = in.nextLine();
                System.out.println("From " + player.getPlayerNickname() + ": " + read);

                if(!lost) {
                    notify(read);
                } else {
                    asyncSend(Messages.spectator);
                }
            }


        } catch(IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
            active = false;
        } finally {
            if(!lost) {
                closeMatch();
            } else {
                deregisterPlayer();
            }
        }
    }


    public int getLobbyID() {
        return lobbyID;
    }


    public Player getPlayer() {
        return player;
    }


    public void setLost(boolean lost) {
        this.lost = lost;
    }


    public void setNickname() {
        //Set nickname
        send(Messages.nicknameRequest);
        String readName;
        boolean check;
        do {
            readName = in.nextLine();
            check = server.getLobbyController().canUseNickname(readName);
            if(!check) {
                send(Messages.nicknameInUse);
            } else {
                send(Messages.nicknameAvailable);
            }
        } while(!check);

        player = new Player(readName);
        server.getLobbyHandler().addPlayer(player.getPlayerNickname());
        send("Hi, " + player.getPlayerNickname() + "!");
    }


}