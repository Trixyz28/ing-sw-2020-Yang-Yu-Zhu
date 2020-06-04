package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.*;

import java.util.ArrayList;

public class TurnController {

    private Model model;

    public TurnController(Model model) {
        this.model = model;
        workerChanged = false;
    }

    private Turn currentTurn;
    private UndecoratedWorker chosenWorker;
    private boolean workerChanged;

    protected boolean isWorkerChanged(){
        return workerChanged;
    }

    /* scelta worker + controllo tipo worker */
    public void setChosenWorker(int index){
        UndecoratedWorker worker = currentTurn.getCurrentPlayer().chooseWorker(index);

        //controllare se il Worker scelta possa fare la mossa o no
        if(worker.canMove().size() != 0 || chosenWorker.getGodPower()) {  //(chosenWorker instanceof Prometheus && !model.checkLoseBuild(worker))
            /* tile che può andarci != 0 oppure Prometheus canBuild*/
            chosenWorker = worker;
            workerChanged = true;
            choseWorker();

        }else {
            //view.chooseWorker; -> richiedere scelta
            model.sendMessage("Riprova con un altro");
            model.sendMessage(Messages.Worker);
        }

    }

    /* fine move -> aggiornare FinalTile + chackWin -> inizio Build */
    public void endMove() {
        currentTurn.setFinalTile(chosenWorker.getPosition());
        if(model.checkWin()){
            //view.win(currentTurn.getCurrentPlayer());  /* il currentPlayer vince */
            model.sendMessage("Hai vintoooooo!!!");
            model.gameOver();
        } else {
            if(chosenWorker.getGodPower()){
                currentTurn.setInitialTile(currentTurn.getFinalTile());/* se il worker può fare un'altra mossa */
                model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());  /* chiedere al Player se vuole fare move in più */
            }else {
                //view.build();  /* chiedere al player di Buildare */
                if (model.checkLoseBuild(currentTurn.getChosenWorker())) {  /* check se il worker possa o no fare Build */
                    checkGameOver();
                } else {
                    model.build();
                }
            }
        }
    }

    /* fine Turn -> aggiornare BuiltTile + nextTurn */
    public void endTurn(Operation build) {
        model.setWorkerChosen(false);  /* per stampa Board in Client */
        model.showBoard();  /* mostrare mappa dopo build */
        currentTurn.setBuiltTile(model.commandToTile(build.getRow(), build.getColumn()));  /* ottenere le coordinate del Tile dalla Operation */
        //illustrare qualche messaggio sulla view
        model.sendMessage("Il tuo turno è terminato!");  /* mandare solo alla view*/
        nextTurn();
    }

    /* aggiornare Turn + ripristinare condizioni */
    public void nextTurn() {
        currentTurn = model.getCurrentTurn();
        ArrayList<Player> playerList = model.getMatchPlayersList();
        int turnNumber = currentTurn.getTurnNumber() + 1;  /* nextTurnNumber */
        currentTurn.setTurnNumber(turnNumber);
        int index = model.getNextPlayerIndex();  //trovare indice del player successivo
        currentTurn.setCurrentPlayer(playerList.get(index));
        workerChanged = false;  /* da ripristinare ad ogni inizio turno (per scelta worker) */
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
            if(chosenWorker.canMove().size()!=0) {
                model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());  /* chiedere se fare move o build */
            }else{
                model.build();
            }
        }else {
            model.move();
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

    protected void endBuild(Operation build){
         /* primo build Demeter, secondo normale build */  /* Hephaestus build un blocco in più */
        if(chosenWorker.getGodPower()){
            model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());  /* chiedere al Player se vuole fare build in più */
        }else{
            endTurn(build);
        }
    }

    protected void checkGameOver(){
        if(!model.isGameOver()){
            nextTurn();
        }
    }


}