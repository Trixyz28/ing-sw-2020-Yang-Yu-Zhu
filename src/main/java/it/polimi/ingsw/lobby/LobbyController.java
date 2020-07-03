package it.polimi.ingsw.lobby;

/**
 * Controller that handles the creation and management of new lobbies through a lobby handler.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class LobbyController {

    private final LobbyHandler lobbyHandler;

    /**
     *Creates a <code>LobbyController</code> with the specified attributes.
     * @param lobbyHandler represents the waiting room handling all lobbies.
     */

    public LobbyController(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
    }

    /**
     * Checks if the nickname can be used or not.
     * A nickname can't be used if it is already taken by a player in game in this moment.
     * @param s Variable that is the nickname at issue.
     * @return A boolean: <code>true</code> if the nickname can be used, otherwise <code>false</code>.
     */
    public boolean canUseNickname(String s) {

        for (String name : lobbyHandler.getPlayerList()) {
            if (s.equals(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     *Creates a new lobby.
     * @param name Variable that represents the first player that got in the lobby.
     * @param playerNumber Variable that represents the number of players of the lobby
     *                     chosen by the first player.
     * @return An integer that represents the id of the lobby created with the parameters at issue.
     */
    public int createLobby(String name,int playerNumber) {
        return lobbyHandler.newLobby(name,playerNumber);
    }

    /**
     *Makes a player join a lobby.
     * @param name Variable that represents the nickname of the player.
     * @return An integer that represents the ID of the lobby the player at issue joined.
     */
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

    /**
     * Removes the lobby from the lobbyHandler.
     * @param lobbyID Variable that represents the ID of the lobby at issue.
     */
    public void removeLobby(int lobbyID) {
        lobbyHandler.getLobbyList().remove(lobbyHandler.findLobby(lobbyID));
    }

    //on server runs a method that checks if a lobby is full or not and then starts the game on the current lobby

}