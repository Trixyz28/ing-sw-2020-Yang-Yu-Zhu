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
        canMoveUp = true;
        workerChanged = false;
        isArtemis = false;
        isDemeter = false;
        isPrometheus = false;
        isAthena = false;
    }

    private Turn currentTurn;
    private UndecoratedWorker chosenWorker;
    private View currentView;
    private boolean workerChanged;
    private boolean isArtemis;
    private boolean isDemeter;
    private boolean isPrometheus;
    private boolean isAthena;
    private boolean isHephaestus;
    private boolean canMoveUp;

    protected boolean isWorkerChanged(){
        return workerChanged;
    }

    protected boolean isArtemis(){
        return isArtemis;
    }

    protected boolean isDemeter(){
        return isDemeter;
    }

    protected boolean isPrometheus() {
        return isPrometheus;
    }

    protected boolean isHephaestus() {
        return isHephaestus;
    }

    protected boolean CanMoveUp(){
        return canMoveUp;
    }


    /* scelta worker + controllo tipo worker */
    public void setChosenWorker(int index){
        UndecoratedWorker worker = currentTurn.getCurrentPlayer().chooseWorker(index);

        //controllare se il Worker scelta possa fare la mossa o no
        if(worker.canMove(canMoveUp).size() != 0 || (chosenWorker instanceof Prometheus && !model.checkLoseBuild(worker))) {
            /* tile che può andarci != 0 oppure Prometheus canBuild*/
            chosenWorker = worker;
            if (chosenWorker instanceof Artemis){  /* move in più */
                isArtemis = true;
            }else if (chosenWorker instanceof Demeter){
                isDemeter = true;
            }else if (chosenWorker instanceof Prometheus){
                isPrometheus = true;
            }else if (chosenWorker instanceof Athena){
                isAthena = true;
                canMoveUp = true;  /* ripristinare canMoveUp al turno di Athena */
            }else if(chosenWorker instanceof Hephaestus){
                isHephaestus = true;
            }
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
        currentTurn.setFinalTile(chosenWorker.getPosition());
        if(model.checkWin()){
            //view.win(currentTurn.getCurrentPlayer());  /* il currentPlayer vince */
            currentView.showMessage("Hai vintoooooo!!!");
            model.gameOver();
        } else {
            if (isAthena) {  /* aggiornare canMoveUp */
                if (checkMoveUpAthena()) {
                    canMoveUp = false;  /* gli altri player non possono più salire di livello */
                }
            }
            if (isPrometheus) {
                isPrometheus = false; /* se Prometheus fa prima Build ripristianare */
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

    /* fine Turn -> aggiornare BuiltTile + nextTurn */
    public void endTurn(Operation build) {
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
        isArtemis = false;
        isDemeter = false;
        isPrometheus = false;
        isAthena = false;
        isHephaestus = false;
        currentView = views.get(currentTurn.getCurrentPlayer());
        //view.chooseWorker;  -> far scegliere al player il worker dalla view
        if (model.checkLoseMove(canMoveUp)) {
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

        currentTurn.setChosenWorker(chosenWorker);
        currentTurn.setInitialTile(chosenWorker.getPosition());
        currentTurn.setFinalTile(null);  //inizializzare Final e Built
        currentTurn.setBuiltTile(null);
        model.setWorkerChosen(true);
        model.showBoard();
        //view.move();  /* ->  passare alla scelta della mossa */
        if(isPrometheus) {
            if(!model.checkLoseBuild(currentTurn.getChosenWorker())) {  /* se Prometheus può Build mandare messaggio */
                model.sendMessage(Messages.Prometheus);  /* chiedere se fare move o build */
            }else{
                isPrometheus = false;
            }
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


    protected void movePrometheus() {  /* se "MOVE": Worker normale -> continuare */
        isPrometheus = false;
        startMove();
    }



    protected void moveArtemis(){  /* prima move Artemis, la seconda come move normale */
        if (isArtemis) {
            currentTurn.setFinalTile(chosenWorker.getPosition());
            if(model.checkWin()){
                //view.win(currentTurn.getCurrentPlayer());  /* il currentPlayer vince */
                currentView.showMessage("Hai vintoooooo!!!");
                model.gameOver();
            }else {
                currentTurn.setInitialTile(currentTurn.getFinalTile());
                if (chosenWorker.canMove(canMoveUp).size() != 0) {  /* se il worker può fare un'altra mossa */
                    model.sendMessage(Messages.Artemis);  /* chiedere al Player se vuole fare move in più */
                    isArtemis = false;
                }
            }
        }else{
            //currentView.showMessage("Move again");
            model.move();
        }
    }

    protected void buildDemeter(){  /* primo build Demeter, secondo normale build */
        if(isDemeter){
            model.sendMessage(Messages.Demeter);  /* chiedere al Player se vuole fare build in più */
            isDemeter = false;
        }else{
            currentView.showMessage("Build again");
            model.build();
        }
    }

    private boolean checkMoveUpAthena(){  /* se Athena sale di livello */
        return (currentTurn.getFinalTile().getBlockLevel() > currentTurn.getInitialTile().getBlockLevel());
    }



}