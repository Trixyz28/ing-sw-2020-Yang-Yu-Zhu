package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Lobby {

    //Value of the id of the Lobby
    private String lobbyID;

    //Names of players in this Lobby
    private ArrayList<String> playersNameList;

    //Number of players the Lobby was made for
    private int lobbyPlayersNumber;

    //If the Lobby is complete -> true
    private boolean checkFull = false;



    //Get() of the LobbyID
    public String getLobbyID() {

        return lobbyID;
    }

    //Set() of the LobbyID
    public void setLobbyID(String lobby) {

        this.lobbyID = lobby;
    }

    //Set()of checkFull
    public void setCheckFull() {

        this.checkFull = true;
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
    public void getPlayersNameList() {

        for (int i = 0; i < lobbyPlayersNumber; i++) {
            String helper = playersNameList.get(i);
            if (helper.equals("0000")) {
                System.out.println("Player not available yet.");
            } else {
                System.out.println(helper);
            }

        }
    }

    //Set() of the playersNameList from Lobbies at creation of Lobby
    public void setPlayersNameList(ArrayList<String> parameterList){

        ArrayList<String> playersNameList = (ArrayList<String>)parameterList.clone();
    }

    //Adds a player name to the List: if value is "000" it is changed to the player name then return
    public void addPlayer(String playerName) {

        int flag = 0;

        for (int i = 0; i < lobbyPlayersNumber ; i++) {

            String helper = playersNameList.get(i);

            if (flag == 1) {
                System.out.println("Done.");
            }
            if (helper.equals("0000")) {
                playersNameList.set(0,playerName);
                flag = 1;
            }

        }
        if (flag == 0){
            System.out.println("Lobby is full");
        }
    }

    //Get() of the value of the available Players : for every player not "0000" availablePlayers +1
    public int getAvailablePlayerNumber(Lobby lobby) {

        int availablePlayers = 0;

        for (int i = 0; i < lobby.lobbyPlayersNumber; i++) {

            String helper = lobby.playersNameList.get(i);
            if (!helper.equals("0000")) {
                availablePlayers++;
            }
        }

        return availablePlayers;
    }

    //new match creator on call
    public void createMatch(Model model) {
        model.initialize(lobbyPlayersNumber, playersNameList);
    }
}
