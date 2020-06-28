package it.polimi.ingsw.lobby;

import java.util.ArrayList;


public class LobbyHandler {


    //List of all active lobbies
    private final ArrayList<Lobby> lobbyList;

    //List of all players
    private final ArrayList<String> playerList;

    private int counter = 0;

    public LobbyHandler() {
        this.lobbyList = new ArrayList<>();
        this.playerList = new ArrayList<>();
    }

    //Add a new player in the waiting room
    public void addPlayer(String name) {
        playerList.add(name);
    }


    public void removePlayer(String name) {
        playerList.remove(name);
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


    public Lobby findLobby(int index) {
        for(Lobby lobby : getLobbyList()) {
            if(lobby.getLobbyID() == index) {
                return lobby;
            }
        }
        return null;
    }

}
