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
    }

    private Turn currentTurn;
    private ArrayList<Player> playerList;
    private UndecoratedWorker chosenWorker;
    private View currentView;
    private boolean workerChanged = false;
    private boolean changed = false;
    private boolean isArtemis = false;
    private boolean isDemeter = false;
    private boolean isPrometheus = false;
    private boolean isAthena = false;
    private boolean canMoveUp;

    protected boolean isWorkerChanged(){
        return workerChanged;
    }

    protected void setChanged(){
        changed = true;
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

    protected void movePrometheus() {
        isPrometheus = false;
    }

    public void setChosenWorker(int index){
        UndecoratedWorker worker = currentTurn.getCurrentPlayer().chooseWorker(index);

        //controllare se il Worker scelta possa fare la mossa o no
        if(worker.canMove(currentTurn.getInitialTile()).size() != 0) {  /* tile che può ancarci != 0 */
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
            }
            workerChanged = true;
        }else {
            //view.chooseWorker; -> richiedere scelta
            currentView.showMessage("Riprova con un altro");
        }

    }

    protected boolean CanMoveUp(){
        return canMoveUp;
    }

    public void endMove() {
        //currentTurn.setFinalTile(chosenWorker.getCurrentPosition());
        if(model.checkWin()){
            //view.win(currentTurn.getCurrentPlayer());  /* il currentPlayer vince */
            currentView.showMessage("Hai vintoooooo!!!");
            //model.win();
        }
        if(isAthena){  /* aggiornare canMoveUp */
            if(checkMoveUpAthena()){
                canMoveUp = false;  /* gli altri player non possono più salire di livello */
            }
        }
        //view.build();  /* chiedere al player di Buildare */
        startBuild();

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
        workerChanged = false;  /* da ripristinare ad ogni inizio turno (per scelta worker) */
        changed = false;  /* ripristinare checkChange */
        isArtemis = false;
        isDemeter = false;
        isPrometheus = false;
        isAthena = false;
        currentView = views.get(currentTurn.getCurrentPlayer());
        //view.chooseWorker;  -> far scegliere al player il worker dalla view
        if(model.checkLose()){
            currentView.showMessage("Spiacenti! Non sei più in grado di fare mosse, hai perso!!!!");
            //model.lose();
        }
        currentView.showMessage("Scegli il worker che vuoi fare la mossa");
        /* attesa scelta worker */
        while(true){
            if(workerChanged){
                break;
            }
        }
        currentTurn.setChosenWorker(chosenWorker);
        //currentTurn.setInitialTile(chosenWorker.getCurrentPosition());
        currentTurn.setFinalTile(null);  //inizializzare Final e Built
        currentTurn.setBuiltTile(null);
        //view.move();  /* ->  passare alla scelta della mossa */
        startMove();
    }

    private void startMove(){
        if(isPrometheus) {
            model.sendMessage(Messages.Prometheus);  /* chiedere se fare move o build */
            waitChange();
        }
        currentView.showMessage("Move!!!");
        model.move();
        if (isArtemis) {
            waitChange();  /* fine prima move */
            //currentTurn.setFinalTile(chosenWorker.getCurrentPosition());
            if(model.checkWin()){
                //view.win(currentTurn.getCurrentPlayer());  /* il currentPlayer vince */
                currentView.showMessage("Hai vintoooooo!!!");
                //model.win();
            }
            currentTurn.setInitialTile(currentTurn.getFinalTile());
            if(chosenWorker.canMove(currentTurn.getInitialTile()).size() != 0) {  /* se il worker può fare un'altra mossa */
                model.sendMessage(Messages.Artemis);  /* chiedere al Player se vuole fare move in più */
            }
        }
    }

    private void startBuild(){
        currentView.showMessage("Builda!");
        model.build();
        if(isDemeter){
            waitChange();  /* fine primo build */
            changed = false;
            model.sendMessage(Messages.Demeter);  /* chiedere al Player se vuole fare build in più */
        }
    }

    private void waitChange(){
        while(true){
            if(changed){
                break;
            }
        }
        changed = false;
    }

    protected void moveArtemis(){  /* prima move fa uscire dal while la seconda come move normale */
        isArtemis = false;
        currentView.showMessage("Move again");
        model.move();
    }

    protected void buildDemeter(){  /* primo build fa uscire dalla while secondo normale build */
        isDemeter = false;
        currentView.showMessage("Build again");
        model.build();
    }

    private boolean checkMoveUpAthena(){  /* se Athena sale di livello */
        return (currentTurn.getFinalTile().getBlockLevel() > currentTurn.getInitialTile().getBlockLevel());
    }


}