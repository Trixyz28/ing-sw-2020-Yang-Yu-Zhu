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
/**
 * Class used to handle a client connection.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class SocketConnection extends Observable<String> implements Runnable {

    private final Socket socket;
    private final Server server;

    private Scanner in;
    private ObjectOutputStream out;

    private int lobbyID = -1;
    private Player player;

    private boolean inMatch = false;
    private boolean active = true;
    private boolean lost = false;

    /**
     *  Creates a <code>SocketConnection</code> with the specified attributes.
     * @param socket Variable that indicates the socket used.
     * @param server Variable that indicates the server used.
     */
    public SocketConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Checks if the connection is active or not.
     * @return A boolean: <code>true</code> if the connection is active, otherwise <code>false</code>.
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * Sends an message through the connection to the socket.
     * @param message Variable that represents the message that needs to be sent.
     */
    public synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e) {
            System.out.println("SocketConnection stopped");
        }
    }

    /**
     * Closes the socket of the connection.
     */
    public void closeConnection() {
        active = false;
        try {
            System.out.println("Deregistering player " + player.getPlayerNickname() + " of lobby n." + lobbyID);
            socket.close();
            in.close();
            out.close();

        } catch(IOException e) {
            System.err.println("Error when closing socket!");
        }

    }

    /**
     * Deletes the player associated to the socket from the server.
     */
    public void deletePlayer() {
        server.deregisterPlayer(this);
    }

    /**
     * Closes the match on the socket.
     */
    public void closeMatch()  {
        server.deregisterMatch(this);
    }

    /**
     * Starts a new thread to send a message.
     * @param message Variable that represents the message to be sent.
     */
    public void asyncSend(final Object message) {
        new Thread(() -> send(message)).start();
    }

    /**
     * {@inheritDoc}
     *<p></p>
     * Handles the connection between the server and a new client.
     * <p></p>
     */
    @Override
    public void run() {

        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            setNickname();

            //Create a lobby
            if(!server.getLobbyHandler().checkAvailableLobby()) {
                noAvailableLobby();
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
                    send(new Obj(Tags.GENERIC,Messages.spectator));
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

    /**
     * If there's no available Lobby then a new one is created for the player newly connected.
     */
    private void noAvailableLobby() {
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

    /**
     * Sets the nickname of the player connect
     */
    private void setNickname() {
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

    /**
     * Gets the Id of the lobby at issue.
     * @return An integer that represents the Id of the lobby.
     */
    public int getLobbyID() {
        return lobbyID;
    }

    /**
     * Gets the player of the actual connection.
     * @return A <code>Player</code> object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the attribute that represents if the player connected has lost or not.
     */
    public void setLost(boolean lost) {
        this.lost = lost;
    }


    /**
     * Sets the attribute that indicates if the player connected is in a match or no.
     * @param inMatch Variable boolean: <code>True</code> if the player is in the match, otherwise <code>False</code>.
     */
    public void setInMatch(boolean inMatch) {
        this.inMatch = inMatch;
    }

}