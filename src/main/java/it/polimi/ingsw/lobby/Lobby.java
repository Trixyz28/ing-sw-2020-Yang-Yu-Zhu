package it.polimi.ingsw.lobby;

import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.model.Model;

import java.util.ArrayList;


public class Lobby extends Observable {

    //Value of the id of the Lobby
    private int lobbyID;

    //Names of players in this Lobby
    private ArrayList<String> playersNameList;

    //Number of players the Lobby was made for
    private final int lobbyPlayersNumber;

    //If the Lobby is complete -> true
    private boolean full = false;

    public Lobby(int playerNumber) {
        this.lobbyPlayersNumber = playerNumber;
    }

    //Get() of the LobbyID
    public int getLobbyID() {
        return lobbyID;
    }

    //Set() of the LobbyID
    public void setLobbyID(int lobbyID) {
        this.lobbyID = lobbyID;
    }


    //Get() of full
    public boolean isFull() {
        return full;
    }

    //Set() of full
    public void setFull(boolean full) {
        this.full = full;
    }


    //Get() of the number of the Lobby players
    public int getLobbyPlayersNumber() {
        return lobbyPlayersNumber;
    }


    public ArrayList<String> getPlayersNameList() {
        return playersNameList;
    }

    //Set() of the playersNameList from Lobbies at creation of Lobby
    public void setPlayersNameList(ArrayList<String> parameterList){
        playersNameList = (ArrayList<String>) parameterList.clone();
    }

    //Adds a player name to the List: if value is "0000" it is changed to the player name then return
    public void addPlayer(String playerName) {

        playersNameList.add(playerName);

        if (getAvailablePlayerNumber()==lobbyPlayersNumber) {
            setFull(true);
        }
    }

    //Get() of the value of the available Players : for every player not "0000" availablePlayers +1
    public int getAvailablePlayerNumber() {
        return playersNameList.size();
    }

    public void removePlayer(String playerName) {
        playersNameList.remove(playerName);
    }

}
