package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.messages.TurnMessage;
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


    /* scelta worker + controllo tipo worker */
    protected void setChosenWorker(int index){
        UndecoratedWorker worker = currentTurn.getCurrentPlayer().chooseWorker(index);

        //controllare se il Worker scelta possa fare la mossa o no
        if(currentTurn.movableList(worker).size() != 0 || worker.getGodPower()) {
            /* tile che può andarci != 0 oppure Prometheus canBuild */
            chosenWorker = worker;
            choseWorker();

        }else {
            //view.chooseWorker; -> richiedere scelta
            model.sendMessage("Riprova con un altro");
            model.sendMessage(Messages.Worker);
        }

    }

    /* scelto Worker -> inizio move */
    protected void choseWorker(){

        currentTurn.choseWorker(chosenWorker);
        model.setWorkerChosen(true);
        /* godPower prima di move -> Prometheus */
        if(chosenWorker.getGodPower()) {
            model.showBoard();
            model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());
        }else {
            nextState();
        }
    }

    /* end generico operazioni */
    protected void endOperation(){
        int type = currentTurn.getState();
        if(type == 1){
            endMove();
        }else if(type == 2){
            endBuild();
        }
    }

    /* fine move -> aggiornare FinalTile + chackWin -> inizio Build */
    private void endMove() {
        currentTurn.setFinalTile(chosenWorker.getPosition());
        /* check win dopo move */
        if(!model.checkWin()){
            /* godPower prima di Build */
            if(chosenWorker.getGodPower()){
                /* se il worker può fare un'altra mossa */
                currentTurn.setInitialTile(currentTurn.getFinalTile());
                model.showBoard();
                /* chiedere al Player se vuole fare move in più */
                model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());
            }else {
                nextState();
            }
        }

    }

    /* fine build */
    private void endBuild(){
        /* godPower prima di end turn */
        if(chosenWorker.getGodPower()){
            model.showBoard();
            /* chiedere al Player se vuole fare build in più */
            model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());
        }else{
            nextState();
        }
    }


    /* fine Turn -> aggiornare board + nextTurn */
    private void endTurn() {
        /* per stampa Board in Client */
        model.setWorkerChosen(false);
        /* mostrare mappa dopo build */
        model.showBoard();
        /* mandare solo al currentPlayer */
        model.sendMessage("Il tuo turno è terminato!");
        nextTurn();
    }

    /* aggiornare Turn + ripristinare condizioni */
    protected void nextTurn() {
        ArrayList<Player> playerList = model.getMatchPlayersList();
        //trovare indice del player successivo
        int index = model.getNextPlayerIndex();
        currentTurn.nextTurn(playerList.get(index));
        model.notify(new TurnMessage("god", currentTurn.getCurrentPlayer().getPlayerNickname()));
        model.showBoard();
        if (model.checkLose()) {
            checkGameOver();
        } else {
            model.sendMessage("Ecco il tuo turno!");
            /* inviare richiesta worker */
            model.sendMessage(Messages.Worker);
            /* attesa scelta worker */
        }
    }


    /* passare stato prossimo */
    private void nextState(){
        currentTurn.nextState();
        if (currentTurn.getState() == 0){
            endTurn();
        }else {
            model.showBoard();
            /* checkLose prima dell'operazione */
            if(model.checkLose()) {
                checkGameOver();
            }else {
                model.operation();
            }
        }
    }

    /* check dopo lose */
    private void checkGameOver(){
        if(!model.isGameOver()){
            nextTurn();
        }
    }


}