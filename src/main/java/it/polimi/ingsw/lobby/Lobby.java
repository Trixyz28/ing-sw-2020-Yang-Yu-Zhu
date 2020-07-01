package it.polimi.ingsw.lobby;

import it.polimi.ingsw.observers.Observable;

import java.util.ArrayList;

/**
 * Lobby handles the singular lobby associated to one game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Lobby extends Observable {

    //Value of the id of the Lobby
    private int lobbyID;

    //Names of players in this Lobby
    private ArrayList<String> playersNameList;

    //Number of players the Lobby was made for
    private final int lobbyPlayersNumber;

    //If the Lobby is complete -> true
    private boolean full = false;

    /**
     *Creates a <code>Lobby</code> with the specified number of players.
     * @param playerNumber Variable chosen by the first connected player indicating the numbers of players
     *                     that the lobby can host.
     */
    public Lobby(int playerNumber) {
        this.lobbyPlayersNumber = playerNumber;
    }

    /**
     *Gets the ID of the lobby.
     * @return The attribute <code>lobbyID</code> of the lobby at issue.
     */
    //Get() of the LobbyID
    public int getLobbyID() {
        return lobbyID;
    }

    /**
     * Sets the ID of the lobby.
     * @param lobbyID The attribute <code>lobbyId</code> is the integer we want to set the lobby to.
     */
    //Set() of the LobbyID
    public void setLobbyID(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    /**
     * Checks if the lobby is full.
     * @return A boolean: <code>true</code> if the lobby is full, otherwise <code>false</code>.
     */
    //Get() of full
    public boolean isFull() {
        return full;
    }

    /**
     * Sets the lobby to a different capacity of players
     * @param full The attribute is a boolean used to change to full or no the lobby
     */
    //Set() of full
    public void setFull(boolean full) {
        this.full = full;
    }

    /**
     * Gets the numbers of players of the lobby at issue.
     * @return The variable that represents the number of players the lobby can host.
     */
    //Get() of the number of the Lobby players
    public int getLobbyPlayersNumber() {
        return lobbyPlayersNumber;
    }

    /**
     * Gets the list of the players of the lobby.
     * @return A list of the nicknames of the players currently in the lobby.
     */
    public ArrayList<String> getPlayersNameList() {
        return playersNameList;
    }

    /**
     * Creates the list of the players for the lobby.
     * @param parameterList A list created with the first player that created the lobby.
     */
    //Set() of the playersNameList from Lobbies at creation of Lobby
    public void setPlayersNameList(ArrayList<String> parameterList){
        playersNameList = (ArrayList<String>) parameterList.clone();
    }

    /**
     * Adds a player to the list of the players of the lobby.
     * @param playerName Variable that represents the name of the player.
     */
    //Adds a player name to the List: if value is "0000" it is changed to the player name then return
    public void addPlayer(String playerName) {

        playersNameList.add(playerName);

        if (getAvailablePlayerNumber()==lobbyPlayersNumber) {
            setFull(true);
        }
    }

    /**
     *Gets the number of available players of the lobby.
     * @return An integer that is the size of the players list.
     */
    //Get() of the value of the available Players : for every player not "0000" availablePlayers +1
    public int getAvailablePlayerNumber() {
        return playersNameList.size();
    }

    /**
     * Removes a player form the list of players of the lobby.
     * @param playerName Variable that that represents the name of the player at issue.
     */
    public void removePlayer(String playerName) {
        playersNameList.remove(playerName);
    }

}
