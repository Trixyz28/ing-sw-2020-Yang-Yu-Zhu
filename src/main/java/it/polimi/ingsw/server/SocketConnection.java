package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.messages.Obj;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observers.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class SocketConnection extends Observable implements Runnable {

    private Socket socket;
    private Server server;

    private Scanner in;
    private ObjectOutputStream out;

    private int lobbyID;
    private Player player;

    private boolean inMatch = false;
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
            send(Messages.connectionClosed);
            System.out.println("Deregistering player " + player.getPlayerNickname() + " of lobby n." + lobbyID);
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
        server.deregisterMatch(this);
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

                send(new Obj("lobbyMsg",Messages.canCreateLobby));

                do {
                    try {
                        String str = in.nextLine();
                        number = Integer.parseInt(str);
                    } catch (NumberFormatException e) {
                        send(new Obj("lobbyMsg",Messages.lobbyPlayerNumber));
                        number = 0;
                    }
                } while(number!=2 && number!=3);

                lobbyID = server.getLobbyController().createLobby(player.getPlayerNickname(),number);
                send(new Obj("lobbyOk","Create the lobby n." + lobbyID));
            }

            //Join a lobby
            else {
                lobbyID = server.getLobbyController().joinLobby(player.getPlayerNickname());
                send(new Obj("lobbyOk","Successfully join the lobby n." + lobbyID));
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
        } finally {
            if (!inMatch) {
                server.removeFromLobby(lobbyID, this, player.getPlayerNickname());
            } else if (active) {
                if(!lost) {
                    closeMatch();
                } else {
                    deregisterPlayer();
                }

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
        send(new Obj("nameMsg",Messages.nicknameRequest));
        String readName;
        boolean check = false;
        do {
            readName = in.nextLine();

            if(readName.isBlank() || readName.length()>16) {
                send(new Obj("nameMsg",Messages.invalidNickname));
            } else {
                check = server.getLobbyController().canUseNickname(readName);
                if(!check) {
                    send(new Obj("nameMsg",Messages.nicknameInUse));
                } else {
                    send(new Obj("nameMsg",Messages.nicknameAvailable));
                }
            }

        } while(!check);

        player = new Player(readName);
        send(new Obj("setName",readName));
        server.getLobbyHandler().addPlayer(player.getPlayerNickname());
    }

    public void setInMatch(boolean inMatch) {
        this.inMatch = inMatch;
    }
}