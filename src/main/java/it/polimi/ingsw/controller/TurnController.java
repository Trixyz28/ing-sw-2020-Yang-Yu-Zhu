package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.messages.Tags;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that handles the turns of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class TurnController {

    private final Model model;

    /**
     *Creates a <code>TurnController</code> with the specified attributes.
     * @param model
     */
    public TurnController(Model model) {
        this.model = model;
        currentTurn = model.getCurrentTurn();
    }

    private Turn currentTurn;
    private UndecoratedWorker chosenWorker;


    /* choose worker + check possibility to change to the other worker */
    /**
     *Handles which <code>worker</code> is chosen for the turn.
     * @param index Variable that represents which worker is at issue.
     */
    /* scelta worker + controllo tipo worker */
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

    /**
     *Handles the choice of the worker until the next state.
     */
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

    /**
     *Differentiates the end of the different types of actions.
     */
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
    /**
     *Handles the end of the <worker>move</worker> action.
     */
    /* fine build */
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
    /**
     *Handles the end of the current turn.
     */
    /* fine Turn -> aggiornare board + nextTurn */
    private void endTurn() {

        model.setWorkerChosen(false);
        /* board after build */
        model.sendBoard();
        model.sendMessage(Tags.BOARD_MSG,Messages.endTurn);
        nextTurn();
    }


    /* next Turn */
    /**
     *Handles the start of the next turn restoring the board methods.
     */
    /* aggiornare Turn + ripristinare condizioni */
    protected void nextTurn() {
        List<Player> playerList = model.getMatchPlayersList();
        int index = model.getNextPlayerIndex();
        currentTurn.nextTurn(playerList.get(index));
        model.sendBoard();
        /* check if the workers can move */
        if (model.checkLose()) {
            checkGameOver();
        } else {
            model.sendMessage(Tags.GENERIC,Messages.yourTurn);
            /* send message to choose worker */
            model.sendMessage(Tags.G_MSG,Messages.worker);
        }
    }

    /**
     *Brings the state to the next one in the turn.
     */
    /* passare stato prossimo */

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
    /**
     *Checks if the game is over.If not brings the game to the next turn.
     */
    /* check dopo lose */
    private void checkGameOver(){
        if(!model.isGameOver()){
            nextTurn();
        }
    }

    /**
     *Applies the operation checking the game state for losing/winning conditions.
     */
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