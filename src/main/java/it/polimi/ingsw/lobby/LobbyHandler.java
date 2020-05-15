package it.polimi.ingsw.lobby;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.lobby.Lobby;

import java.util.ArrayList;


public class LobbyHandler extends Observable {


    //List of all active lobbies
    private ArrayList<Lobby> lobbyList;

    //List of all players
    private ArrayList<String> playerList;


    public LobbyHandler() {
        lobbyList = new ArrayList<Lobby>();
        playerList = new ArrayList<String>();
    }

    //Add a new player in the waiting room
    public void addPlayer(String name) {
        playerList.add(name);
        notify(playerList);
    }

    //Get() of lobbyList
    public ArrayList<Lobby> getLobbyList() {
        return lobbyList;
    }

    //Get() of playerList
    public ArrayList<String> getPlayerList() {
        return playerList;
    }


    //Check the presence of a lobby available to join
    public boolean checkAvailableLobby() {
        for(Lobby lobby : lobbyList) {
            if(!lobby.isFull()) {
                return true;
            }
        }
        return false;
    }


    //Creation of a new lobby and setting up groundwork for the players List
    public int newLobby(String name,int playerNumber) {

        Lobby newLobby = new Lobby();
        lobbyList.add(newLobby);
        newLobby.setLobbyID(lobbyList.indexOf(newLobby));

        //Set in the Lobby of the total players number
        newLobby.setLobbyPlayersNumber(playerNumber);

        ArrayList<String> temporaryList = new ArrayList<String>();
        //First player nickname added in position [0], unknown players coded as "0000"
        temporaryList.add(name);
        for (int i = 1; i < playerNumber; i++) {
            temporaryList.add("0000");
        }
        //copies temporaryList into the attribute of the associated Lobby object
        newLobby.setPlayersNameList(temporaryList);

        return newLobby.getLobbyID();
    }

}
