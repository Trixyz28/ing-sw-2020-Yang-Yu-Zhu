package it.polimi.ingsw.lobby;


public class LobbyController {

    private LobbyHandler lobbyHandler;

    public LobbyController(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
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

    //on server runs a method that checks if a lobby is full or not and then starts the game on the current lobby

}