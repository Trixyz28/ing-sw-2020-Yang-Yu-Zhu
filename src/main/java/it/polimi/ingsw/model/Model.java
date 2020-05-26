package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.model.God.Pan;
import it.polimi.ingsw.view.cli.BoardView;
import it.polimi.ingsw.view.cli.Colors;
import it.polimi.ingsw.view.cli.WorkerView;


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
    private GodList godsList;  //ho cambiato l'ArrayList in classe GodList

    //Current turn
    private Turn currentTurn;

    //Current player ID
    private int currentPlayerID;

    private boolean workerChosen;

    private boolean isGameOver = false;


    private ArrayList<UndecoratedWorker> totalWorkerList;

    //Constructor for Match class
    public void initialize(int playersNumber) {
        this.playersNumber = playersNumber;
        matchPlayersList = new ArrayList<>();  //inizializzare playerList con i playerName in parametri
        godsList = new GodList(playersNumber);
        workerChosen = false;
        totalWorkerList = new ArrayList<>();
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public void showBoard() {

        WorkerView[] totalWorkerView = new WorkerView[matchPlayersList.size()*2];
        int chosenWorkerID = -1;

        for(int i=0;i<matchPlayersList.size();i++) {
            for(int j=0;j<2;j++) {
                totalWorkerView[i*2+j] = new WorkerView(matchPlayersList.get(i).getWorkerList().get(j));
                totalWorkerView[i*2+j].setColor(workerColor(matchPlayersList.get(i).getPlayerID()));
                if(workerChosen) {
                    if (matchPlayersList.get(i).getWorkerList().get(j).equals(currentTurn.getChosenWorker())) {
                        chosenWorkerID = i*2+j;
                    }
                }
            }
        }

        notify(new BoardView(board.getMap(),totalWorkerView,chosenWorkerID));
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
        challengerID = (r.nextInt(playersNumber));
        matchPlayersList.get(challengerID).setChallenger(true);
        currentPlayerID = challengerID;
        notify("The chosen challenger is: " + matchPlayersList.get(challengerID).getPlayerNickname());
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
        notify(godsList.getCompleteGodList());
    }


   //Create the players
    public void addPlayer(Player player) {
        matchPlayersList.add(player);
        player.setChallenger(false);
    }

    public void showGodList(){
        notify(godsList.getCurrentGodList());
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

    public void sendMessage(String arg){
        String message = null;
        if(arg.equals("ARTEMIS")){
            message = Messages.Artemis;
        }else if(arg.equals("ATLAS")){
            message = Messages.Atlas;
        }else if(arg.equals("DEMETER")){
            message = Messages.Demeter;
        }else if(arg.equals("HEPHAESTUS")){
            message = Messages.Hephaestus;
        }else if(arg.equals("PROMETHEUS")){
            message = Messages.Prometheus;
        }else{
            message = arg;
        }
        notify(new GameMessage(currentTurn.getCurrentPlayer(), message));
    }

    public void place(){  /* type 0 = place */
        notify(new Operation(currentTurn.getCurrentPlayer(),0, -1, -1));
    }

    public void move(){  /* type 1 = move */
        notify(new Operation(currentTurn.getCurrentPlayer(),1, -1, -1));
    }

    public void build(){  /* type 2 = build */
        notify(new Operation(currentTurn.getCurrentPlayer(),2, -1,-1));
    }


    //metodi da implementare con il controller
    public boolean checkWin() {
        if (currentTurn.getInitialTile().getBlockLevel()==2 && currentTurn.getFinalTile().getBlockLevel()==3) {
            return true;
        }
        if(currentTurn.getChosenWorker() instanceof Pan){
            return ((Pan) currentTurn.getChosenWorker()).panCheck(currentTurn.getInitialTile());
        }
        return false;
    }

    public boolean checkLoseMove(boolean canMoveUp) {  /* se tutti i worker non hanno più tile da poter andare -> perde*/
        for (int i = 0; i < 2 ; i++) {
            UndecoratedWorker worker = currentTurn.getCurrentPlayer().chooseWorker(i);
            if (worker.canMove(canMoveUp).size() != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean checkLoseBuild(UndecoratedWorker worker){  /* se il worker non può né build Block né build Dome */
        for(Tile t : worker.getPosition().getAdjacentTiles()){
            if(worker.canBuildBlock(t) || worker.canBuildDome(t)){
                return false;
            }
        }
        return true;
    }

    public void lose(Player player){
        if(matchPlayersList.size() > 2) {
            player.deleteWorker();
            int index = matchPlayersList.indexOf(player) - 1;  /* precedente al currentPlayer (per nextTurn del turnController)*/
            if (index < 0) {
                index = matchPlayersList.size() - 1;
            }
            currentTurn.setCurrentPlayer(matchPlayersList.get(index));
            totalWorkerList.remove(player.chooseWorker(0));  /* aggioranare totalWorkerList */
            totalWorkerList.remove(player.chooseWorker(1));
            matchPlayersList.remove(player);
            notify("The player " + player.getPlayerNickname() + " loses");
            losingPlayer(player);
        } else {
            gameOver();
        }
    }



    public int getCurrentPlayerID() {
        return currentPlayerID;
    }

    public void setCurrentPlayerID(int currentPlayerID) {
        this.currentPlayerID = currentPlayerID;
    }

    public void updateCurrentPlayer() {
        if(currentPlayerID == playersNumber-1) {
            currentPlayerID = 0;
        } else {
            currentPlayerID = currentPlayerID+1;
        }
    }

    public boolean isWorkerChosen() {
        return workerChosen;
    }

    public void setWorkerChosen(boolean workerChosen) {
        this.workerChosen = workerChosen;
    }

    public void gameOver() {
        isGameOver = true;
        notify("The winner is " + currentTurn.getCurrentPlayer().getPlayerNickname() + "!");
        notify(Messages.gameOver);
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void losingPlayer(Player player) {
        notify(player);
    }

    public String workerColor(int playerID) {
        if(playerID==0) {
            return Colors.GREEN_BOLD;
        } else if(playerID==1) {
            return Colors.PURPLE_BOLD;
        } else {
            return Colors.CYAN_BOLD;
        }
    }

    public ArrayList<UndecoratedWorker> createTotalWorkerList() {

        for(int i=0;i<matchPlayersList.size();i++) {
            for(int j=0;j<2;j++) {
                totalWorkerList.add(matchPlayersList.get(i).getWorkerList().get(j));
            }
        }

        return totalWorkerList;
    }

    public ArrayList<UndecoratedWorker> getTotalWorkers() {
        return totalWorkerList;
    }


}
