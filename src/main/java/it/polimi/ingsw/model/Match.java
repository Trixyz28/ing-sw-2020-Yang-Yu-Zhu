package it.polimi.ingsw.model;

import it.polimi.ingsw.Observable;

import java.util.ArrayList;

public class Match extends Observable {

    public Match(int playersNumber) {
        this.playersNumber = playersNumber;
    }
    private int playersNumber;


    private Map map = new Map();





    private ArrayList<Player> playersList;


    private String challengerID;

    private String startingPlayerID;






    private ArrayList<String> godsList;

    private Turn currentTurn;


    private Match currentMatch;


    public Match getCurrentMatch() {
        return currentMatch;
    }




    //Create the players
    public void createPlayers() {

    }

    //Choose a Challenger from playersList in a random way
    public void randomChooseChallenger() {

    }






}
