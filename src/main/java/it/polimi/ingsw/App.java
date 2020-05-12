package it.polimi.ingsw;

import it.polimi.ingsw.lobby.LobbyController;
import it.polimi.ingsw.lobby.LobbyHandler;
import it.polimi.ingsw.lobby.LobbyView;


public class App {

    public static void main(String[] args) {

        LobbyHandler lobbyHandler = new LobbyHandler();
        LobbyView lobbyView = new LobbyView();
        LobbyController lobbyController = new LobbyController(lobbyHandler,lobbyView);

        lobbyView.addObservers(lobbyController);
        lobbyHandler.addObservers(lobbyView);
        lobbyView.run();

    }
}