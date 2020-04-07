package it.polimi.ingsw.model;

import it.polimi.ingsw.Observable;

import java.util.ArrayList;
import java.util.Random;


public class Match extends Observable {

    public Match(int playersNumber, ArrayList<String> playersNameList) {
        playersList = new ArrayList<>();  //inizializzare playerList con i playerName in parametro
        for(String s : playersNameList){
            Player p = createPlayers(s);
            playersList.add(p);
            p.setPlayerID(playersNameList.size());  //ID = la dimensione dell'ArrayList 1, 2, ... (indice = ID - 1)
        }
        this.playersNumber = playersNumber;
    }

    private Map map = new Map();

    private final int playersNumber;

    private ArrayList<Player> playersList;
    public ArrayList<Player> getPlayersList() {
        return playersList;
    }



    private int challengerID;
    public int getChallengerID() {
        return challengerID;
    }
    //Choose a Challenger from playersList in a random way
    public void randomChooseChallenger() {
        Random r = new Random();
        challengerID = (r.nextInt(playersNumber))+1;
    }




    private int startingPlayerID;
    public int getStartingPlayerID() {
        return startingPlayerID;
    }
    public void setStartingPlayerID(int id){
        startingPlayerID = id;
    }


    private GodList godsList = new GodList(playersNumber);  //ho cambiato l'ArrayList in classe GodList

    public GodList getGodsList() { return godsList; }

    private Turn currentTurn;

    private Match currentMatch;

    public Match getCurrentMatch() {
        return currentMatch;
    }

    //Create the players
    public Player createPlayers(String playerName) {
        Player p = new Player();
        p.setPlayerNickname(playerName);
        return p;
    }




    //ancora da decidere (???
    public void startCurrentTurn(){
        currentTurn = new Turn(playersList.get(startingPlayerID));

    }





    public void checkWin() {

    }



    public void checkLose() {

    }

    public void gameOver() {

    }


}
