package it.polimi.ingsw.lobby;

import it.polimi.ingsw.Observer;


public class LobbyController implements Observer {

    private LobbyHandler lobbyHandler;

    public LobbyController(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
    }


    @Override
    public void update(Object message) {
        if (message.equals("setup")) {
            System.out.println("Hi, first player!");

            for(int i = 2; i< lobbyHandler.getLobbyList().get(0).getLobbyPlayersNumber()+1; i++) {
                System.out.println("Player " + i + " ");
            }

        }
    }

    public boolean canUseNickname(String s) {

        for (String name : lobbyHandler.getPlayerList()) {
            if (s.equals(name)) {
                return false;
            }
        }
        return true;
    }

    public int createLobby(String name,int playerNumber) {
        return lobbyHandler.newLobby(name,playerNumber);
    }

    public int joinLobby(String name) {
        Lobby lobby = lobbyHandler.getLobbyList().get(lobbyHandler.getLobbyList().size()-1);
        lobby.addPlayer(name);
        return lobby.getLobbyID();
    }





    public void intoLobby(String name) {

    }


    //on server runs a method that checks if a lobby is full or not and then starts the game on the current lobby







}