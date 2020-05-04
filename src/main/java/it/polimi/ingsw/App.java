package it.polimi.ingsw;

import it.polimi.ingsw.lobby.LobbyController;
import it.polimi.ingsw.lobby.Lobbies;
import it.polimi.ingsw.lobby.LobbyView;


public class App {

    public static void main(String[] args) {

        Lobbies lobbies = new Lobbies();
        LobbyView lobbyView = new LobbyView();
        LobbyController lobbyController = new LobbyController(lobbies,lobbyView);

        lobbyView.addObservers(lobbyController);
        lobbies.addObservers(lobbyView);
        lobbyView.run();

    }
}