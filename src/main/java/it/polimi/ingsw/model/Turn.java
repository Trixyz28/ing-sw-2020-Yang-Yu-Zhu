package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.UndecoratedWorker;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    //Current player
    private Player currentPlayer;

    //Number of the turn
    private int turnNumber;

    //Chosen worker by the current player
    private UndecoratedWorker chosenWorker;

    //Initial tile of the move
    private Tile initialTile;

    //Final tile of the move
    private Tile finalTile;

    //Tile where there it was built
    private Tile builtTile;

    //return state of Turn : 0 -> choosing worker, 1 -> moving, 2 -> building
    private int state;



    //Turn constructor: il turno viene creato a inizio match con turn number 0 ed è gestito dal turnController
    public Turn(Player currentPlayer) {
        this.turnNumber = 0;
        this.currentPlayer = currentPlayer;
    }

    //get() of the currentPlayer
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    //set() of the currentPlayer
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    //get() of the turn number
    public int getTurnNumber() {
        return turnNumber;
    }


    public int getState(){
        /* cambio di stato per God speciali */
        if(chosenWorker != null && !chosenWorker.getGodPower() && chosenWorker.getState() <= 2) {
            state = chosenWorker.getState();
        }
        return state;
    }

    public void nextState(){
        chosenWorker.nextState();
        state = chosenWorker.getState();
    }

    public void nextTurn(Player player){
        turnNumber++;
        state = 0;
        chosenWorker = null;
        setCurrentPlayer(player);
    }


    //get() of the chosenWorker
    public UndecoratedWorker getChosenWorker() {
        return chosenWorker;
    }

    //get() of the initial tile
    public Tile getInitialTile() {
        return initialTile;
    }

    //set() of the initial tile
    public void setInitialTile(Tile initialTile) {
        this.initialTile = initialTile;
    }


    //get() of the final tile
    public Tile getFinalTile() {
        return finalTile;
    }

    //set() of the final tile
    public void setFinalTile(Tile finalTile) {
        this.finalTile = finalTile;
    }

   //get() of the built tile
    public Tile getBuiltTile() {
        return builtTile;
    }

    //set() of the built tile
    public void setBuiltTile(Tile builtTile) {
        this.builtTile = builtTile;
    }

    //get() worker movableTile
    public List<Tile> movableList(UndecoratedWorker worker){
        List<Tile> movable = new ArrayList<>();
        for (Tile tile : worker.getPosition().getAdjacentTiles()){
            if(worker.canMove(tile)){
                movable.add(tile);
            }
        }
        return movable;
    }

    //get() worker buildableTile
    public List<Tile> buildableList(UndecoratedWorker worker) {
        List<Tile> buildable = new ArrayList<>();
        for (Tile tile : worker.getPosition().getAdjacentTiles()){
            if(worker.canBuildBlock(tile) || worker.canBuildDome(tile)){
                buildable.add(tile);
            }
        }
        Tile tile = worker.getPosition();
        if(worker.canBuildBlock(tile) || worker.canBuildDome(tile)){  /* Zeus */
            buildable.add(tile);
        }
        return buildable;
    }

    //After chose worker
    public void choseWorker(UndecoratedWorker chosenWorker){
        this.chosenWorker = chosenWorker;
        /* aggiornare i tile */
        initialTile = chosenWorker.getPosition();
        finalTile = null;
        builtTile = null;
    }

    //check lose conditions
    public boolean checkLose(){
        if(getState() == 0){
            /* before choosing worker: se tutti i worker non hanno più tile da poter andare -> perde */
            for (UndecoratedWorker worker : currentPlayer.getWorkerList()) {
                if (movableList(worker).size() != 0) {
                    return false;
                }
            }
            return true;
        }else if(getState() == 1){
            /* before move */
            return (movableList(chosenWorker).size() == 0);
        }else {
            /* se il worker non può né build Block né build Dome */
            for(Tile t : chosenWorker.getPosition().getAdjacentTiles()){
                if(chosenWorker.canBuildBlock(t) || chosenWorker.canBuildDome(t)){
                    return false;
                }
            }
            return true;
        }
    }



    // methods that interacts with the turn controller for the turn succession,eventually for the chronobreak of the turns
    // i check di validità delle mosse sono chiamati dal moveController e dal buildController con la logica nella classe Tile
    //!! metodo per azzerare tutti i counter di tutti i worker all'inizio di ogni turno


}

