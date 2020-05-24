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
        Lobby canJoin = null;

        for(Lobby lobby : lobbyHandler.getLobbyList()) {
            if (!lobby.isFull()) {
                canJoin = lobby;
                canJoin.addPlayer(name);
                break;
            }
        }
        assert canJoin != null;
        return canJoin.getLobbyID();
    }

    //on server runs a method that checks if a lobby is full or not and then starts the game on the current lobby

}