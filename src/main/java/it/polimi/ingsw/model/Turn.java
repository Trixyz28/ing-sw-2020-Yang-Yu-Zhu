package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.UndecoratedWorker;

import java.util.ArrayList;
import java.util.List;
/**
 * Class that represents the turn.
 * <p></p>
 * In each turn a player have to do a "move" operation and a "build" operation at least once with one of his 2 workers.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
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

    //return state of Turn : 0 -> choosing worker, 1 -> moving, 2 -> building
    private int state;



    //Turn constructor: handled by turn controller, the turn flow start with the turn n.0

    /**
     *  Creates a <code>Turn</code> with the specified attributes.
     *  @param currentPlayer Variable that indicates the current player.
     */
    public Turn(Player currentPlayer) {
        this.turnNumber = 0;
        this.currentPlayer = currentPlayer;
    }

    //get() of the currentPlayer

    /**
     * Gets the player of the current turn.
     * @return A <code>Player</code> object associated to the relative player of the game.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    //set() of the currentPlayer

    /**
     * Sets the player of the current turn.
     * @param currentPlayer indicates the player to be set as current.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    //get() of the turn number

    /**
     * Gets the turn number.
     * @return An integer that represents the turn number.
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * Gets the state for particular Gods.
     * @return An integer used for special operation of particular God Powers.
     */
    public int getState(){
        /* change state for special Gods */
        if(chosenWorker != null && !chosenWorker.getGodPower() && chosenWorker.getState() <= 2) {
            state = chosenWorker.getState();
        }
        return state;
    }

    /**
     * Updates the turn to the next state.
     */
    public void nextState(){
        chosenWorker.nextState();
        state = chosenWorker.getState();
    }

    /**
     * Advances to the next turn
     * @param player Variable that represents the player of the next turn.
     */
    public void nextTurn(Player player){
        turnNumber++;
        state = 0;
        chosenWorker = null;
        setCurrentPlayer(player);
    }


    //get() of the chosenWorker

    /**
     * Gets the chosen worker of the state.
     * @return The worker that was chosen by the current player.
     */
    public UndecoratedWorker getChosenWorker() {
        return chosenWorker;
    }

    //get() of the initial tile

    /**
     * Gets the initial tile of the state.
     * @return A <code>Tile</code> object that represents the initial tile of eventual operations.
     */
    public Tile getInitialTile() {
        return initialTile;
    }

    //set() of the initial tile

    /**
     * Sets the initial tile of the state.
     * @param initialTile Variable that represents the initial tile of eventual operations.
     */
    public void setInitialTile(Tile initialTile) {
        this.initialTile = initialTile;
    }


    //get() of the final tile

    /**
     * Gets the final tile of the state.
     * @return A <code>Tile</code> object that represents the final tile of eventual operations.
     */
    public Tile getFinalTile() {
        return finalTile;
    }

    //set() of the final tile

    /**
     * Sets the final tile of the state.
     * @param finalTile Variable that represents the final tile of eventual operations.
     */
    public void setFinalTile(Tile finalTile) {
        this.finalTile = finalTile;
    }

    //get() worker movableTile

    /**
     * Gets a list of the tiles in which a worker can move to.
     * @param worker Variable that indicates the worker chosen for the "move" operation.
     * @return A list of tiles that indicates which tiles are possible for the worker to "move" to.
     */
    public List<Tile> movableList(UndecoratedWorker worker){
        List<Tile> movable = new ArrayList<>();
        for (Tile tile : worker.getAdjacentTiles()){
            if(worker.canMove(tile)){
                movable.add(tile);
            }
        }
        return movable;
    }

    //get() worker buildableTile

    /**
     * Gets a list of the tiles in which a worker can build on.
     * @param worker Variable that indicates the worker chosen for the "build" operation.
     * @return A list of tiles that indicates which tiles are possible for the worker to "build" on.
     */
    public List<Tile> buildableList(UndecoratedWorker worker) {
        List<Tile> buildable = new ArrayList<>();
        for (Tile tile : worker.getAdjacentTiles()){
            if(worker.canBuildBlock(tile) || worker.canBuildDome(tile)){
                buildable.add(tile);
            }
        }
        return buildable;
    }

    //After chose worker

    /**
     * Handles the new choice of a worker by the player, initializing the turn.
     * @param chosenWorker Variable that indicates the worker chosen by the current player.
     */
    public void choseWorker(UndecoratedWorker chosenWorker){
        this.chosenWorker = chosenWorker;
        /* update tiles */
        initialTile = chosenWorker.getPosition();
        finalTile = null;
    }

    //check lose conditions

    /**
     * Checks if a player cannot move both of his workers or build with the moved one so he loses.
     * @return A boolean: <code>true</code> if the losing condition is fulfilled, otherwise <code>false</code>.
     */
    public boolean checkLose(){
        if(getState() == 0){
            /* before choosing worker: if no worker can move to a tile-> player loses */
            for (UndecoratedWorker worker : currentPlayer.getWorkerList()) {
                if (!movableList(worker).isEmpty()) {
                    return false;
                }
            }
            return true;
        }else if(getState() == 1){
            /* before move */
            return movableList(chosenWorker).isEmpty();
        }else {
            /* if worker cant build a block or a dome */
            for(Tile t : chosenWorker.getAdjacentTiles()){
                if(chosenWorker.canBuildBlock(t) || chosenWorker.canBuildDome(t)){
                    return false;
                }
            }
            return true;
        }
    }


}

