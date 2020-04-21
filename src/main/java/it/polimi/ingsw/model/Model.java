package it.polimi.ingsw.model;

import it.polimi.ingsw.Observable;

import java.util.ArrayList;
import java.util.Random;


public class Model extends Observable {


    private Map map = new Map();

    private int playersNumber;

    private ArrayList<Player> playersList;

    private int challengerID;

    private int startingPlayerID;

    private GodList godsList;  //ho cambiato l'ArrayList in classe GodList

    private Turn currentTurn;

    //private Model currentMatch;


    public void initialize(int playersNumber, ArrayList<String> playersNameList) {
        playersList = new ArrayList<>();
        for(String s : playersNameList){  //inizializzare playerList con i playerName in parametro
            Player p = createPlayers(s);
            playersList.add(p);
            p.setPlayerID(playersNameList.size());  //ID = la dimensione dell'ArrayList 1, 2, ... (indice = ID - 1)
        }
        this.playersNumber = playersNumber;
        godsList = new GodList(playersNumber);
    }

    public ArrayList<Player> getPlayersList() {
        return playersList;
    }

    public GodList getGodsList() { return godsList; }

    /*
    public Model getCurrentMatch() {
        return currentMatch;
    }
    */

    //Create the players
    public Player createPlayers(String playerName) {
        Player p = new Player();
        p.setPlayerNickname(playerName);  //settare i Nickname con i playerName nell'arrayList playersNameList
        return p;
    }

    public int getChallengerID() {
        return challengerID;
    }

    //Choose a Challenger from playersList in a random way
    public void randomChooseChallenger() {
        Random r = new Random();
        challengerID = (r.nextInt(playersNumber))+1;
        Player challenger = getPlayersList().get(challengerID-1); /* Challenger indice = ID - 1 */
        challenger.setChallenger();  /* challenger.isChallenger = true */

    }

    public int getStartingPlayerID() {
        return startingPlayerID;
    }

    public void setStartingPlayerID(int id){
        startingPlayerID = id;
    }

    //ancora da decidere (???
    public void startCurrentTurn(){
        currentTurn = new Turn(playersList.get(startingPlayerID));
    }

    //notificare la view per printare la lista completa
    public void showCompleteGodList(){
        notify(godsList.showComplete());  /* tipo parametro = String [] */
    }

    public void checkWin() {

    }

    public void checkLose() {

    }

    public void gameOver() {

    }


}
