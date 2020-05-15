package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.Map;

public class TurnController {

    private Model model;
    private Map<Player, View> views;

    public TurnController(Model model, Map views) {
        this.model = model;
        this.views = views;
    }

    private Turn currentTurn;
    private ArrayList<Player> playerList;
    private Worker chosenWorker;


    public void setChosenWorker(int index){
        Worker worker = currentTurn.getCurrentPlayer().chooseWorker(index);

        /*
        if(worker.canMove()) {    controllare se il Worker scelta possa fare la mossa o no
            chosenWorker = worker;
        }else {
            //view.chooseWorker; -> richiedere scelta
        }
        */
    }

    public void endMove() {
        currentTurn.setFinalTile(chosenWorker.getCurrentPosition(chosenWorker));
        if(checkWin()){
            //view.win(currentTurn.getCurrentPlayer());  /* il currentPlayer vince */
            views.get(currentTurn.getCurrentPlayer()).showMessage("Hai vintoooooo!!!");
        }
        //view.build();  /* chiedere al player di Buildare */
        views.get(currentTurn.getCurrentPlayer()).showMessage("Builda!");
    }

    public void endTurn(Operation build) {
        currentTurn.setBuiltTile(model.commandToTile(build.getRow(), build.getColumn()));  /* ottenere le coordinate del Tile dalla Operation */
        //illustrare qualche messaggio sulla view -> attesa end
        nextTurn();
    }

    public void nextTurn() {
        currentTurn = model.getCurrentTurn();
        playerList = model.getMatchPlayersList();
        int turnNumber = currentTurn.getTurnNumber() + 1;  /* nextTurnNumber */
        currentTurn.setTurnNumber(turnNumber);
        int index = model.getNextPlayerIndex();  //trovare indice del player successivo
        currentTurn.setCurrentPlayer(playerList.get(index));
        //view.chooseWorker;  -> far scegliere al player il worker dalla view
        currentTurn.setChosenWorker(chosenWorker);
        currentTurn.setInitialTile(chosenWorker.getCurrentPosition(chosenWorker));
        currentTurn.setFinalTile(null);  //inizializzare Final e Built
        currentTurn.setBuiltTile(null);
        //view.move();  /* ->  passare alla scelta della mossa */
        views.get(currentTurn.getCurrentPlayer()).showMessage("Move!!!");
    }

    private boolean checkWin(){
        /*
        return currentTurn.getChosenWorker().checkWinningMove();
        *   if(currentTurn.getInitialTile().getBlockLevel()==3 && currentTurn.getFinalTile().getBlockLevel()==0){
        *      return true;
        *  }
        *  return false;
        */
        return false;
    }

}