package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.*;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.Map;

public class TurnController {

    private Model model;
    private Map<Player, View> views;

    public TurnController(Model model, Map views) {
        this.model = model;
        this.views = views;
        workerChanged = false;
    }

    private Turn currentTurn;
    private UndecoratedWorker chosenWorker;
    private View currentView;
    private boolean workerChanged;
    private int moveCounter;

    protected boolean isWorkerChanged(){
        return workerChanged;
    }

    protected boolean checkMoveCounter(){
        return moveCounter != 0;
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
            currentView.showMessage("Riprova con un altro");
            model.sendMessage(Messages.Worker);
        }

    }

    /* fine move -> aggiornare FinalTile + chackWin -> inizio Build */
    public void endMove() {
        moveCounter++;
        currentTurn.setFinalTile(chosenWorker.getPosition());
        if(model.checkWin()){
            //view.win(currentTurn.getCurrentPlayer());  /* il currentPlayer vince */
            currentView.showMessage("Hai vintoooooo!!!");
            model.gameOver();
        } else {
            if(chosenWorker.getGodPower()){
                currentTurn.setInitialTile(currentTurn.getFinalTile());/* se il worker può fare un'altra mossa */
                model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());  /* chiedere al Player se vuole fare move in più */
                chosenWorker.setGodPower(false);
            }else {
                /*
                if (isAthena) {  /* aggiornare canMoveUp
                    if (checkMoveUpAthena()) {
                        canMoveUp = false;  /* gli altri player non possono più salire di livello
                    }
                }
                //view.build();  /* chiedere al player di Buildare */
                if (model.checkLoseBuild(currentTurn.getChosenWorker())) {  /* check se il worker possa o no fare Build */
                    currentView.showMessage("Spiacenti! Non sei più in grado di buildare, hai perso!!!!");
                    currentView.setEndGame();  /* player finisce la partita */
                    model.lose(currentTurn.getCurrentPlayer());  /* lose */
                    if (!model.isGameOver()) {  // eliminare currentPlayer e continuare
                        nextTurn();
                    }
                } else {
                    startBuild();
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
        currentView.showMessage("Il tuo turno è terminato!");  /* mandare solo alla view*/
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
        moveCounter = 0;
        currentView = views.get(currentTurn.getCurrentPlayer());
        //view.chooseWorker;  -> far scegliere al player il worker dalla view
        if (model.checkLoseMove()) {
            currentView.showMessage("Spiacenti! Non sei più in grado di fare mosse, hai perso!!!!");
            currentView.setEndGame();  /* player finisce la partita */
            model.lose(currentTurn.getCurrentPlayer());  /* lose */
            if (!model.isGameOver()) {  // eliminare currentPlayer e continuare
                nextTurn();
            }
        } else {
            currentView.showMessage("Ecco il tuo turno!\nScegli il worker con cui vuoi fare la mossa");
            model.sendMessage(Messages.Worker);  /* inviare richiesta worker */
            /* attesa scelta worker */
        }
    }

    /* scelto Worker -> inizio move */
    protected void choseWorker(){

        /*
        currentTurn.setChosenWorker(chosenWorker);
        currentTurn.setInitialTile(chosenWorker.getPosition());
        currentTurn.setFinalTile(null);  //inizializzare Final e Built
        currentTurn.setBuiltTile(null);
         */
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
            chosenWorker.setGodPower(false);
        }else {
            startMove();
        }
    }

    protected void startMove(){
        //currentView.showMessage("Move!!!");
        model.move();
        //System.out.println("Ho finito di notificare move");
    }

    private void startBuild(){
        //currentView.showMessage("Builda!");
        model.build();

    }

    protected void endBuild(Operation build){
         /* primo build Demeter, secondo normale build */  /* Hephaestus build un blocco in più */
        if(chosenWorker.getGodPower()){
            if(! (chosenWorker instanceof Atlas)) {
                model.showBoard();  /* mostrare mappa dopo build */
            }
            model.sendMessage(currentTurn.getCurrentPlayer().getGodCard());  /* chiedere al Player se vuole fare build in più */
            chosenWorker.setGodPower(false);
        }else{
            endTurn(build);
        }
    }


    private boolean checkMoveUpAthena(){  /* se Athena sale di livello */
        return (currentTurn.getFinalTile().getBlockLevel() > currentTurn.getInitialTile().getBlockLevel());
    }



}