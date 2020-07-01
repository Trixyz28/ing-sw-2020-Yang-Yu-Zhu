package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.messages.Obj;
import it.polimi.ingsw.messages.Tags;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observers.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class SocketConnection extends Observable implements Runnable {

    private Socket socket;
    private Server server;

    private Scanner in;
    private ObjectOutputStream out;

    private int lobbyID = -1;
    private Player player;

    private boolean inMatch = false;
    private boolean active = true;
    private boolean lost = false;

    public SocketConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }


    public synchronized boolean isActive() {
        return active;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e) {
            System.out.println("SocketConnection stopped");
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

    public void deletePlayer() {
        server.deregisterPlayer(this);
    }


    public void closeMatch()  {
        server.deregisterMatch(this);
    }



    public void asyncSend(final Object message) {
        new Thread(() -> send(message)).start();
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

        } catch(Exception e) {
            if(player!=null) {
                System.out.print(player.getPlayerNickname());
            }
            System.out.println(": Connection.run() stopped");
        } finally {
            if (!inMatch) {
                if(player!=null) {
                    if(lobbyID==-1) {
                        server.removePlayerName(player.getPlayerNickname());
                    } else {
                        server.removeFromLobby(lobbyID, this, player.getPlayerNickname());
                    }
                }
            } else if(lost) {
                active = false;
                deletePlayer();
            } else if(active) {
                System.out.println("closematch " + player.getPlayerNickname());
                closeMatch();
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
        send(new Obj(Tags.NAME_MSG,Messages.nicknameRequest));
        String readName;
        boolean check = false;
        do {
            readName = in.nextLine();

            if(readName.isBlank() || readName.length()>16) {
                send(new Obj(Tags.NAME_MSG,Messages.invalidNickname));
            } else {
                check = server.getLobbyController().canUseNickname(readName);
                if(!check) {
                    send(new Obj(Tags.NAME_MSG,Messages.nicknameInUse));
                } else {
                    send(new Obj(Tags.NAME_MSG,Messages.nicknameAvailable));
                }
            }

        } while(!check);

        player = new Player(readName);
        send(new Obj(Tags.SET_NAME,readName));
        server.getLobbyHandler().addPlayer(player.getPlayerNickname());
    }

    public void setInMatch(boolean inMatch) {
        this.inMatch = inMatch;
    }

}