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
        if(worker.canMove().size() != 0 || chosenWorker.getGodPower()) {  //(chosenWorker instanceof Prometheus && !model.checkLoseBuild(worker))
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
                model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());  /* chiedere al Player se vuole fare move in più */
            }else {
                //view.build();  /* chiedere al player di Buildare */
                if (model.checkLoseBuild(currentTurn.getChosenWorker())) {  /* check se il worker possa o no fare Build */
                    checkGameOver();
                } else {
                    chosenWorker.nextState();
                    model.operation();
                }
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
        int turnNumber = currentTurn.getTurnNumber() + 1;  /* nextTurnNumber */
        currentTurn.setTurnNumber(turnNumber);
        int index = model.getNextPlayerIndex();  //trovare indice del player successivo
        currentTurn.setCurrentPlayer(playerList.get(index));
        //view.chooseWorker;  -> far scegliere al player il worker dalla view
        if (model.checkLoseMove()) {
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
        model.showBoard();
        //view.move();  /* ->  passare alla scelta della mossa */
        if(chosenWorker.getGodPower()) { /* se Prometheus può Build mandare messaggio */
            model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());  /* chiedere se fare move o build */
       }else {
            chosenWorker.nextState();
            model.operation();
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
        int type = chosenWorker.getState();
        if(type == 1){
            endMove();
        }else if(type == 2){
            endBuild();
        }
    }

    private void endBuild(){
         /* primo build Demeter, secondo normale build */  /* Hephaestus build un blocco in più */
        if(chosenWorker.getGodPower()){
            model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());  /* chiedere al Player se vuole fare build in più */
        }else{
            chosenWorker.nextState();
            if(chosenWorker.getState() == 1){  /* se deve fare Move */
                if(chosenWorker.canMove().size() !=0 ) {
                    model.showBoard();
                    model.operation();
                }else {
                    model.lose(model.getCurrentTurn().getCurrentPlayer());
                    checkGameOver();
                }
            }else {
                endTurn();
            }
        }
    }

    protected void checkGameOver(){
        if(!model.isGameOver()){
            nextTurn();
        }
    }


}