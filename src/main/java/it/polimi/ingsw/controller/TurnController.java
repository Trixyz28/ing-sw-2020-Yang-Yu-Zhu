package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.*;

import java.util.ArrayList;

public class TurnController {

    private Model model;

    public TurnController(Model model) {
        this.model = model;
    }

    private Turn currentTurn;
    private UndecoratedWorker chosenWorker;


    /* scelta worker + controllo tipo worker */
    protected void setChosenWorker(int index){
        UndecoratedWorker worker = currentTurn.getCurrentPlayer().chooseWorker(index);

        //controllare se il Worker scelta possa fare la mossa o no
        if(currentTurn.movableList(worker).size() != 0 || worker.getGodPower()) {  //(chosenWorker instanceof Prometheus && !model.checkLoseBuild(worker))
            /* tile che può andarci != 0 oppure Prometheus canBuild*/
            chosenWorker = worker;
            choseWorker();

        }else {
            //view.chooseWorker; -> richiedere scelta
            model.sendMessage("Riprova con un altro");
            model.sendMessage(Messages.Worker);
        }

    }

    /* fine move -> aggiornare FinalTile + chackWin -> inizio Build */
    private void endMove() {
        currentTurn.setFinalTile(chosenWorker.getPosition());
        if(!model.checkWin()){
            if(chosenWorker.getGodPower()){
                currentTurn.setInitialTile(currentTurn.getFinalTile());/* se il worker può fare un'altra mossa */
                model.showBoard();
                model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());  /* chiedere al Player se vuole fare move in più */
            }else {
                nextState();
                /*
                chosenWorker.nextState();
                //view.build();  /* chiedere al player di Buildare

                if (model.checkLose()) {  /* check se il worker possa o no fare Build
                    checkGameOver();
                } else {
                    model.operation();
                }
                */
            }
        }

    }

    /* fine Turn -> aggiornare BuiltTile + nextTurn */
    private void endTurn() {
        model.setWorkerChosen(false);  /* per stampa Board in Client */
        model.showBoard();  /* mostrare mappa dopo build */
        /* ottenere le coordinate del Tile dalla Operation */
        //illustrare qualche messaggio sulla view
        model.sendMessage("Il tuo turno è terminato!");  /* mandare solo alla view*/
        nextTurn();
    }

    /* aggiornare Turn + ripristinare condizioni */
    protected void nextTurn() {
        currentTurn = model.getCurrentTurn();
        ArrayList<Player> playerList = model.getMatchPlayersList();
        /* nextTurnNumber */
        int index = model.getNextPlayerIndex();  //trovare indice del player successivo
        currentTurn.nextTurn(playerList.get(index));
        //view.chooseWorker;  -> far scegliere al player il worker dalla view
        if (model.checkLose()) {
            checkGameOver();
        } else {
            model.sendMessage("Ecco il tuo turno!\nScegli il worker con cui vuoi fare la mossa");
            model.sendMessage(Messages.Worker);  /* inviare richiesta worker */
            /* attesa scelta worker */
        }
    }

    /* scelto Worker -> inizio move */
    protected void choseWorker(){

        currentTurn.choseWorker(chosenWorker);
        model.setWorkerChosen(true);
        //view.move();  /* ->  passare alla scelta della mossa */
        if(chosenWorker.getGodPower()) { /* se Prometheus può Build mandare messaggio */
            model.showBoard();
            model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());  /* chiedere se fare move o build */
       }else {
            nextState();
            /*
            chosenWorker.nextState();
            model.operation();

             */
        }
    }
/*
    protected void startMove(){
        //currentView.showMessage("Move!!!");
        model.move();
        //System.out.println("Ho finito di notificare move");
    }
/*
    private void startBuild(){
        //currentView.showMessage("Builda!");
        model.build();

    }
*/
    protected void endOperation(){
        int type = currentTurn.getState();
        if(type == 1){
            endMove();
        }else if(type == 2){
            endBuild();
        }
    }

    private void endBuild(){
         /* primo build Demeter, secondo normale build */  /* Hephaestus build un blocco in più */
        if(chosenWorker.getGodPower()){
            model.showBoard();
            model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());  /* chiedere al Player se vuole fare build in più */
        }else{
            nextState();
            /*
            chosenWorker.nextState();
            if(chosenWorker.getState() == 1){  /* se deve fare Move
                model.showBoard();
                if(model.checkLose()) {
                    checkGameOver();
                }else {
                    model.operation();
                }
            }else {
                endTurn();
            }

             */
        }
    }

    private void nextState(){
        currentTurn.nextState();
        if (currentTurn.getState() == 0){
            endTurn();
        }else {
            model.showBoard();
            if(model.checkLose()) {
                checkGameOver();
            }else {
                model.operation();
            }
        }
    }

    protected void checkGameOver(){
        if(!model.isGameOver()){
            nextTurn();
        }
    }


}