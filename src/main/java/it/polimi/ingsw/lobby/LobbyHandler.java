package it.polimi.ingsw.lobby;

import java.util.ArrayList;

/**
 * Manages all the lobbies of the server.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */

public class LobbyHandler {


    //List of all active lobbies
    private final ArrayList<Lobby> lobbyList;

    //List of all players
    private final ArrayList<String> playerList;

    private int counter = 0;

    /**
     *Creates a <code>LobbyHandler</code> with the specified attributes.
     */
    public LobbyHandler() {
        this.lobbyList = new ArrayList<>();
        this.playerList = new ArrayList<>();
    }

    /**
     *Adds a player to the waiting room.
     * @param name Variable that represents the name of the player at issue.
     */
    //Add a new player in the waiting room
    public void addPlayer(String name) {
        playerList.add(name);
    }

    /**
     *Removes a player from the waiting room.
     * @param name Variable that represents the name of the player at issue.
     */
    public void removePlayer(String name) {
        playerList.remove(name);
    }

    /**
     *Gets the list of the lobbies of the server.
     * @return A list made of the IDs of the lobbies.
     */
    //Get() of lobbyList
    public ArrayList<Lobby> getLobbyList() {
        return lobbyList;
    }

    /**
     *Gets the list of the players in the waiting room.
     * @return A list made of the IDs of the players.
     */
    //Get() of playerList
    public ArrayList<String> getPlayerList() {
        return playerList;
    }

    /**
     *Checks a lobby's availability.
     * @return A boolean: <code>true</code> if the lobby is available, otherwise <code>false</code>.
     */
    //Check the presence of a lobby available to join
    public boolean checkAvailableLobby() {
        for(Lobby lobby : lobbyList) {
            if(!lobby.isFull()) {
                return true;
            }
        }
        return false;
    }

    /**
     *Handles the creation of a new <code>lobby</code>.
     * @param name Variable that represents the nickname of the first player of the lobby at issue.
     * @param playerNumber Variable that represents the number of players of the lobby at issue.
     * @return The ID of the newly created lobby.
     */
    //Creation of a new lobby and setting up groundwork for the players List
    public int newLobby(String name,int playerNumber) {

        Lobby newLobby = new Lobby(playerNumber);

        counter++;
        lobbyList.add(newLobby);
        newLobby.setLobbyID(counter-1);

        ArrayList<String> temporaryList = new ArrayList<>();
        temporaryList.add(name);

        //copies temporaryList into the attribute of the associated Lobby object
        newLobby.setPlayersNameList(temporaryList);

        return newLobby.getLobbyID();
    }

    /**
     *Finds a lobby in the server.
     * @param index Variable that represents the ID of the wanted lobby.
     * @return The <code>lobby</code> at issue, if not found returns <code>null</code>.
     */
    public Lobby findLobby(int index) {
        for(Lobby lobby : getLobbyList()) {
            if(lobby.getLobbyID() == index) {
                return lobby;
            }
        }
        return null;
    }

}
