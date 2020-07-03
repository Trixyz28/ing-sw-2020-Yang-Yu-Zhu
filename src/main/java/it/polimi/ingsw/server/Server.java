package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.lobby.Lobby;
import it.polimi.ingsw.lobby.LobbyController;
import it.polimi.ingsw.lobby.LobbyHandler;
import it.polimi.ingsw.messages.Obj;
import it.polimi.ingsw.messages.Tags;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that is used to setup and handle the Server.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Server {

    private int port;
    private ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<Integer, List<SocketConnection>> matchConnection = new HashMap<>();

    private final LobbyHandler lobbyHandler;
    private final LobbyController lobbyController;

    /**
     *  Creates a <code>Server</code> with the specified attributes.
     */
    public Server() {
        lobbyHandler = new LobbyHandler();
        lobbyController = new LobbyController(lobbyHandler);
    }

    /**
     * Starts the server with the chosen port. It will be ready to receive client connections.
     * @param inputPort Variable that represents the port of the server.
     * @throws IOException If no connection is established.
     * At any Exception caught, the server should be disconnected.
     */
    public void startServer(String inputPort) throws IOException {

        this.port = Integer.parseInt(inputPort);
        this.serverSocket = new ServerSocket(port);

        System.out.println("Server socket ready on port: " + port);

        while(true) {
            try {
                Socket newSocket = serverSocket.accept();

                //Connection handle
                SocketConnection socketConnection = new SocketConnection(newSocket,this);
                System.out.println("There is a new connected client");

                executor.submit(socketConnection);

            } catch (Exception e) {
                System.out.println("Connection Error");
                serverSocket.close();
            }
        }
    }

    /**
     * Handles the connections and eventually starts a new match of a full lobby handling all steps to set it up,
     * thus setting up the remoteViews, the Model and the Controller for the match of the lobby.
     * <p></p>
     * This method is started as the player connects and enters a lobby. It maps the player connection to
     * the correspondent lobby.
     * When a lobby is full the original <code>match</code> method sets up the "waterfall" of steps to initialize the match.
     * <p></p>
     * @param lobbyID Variable that indicates the lobby at issue.
     * @param c Variable that represents the server-side connection of each player.
     */
    public synchronized void match(int lobbyID, SocketConnection c) {

        //The match is not registered yet
        if(!matchConnection.containsKey(lobbyID)) {
            List<SocketConnection> connections = new ArrayList<>();
            matchConnection.put(lobbyID, connections);
        }

        //Add the connection into the match
        matchConnection.get(lobbyID).add(c);

        System.out.println("Lobby n." + lobbyID +": " + matchConnection.get(lobbyID).size() + " available players");

        //All players ready to start
        if(matchConnection.get(lobbyID).size()==lobbyHandler.findLobby(lobbyID).getLobbyPlayersNumber()) {

            for(SocketConnection connection : matchConnection.get(lobbyID)) {
                connection.setInMatch(true);

                //Set player ID
                connection.getPlayer().setPlayerID(matchConnection.get(lobbyID).indexOf(connection));

                //Display match players info
                connection.send(new Obj(Tags.PLAYER_LIST,lobbyHandler.findLobby(lobbyID).getPlayersNameList()));
            }

            //Create and initialize Model
            Model model = new Model();
            model.initialize(lobbyHandler.findLobby(lobbyID).getLobbyPlayersNumber());

            //Set players in model
            model.addPlayer(matchConnection.get(lobbyID).get(0).getPlayer());
            model.addPlayer(matchConnection.get(lobbyID).get(1).getPlayer());

            //Create and connect RemoteView
            View view0 = new RemoteView(matchConnection.get(lobbyID).get(0).getPlayer(),matchConnection.get(lobbyID).get(0));
            View view1 = new RemoteView(matchConnection.get(lobbyID).get(1).getPlayer(),matchConnection.get(lobbyID).get(1));

            //Create Controller
            Controller controller = new Controller(model);

            //Set observers
            model.addObservers(view0);
            model.addObservers(view1);
            view0.addObservers(controller);
            view1.addObservers(controller);

            //3 players case
            if(model.getPlayersNumber()==3) {
                model.addPlayer(matchConnection.get(lobbyID).get(2).getPlayer());
                View view2 = new RemoteView(matchConnection.get(lobbyID).get(2).getPlayer(),matchConnection.get(lobbyID).get(2));
                model.addObservers(view2);
                view2.addObservers(controller);
            }

            //Initialize match conditions
            view0.notify(new Obj(Tags.SETUP,""));
        }
    }

    /**
     * Removes completely one player at issue from the lobby.
     * @param lobbyID Variable that indicates the lobby
     * @param connection Variable that indicates the connection of the player at issue.
     * @param nickname Variable that represents the nickname of the player at issue.
     */
    public synchronized void removeFromLobby(int lobbyID, SocketConnection connection,String nickname) {
        Lobby lobby = lobbyHandler.findLobby(lobbyID);
        matchConnection.get(lobbyID).remove(connection);
        lobbyHandler.removePlayer(nickname);
        lobby.removePlayer(nickname);
        System.out.println("Removed lobby connection of " + connection.getPlayer().getPlayerNickname() + " in lobby n." + lobbyID);
        System.out.println("Lobby n." + lobbyID + " actual size: " + lobby.getAvailablePlayerNumber());
        if(lobby.getAvailablePlayerNumber()==0) {
            lobbyController.removeLobby(lobbyID);
            System.out.println("Lobby n." + lobbyID + " deleted");
        }
    }

    /**
     * Removes a player's nickname from the lobby.
     * @param nickname Variable that indicates which nickname has been freed from usage.
     */
    public synchronized void removePlayerName(String nickname) {
        lobbyHandler.removePlayer(nickname);
    }

    /**
     * Removes a player's connection to the lobby.
     * @param c Variable that indicates which connection needs to be cut.
     */
    public synchronized void deregisterPlayer(SocketConnection c) {
        lobbyHandler.removePlayer(c.getPlayer().getPlayerNickname());
        matchConnection.get(c.getLobbyID()).remove(c);
        c.closeConnection();
    }

    /**
     * Deregisters the match of the connection owner from server and deletes the associated lobby after the game has finished.
     * All players in the match are deregistered.
     * @param c Variable that is the connection used by the client at issue.
     */
    public synchronized void deregisterMatch(SocketConnection c) {
        int lobbyID = c.getLobbyID();

        if(matchConnection.containsKey(lobbyID)) {
            while(!matchConnection.get(lobbyID).isEmpty()) {
                deregisterPlayer(matchConnection.get(lobbyID).get(0));
            }
            matchConnection.remove(lobbyID);
            lobbyController.removeLobby(lobbyID);
        }
    }

    /**
     * Gets the LobbyHandler at issue.
     * @return An object <code>LobbyHandler</code> currently in use.
     */
    public LobbyHandler getLobbyHandler() {
        return lobbyHandler;
    }

    /**
     * Gets the LobbyController at issue.
     * @return An object <code>LobbyController</code> currently in use.
     */
    public LobbyController getLobbyController() {
        return lobbyController;
    }


}
