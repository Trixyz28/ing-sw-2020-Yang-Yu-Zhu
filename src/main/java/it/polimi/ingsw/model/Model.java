package it.polimi.ingsw.model;

import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.model.God.Pan;


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
    private Board board;



    //Number of players in the match
    private int playersNumber;

    //GodList for the choice of the ispiring God for each player
    private GodList godsList = new GodList(playersNumber);  //ho cambiato l'ArrayList in classe GodList

    //Current turn
    private Turn currentTurn;

    //Current player ID
    private int currentPlayerID;


    //Constructor for Match class
    public void initialize(int playersNumber) {
        matchPlayersList = new ArrayList<>();  //inizializzare playerList con i playerName in parametri
        this.playersNumber = playersNumber;

        board = new Board();

    }

    public Board getBoard() {
        return board;
    }

    public void showBoard() {
        notify(board);
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

    public int getPlayersNumber() {
        return playersNumber;
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

    //print completeList
    public void showCompleteGodList(){
        notify(godsList.showComplete());
    }


   //Create the players
    public void addPlayer(Player player) {
        matchPlayersList.add(player);
        player.setChallenger(false);
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
        return board.getTile(row,column);
    }

    public void sendMessage(String message){
        notify(new GameMessage(currentTurn.getCurrentPlayer(), message));
    }

    public void place(){  /* type 0 = place */
        notify(new Operation(currentTurn.getCurrentPlayer(),0, -1, -1));
    }

    public void move(){  /* type 0 = move */
        notify(new Operation(currentTurn.getCurrentPlayer(),1, -1, -1));
    }

    public void build(){  /* type 0 = build */
        notify(new Operation(currentTurn.getCurrentPlayer(),2, -1,-1));
    }



    //metodi da implementare con il controller
    public boolean checkWin() {
        if (currentTurn.getInitialTile().getBlockLevel()==2 && currentTurn.getFinalTile().getBlockLevel()==3) {
            return true;
        }
        if(currentTurn.getChosenWorker() instanceof Pan){
            ((Pan) currentTurn.getChosenWorker()).panCheck(currentTurn.getFinalTile(), currentTurn.getInitialTile());
        }
        return false;
    }

    public boolean checkLose() {  /* se tutti i worker non hanno più tile da poter andare -> perde*/
        /*
        for (int i = 0; i < 2; i++) {
            if (currentTurn.getCurrentPlayer().chooseWorker(i).canMove().size() != 0) {
                return false;
            }
        }
        */
        return true;
    }
    public void gameOver() {

    }


}
