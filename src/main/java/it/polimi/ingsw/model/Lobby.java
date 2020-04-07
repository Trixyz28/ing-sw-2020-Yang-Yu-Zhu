package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Lobby {


    private String lobbyID;
    public String getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(String lobbyID) {
        this.lobbyID = lobbyID;
    }

    //Name of players in this lobby
    private ArrayList<String> playersNameList;


    //Number of players in this lobby
    private int availablePlayerNumber;
    public int getAvailablePlayerNumber() {
        return availablePlayerNumber;
    }



    private int playersNumber;

    public ArrayList<String> addPlayer(String name) {

        return playersNameList;
    }

    //If the lobby is complete -> true
    private boolean checkFull;
    //if playersNumber == availablePlayerNumber
    public boolean isFull(int playersNumber,int availablePlayerNumber) {


        return checkFull;
    }



    public void createMatch() {

    }
}
