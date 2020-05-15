package it.polimi.ingsw.server;

import it.polimi.ingsw.lobby.LobbyController;
import it.polimi.ingsw.lobby.LobbyHandler;
import it.polimi.ingsw.lobby.LobbyView;

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
    private Map<String, Connection> waitingConnection = new HashMap<>();
    private Map<Connection, Connection> playingConnection = new HashMap<>();

    private LobbyHandler lobbyHandler;
    private LobbyView lobbyView;
    private LobbyController lobbyController;


    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }


    public void setWaitingRoom() {

        lobbyHandler = new LobbyHandler();
        lobbyController = new LobbyController(lobbyHandler);

    }



    public void startServer() {

        setWaitingRoom();
        System.out.println("Waiting room ready");

        System.out.println("Server socket ready on port: " + port);

        while(true) {
            try {

                System.out.println("Waiting for client connection");
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


    public synchronized void deregisterConnection(Connection c) {

        Connection opponent = playingConnection.get(c);

        if(opponent!=null) {
            opponent.closeConnection();
        }

        playingConnection.remove(c);
        playingConnection.remove(opponent);

        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()) {
            if(waitingConnection.get(iterator.next())==c) {
                iterator.remove();
            }
        }
    }


    public synchronized void lobby(Connection c, String name) {

        waitingConnection.put(name,c);

        if(waitingConnection.size() == 2) {
            List<String> keys = new ArrayList<>(waitingConnection.keySet());

            Connection c1 = waitingConnection.get(keys.get(0));
            Connection c2 = waitingConnection.get(keys.get(1));

            playingConnection.put(c1,c2);
            playingConnection.put(c2,c1);
            waitingConnection.clear();

            c1.asyncSend("Print map");
            c2.asyncSend("Print map");

            int turno = 0;

            if(turno==0) {
                c1.asyncSend("move");
                c2.asyncSend("wait");
            } else {
                c2.asyncSend("move");
                c1.asyncSend("wait");
            }

        }
    }

    public LobbyHandler getLobbyHandler() {
        return lobbyHandler;
    }

    public LobbyController getLobbyController() {
        return lobbyController;
    }

    public boolean canCreateLobby() {
        if(waitingConnection.size()==0) {
            return true;
        }

        return false;
    }

}
