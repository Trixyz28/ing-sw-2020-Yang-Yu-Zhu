package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.lobby.LobbyController;
import it.polimi.ingsw.lobby.LobbyHandler;
import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.messages.Obj;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    private int port;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private Map<Integer, List<SocketConnection>> matchConnection = new HashMap<>();

    private LobbyHandler lobbyHandler;
    private LobbyController lobbyController;

    public Server() {
        lobbyHandler = new LobbyHandler();
        lobbyController = new LobbyController(lobbyHandler);
    }


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

            } catch (IOException e) {
                System.out.println("Connection Error");
            }
        }
    }


    public synchronized void deregisterMatch(SocketConnection c) {

        int lobbyID = c.getLobbyID();

        if(matchConnection.containsKey(lobbyID)) {
            for(SocketConnection connection : matchConnection.get(lobbyID)) {
                lobbyHandler.removePlayer(connection.getPlayer().getPlayerNickname());
                if(connection.getPlayer().getPlayerID()!=c.getPlayer().getPlayerID()) {
                    connection.closeConnection();
                }
            }
            matchConnection.remove(lobbyID);
        }
    }

    public synchronized void lostPlayerQuit(SocketConnection c) {
        lobbyHandler.removePlayer(c.getPlayer().getPlayerNickname());
        matchConnection.get(c.getLobbyID()).remove(c);
        c.closeConnection();
    }


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
        if(matchConnection.get(lobbyID).size()==lobbyHandler.getLobbyList().get(lobbyID).getLobbyPlayersNumber()) {

            for(SocketConnection connection : matchConnection.get(lobbyID)) {
                connection.setInMatch(true);

                //Set player ID
                connection.getPlayer().setPlayerID(matchConnection.get(lobbyID).indexOf(connection));

                //Display match players info
                connection.asyncSend(new Obj("playerList",lobbyHandler.getLobbyList().get(lobbyID).getPlayersNameList()));

            }

            //Create and initialize Model
            Model model = new Model();
            model.initialize(lobbyHandler.getLobbyList().get(lobbyID).getLobbyPlayersNumber());

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
            view0.notify("setup");

        }

    }

    public void removeFromLobby(int lobbyID, SocketConnection connection,String nickname) {
        matchConnection.get(lobbyID).remove(connection);
        lobbyHandler.removePlayer(nickname);
        lobbyHandler.getLobbyList().get(lobbyID).removePlayer(nickname);
        System.out.println("Removed lobby connection of " + connection.getPlayer().getPlayerNickname() + " in lobby n." + lobbyID);
        System.out.println("Lobby n." + lobbyID + " actual size: " + lobbyHandler.getLobbyList().get(lobbyID).getAvailablePlayerNumber());
    }



    public LobbyHandler getLobbyHandler() {
        return lobbyHandler;
    }

    public LobbyController getLobbyController() {
        return lobbyController;
    }


}
