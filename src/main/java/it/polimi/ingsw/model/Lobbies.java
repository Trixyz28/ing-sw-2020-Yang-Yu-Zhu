package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Lobbies {

    //Nickname chosen by the first player
    private String firstPlayerNickname;

    //Total players number in the game
    private int totalPlayersNumber;

    //Creation of the lobby
    private Lobby currentLobby;



    //Get() of the value of total players number
    public int getTotalPlayersNumber() {

        return getTotalPlayersNumber();
    }

    //Set() of the total players value: the first player chooses the total players number
    public void setTotalPlayersNumber(int playersNumber) {

        this.totalPlayersNumber = playersNumberNumber;
    }

    //Get() of the current lobby
    public Lobby getCurrentLobby() {

        return currentLobby;
    }

    //Set() of the current lobby
    public void setCurrentLobby(Lobby lobby) {

        this.currentLobby = lobby;
    }

    //Creation of a new lobby and setting up groundwork for the players List
    private void createLobby() {

        Lobby newLobby = new Lobby();

        //Set in the Lobby of the total players number
        setLobbyPlayersNumber(totalPlayersNumber);

        ArrayList<string> temporaryList = new ArrayList<string>();
        //First player nickname added in position [0], unknown players coded as "0000"
        temporaryList.add(firstPlayerNickname);
        for (int i = 1; i < totalPlayersNumber; i++) {
            playerNameList.add("0000");
        }
        //copies temporaryList into the attribute of the associated Lobby object
        setPlayersNameList(temporaryList);

    }

}
