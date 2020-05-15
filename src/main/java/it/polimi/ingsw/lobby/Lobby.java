package it.polimi.ingsw.lobby;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class Lobby extends Observable {

    //Value of the id of the Lobby
    private int lobbyID;

    //Names of players in this Lobby
    private ArrayList<String> playersNameList;

    //Number of players the Lobby was made for
    private int lobbyPlayersNumber;

    //If the Lobby is complete -> true
    private boolean full = false;


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

    //Set() of the number of the Lobby players
    public void setLobbyPlayersNumber(int playersNumber) {

        this.lobbyPlayersNumber = playersNumber;
    }

    //Get() : Print on screen the list of players;if value is "0000" the players doesn't exist yet
    public void printPlayersNameList() {

        for (int i = 0; i < lobbyPlayersNumber; i++) {
            String helper = playersNameList.get(i);
            if (helper.equals("0000")) {
                System.out.println("Player not available yet.");
            } else {
                System.out.println(helper);
            }

        }
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

        int flag = 0;
        int i;

        for (i = 0; (i < lobbyPlayersNumber && flag==0); i++) {
            String helper = playersNameList.get(i);
            if (helper.equals("0000")) {
                playersNameList.set(i,playerName);
                flag++;
            }
        }

        if (i==lobbyPlayersNumber) {
            setFull(true);
        }
    }

    //Get() of the value of the available Players : for every player not "0000" availablePlayers +1
    public int getAvailablePlayerNumber() {

        int availablePlayers = 0;

        for (int i = 0; i < lobbyPlayersNumber; i++) {

            String helper = playersNameList.get(i);
            if (!helper.equals("0000")) {
                availablePlayers++;
            }
        }

        return availablePlayers;
    }

    //new match creator on call
    public void createMatch() {

        Model model = new Model();
        System.out.println("Model created");
    }
}
