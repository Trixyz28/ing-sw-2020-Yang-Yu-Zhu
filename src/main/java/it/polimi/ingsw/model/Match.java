package it.polimi.ingsw.model;

import it.polimi.ingsw.Observable;

import java.util.ArrayList;
import java.util.Random;


public class Match extends Observable {

    //ID of the challenger
    private int challengerID;

    //ID of the starting player
    private int startingPlayerID;

    //ArrayList of the players
    private ArrayList<Player> matchPlayersList;

    //Map: 5x5 tiles
    private Map map = new Map();

    //GodList for the choice of the ispiring God for each player
    private GodList godsList = new GodList(playersNumber);  //ho cambiato l'ArrayList in classe GodList

    //Number of players in the match
    private final int playersNumber;

    //Current turn
    private Turn currentTurn;

    //Next playerID
    private int nextPlayerID;


    //Constructor for Match class
    public Match(int playersNumber, ArrayList<String> playersNameList) {

        matchPlayersList = new ArrayList<String>();  //inizializzare playerList con i playerName in parametro

        for(String s : playersNameList){
            Player p = createPlayers(s);
            matchPlayersList.add(p);
            p.setPlayerID(playersNameList.size());  //ID = la dimensione dell'ArrayList 1, 2, ... (indice = ID - 1)
        }

        this.playersNumber = playersNumber;
    }


    //get() of the arraylist made by Players
    public ArrayList<Player> getMatchPlayersList() {

        return matchPlayersList;
    }

    //get() of the challengerID
    public int getChallengerID() {

        return challengerID;
    }

    //Choose a Challenger from playersList in a random way
    public void randomChooseChallenger() {

        Random r = new Random();
        challengerID = (r.nextInt(playersNumber))+1;
    }

    //get() starting playerID
    public int getStartingPlayerID() {

        return startingPlayerID;
    }

    //set of the starting playerID
    public void setStartingPlayerID(int id){

        startingPlayerID = id;
    }

    //get() of the GodList
    public GodList getGodsList() {

        return godsList;
    }


   //Create the players
    public Player createPlayers(String playerName) {
        Player p = new Player();
        p.setPlayerNickname(playerName);
        return p;
    }

   //turn advancer nel turn controller/funzione che prosegue con la scelta del player successive, più  futura
    //implementazione oggetto chronobreak per salvare le partite

    //metodi da implementare con il controller
    public void checkWin() {

    }

    public void checkLose() {

    }

    public void gameOver() {

    }


}
