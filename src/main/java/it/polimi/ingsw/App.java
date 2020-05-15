package it.polimi.ingsw;

import it.polimi.ingsw.lobby.LobbyController;
import it.polimi.ingsw.lobby.LobbyHandler;


public class App {

    public static void main(String[] args) {

        LobbyHandler lobbyHandler = new LobbyHandler();
        LobbyController lobbyController = new LobbyController(lobbyHandler);

    }
}