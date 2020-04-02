package it.polimi.ingsw.model;

public class Lobbies {

    //Nickname chosen by the first player
    private String firstPlayerNickname;

    //Total player number
    private int playerNumber;

    public int getPlayerNumber() {
        return playerNumber;
    }

    //The first player chooses the total player number
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }


    private Lobby currentLobby;


    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby = currentLobby;
    }


    private void createLobby() {

    }


}
