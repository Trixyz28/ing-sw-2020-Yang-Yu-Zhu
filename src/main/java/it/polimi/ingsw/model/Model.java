package it.polimi.ingsw.model;

import it.polimi.ingsw.Observable;

import java.util.ArrayList;
import java.util.Random;


public class Model extends Observable {

    //ID of the challenger
    private int challengerID;

    //ID of the starting player
    private int startingPlayerID;

    //ArrayList of the players
    private ArrayList<Player> matchPlayersList;



    //Map: 5x5 tiles
    private Map map = new Map();

    //Number of players in the match
    private int playersNumber;

    //GodList for the choice of the ispiring God for each player
    private GodList godsList = new GodList(playersNumber);  //ho cambiato l'ArrayList in classe GodList

    //Current turn
    private Turn currentTurn;

    //Next playerID
    private int nextPlayerIndex;


    //Constructor for Match class
    public void initialize(int playersNumber, ArrayList<String> playersNameList) {

        matchPlayersList = new ArrayList<Player>();  //inizializzare playerList con i playerName in parametro

        for(String s : playersNameList){
            Player p = createPlayers(s);
            matchPlayersList.add(p);
            p.setPlayerID(matchPlayersList.size()-1);  //ID = indice iniziale dei players
        }

        this.playersNumber = playersNumber;
    }

    public Map getMap() {
        return map;
    }

    //get the index of the nextPlayer
    public int getNextPlayerIndex(){
        int index = matchPlayersList.indexOf(currentTurn.getCurrentPlayer())+1;
        if(index == matchPlayersList.size()){
            return 0;
        }else{
            return index;
        }
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
        challengerID = (r.nextInt(playersNumber-1));
        matchPlayersList.get(challengerID).setChallenger(true);
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
        p.setChallenger(false);
        return p;
    }

    public void startCurrentTurn() {
        currentTurn = new Turn(matchPlayersList.get(startingPlayerID));
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    //turn advancer nel turn controller/funzione che prosegue con la scelta del player successive, più  futura
    //implementazione oggetto chronobreak per salvare le partite


    public Tile commandToTile(int row,int column) {
        return map.getTile(row,column);
    }



    //metodi da implementare con il controller
    public boolean checkWin() {
        if (currentTurn.getInitialTile().getBlockLevel()==2 && currentTurn.getFinalTile().getBlockLevel()==3) {
            return true;
        }
        return false;
    }

    public void checkLose() {

    }

    public void gameOver() {

    }


}
