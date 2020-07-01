package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.messages.Tags;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.*;

import java.util.ArrayList;

public class TurnController {

    private Model model;

    public TurnController(Model model) {
        this.model = model;
        currentTurn = model.getCurrentTurn();
    }

    private Turn currentTurn;
    private UndecoratedWorker chosenWorker;


    /* choose worker + check possibility to change to the other worker */
    protected void setChosenWorker(int index) {
        UndecoratedWorker worker = currentTurn.getCurrentPlayer().chooseWorker(index);

        //check if the chosen worker can move or not
        if (!currentTurn.movableList(worker).isEmpty()) {
            /* can move -> worker can be chosen */
            chosenWorker = worker;
            currentTurn.choseWorker(chosenWorker);
            model.setWorkerChosen(true);

            /* index of the other worker */
            index = index==0 ? 1 : 0;
            UndecoratedWorker unchosen = currentTurn.getCurrentPlayer().chooseWorker(index);

            /* check if the other worker can move */
            if(!currentTurn.movableList(unchosen).isEmpty()){
                /* possibility to change worker */
                model.setWorkerPending(true);
                model.sendBoard();
                model.sendMessage(Tags.G_MSG, Messages.confirmWorker);
            }else {
                /* choose the worker definitely */
                choseWorker();
            }
        }else {
            model.sendMessage(Tags.BOARD_MSG,Messages.anotherWorker);
            model.sendMessage(Tags.G_MSG,Messages.worker);
        }

    }

    /* Confirm Worker choice -> start move */
    protected void choseWorker(){

        model.setWorkerPending(false);
        model.sendMessage(Tags.BOARD_MSG,Messages.workerChose);
        /* godPower before move -> Prometheus */
        if(chosenWorker.getGodPower()) {
            model.sendBoard();
            model.sendMessage(Tags.G_MSG,currentTurn.getCurrentPlayer().getGodCard());
        }else {
            nextState();
        }
    }

    /* end of an operation */
    protected void endOperation(){
        int type = currentTurn.getState();
        if(type == 1){
            endMove();
        }else if(type == 2){
            endBuild();
        }
    }

    /* end of move operation -> update FinalTile + checkWin -> start Build */
    private void endMove() {
        currentTurn.setFinalTile(chosenWorker.getPosition());
        /* check win dopo move */
        if(!model.checkWin()){
            /* godPower before Build */
            if(chosenWorker.getGodPower()){
                /* the worker can move again */
                currentTurn.setInitialTile(currentTurn.getFinalTile());
                model.sendBoard();
                /* ask to the player to move again */
                model.sendMessage(Tags.G_MSG,currentTurn.getCurrentPlayer().getGodCard());
            }else {
                nextState();
            }
        }

    }

    /* end of build operation */
    private void endBuild(){
        /* godPower before end turn */
        if(chosenWorker.getGodPower()){
            model.sendBoard();
            /* the worker can build again -> ask to the player */
            model.sendMessage(Tags.G_MSG,currentTurn.getCurrentPlayer().getGodCard());
        }else{
            nextState();
        }
    }


    /* end Turn -> update board + nextTurn */
    private void endTurn() {

        model.setWorkerChosen(false);
        /* board after build */
        model.sendBoard();
        model.sendMessage(Tags.BOARD_MSG,Messages.endTurn);
        nextTurn();
    }


    /* next Turn */
    protected void nextTurn() {
        ArrayList<Player> playerList = model.getMatchPlayersList();
        int index = model.getNextPlayerIndex();
        currentTurn.nextTurn(playerList.get(index));
        model.sendBoard();
        /* check if the workers can move */
        if (model.checkLose()) {
            checkGameOver();
        } else {
            model.sendMessage(Tags.BOARD_MSG,Messages.yourTurn);
            /* send message to choose worker */
            model.sendMessage(Tags.G_MSG,Messages.worker);
        }
    }


    /* next state + operation of the new state */
    private void nextState(){
        currentTurn.nextState();
        if (currentTurn.getState() == 0){
            endTurn();
        }else {
            operation();
        }
    }

    /* check after lose */
    private void checkGameOver(){
        if(!model.isGameOver()){
            nextTurn();
        }
    }

    protected void operation() {
        model.sendBoard();
        /* checkLose before operation */
        if(model.checkLose()) {
            checkGameOver();
        }else {
            model.operation();
        }
    }


}