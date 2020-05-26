package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.Athena;
import it.polimi.ingsw.model.God.UndecoratedWorker;

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

    //True if god power is active
    private boolean godPower;

    //True if the chosen worker can move up in the current turn
    private boolean canMoveUp;


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

    //set() of the turn number
    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    /*
    //set() of the chosenWorker
    public void setChosenWorker(UndecoratedWorker chosenWorker) {
        this.chosenWorker = chosenWorker;
    }*/

    //get() of the chosenWorker
    public UndecoratedWorker getChosenWorker() {
        return chosenWorker;
    }

    //get() of canMoveUp
    public boolean canMoveUp(){
        return canMoveUp;
    }

    /*
    public boolean canUsePower(){
        return godPower;
    }

    public void useGodPower(){
        godPower = false;
    }*/

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
    public void setBuiltTile(Tile buildTile) {
        this.builtTile = buildTile;
    }

    public void checkMoveUpAthena(){  /* se Athena sale di livello */
        if(chosenWorker instanceof Athena) {
            if (getFinalTile().getBlockLevel() > getInitialTile().getBlockLevel()){
                canMoveUp = false;  /* !canMoveUp non possono più salire */
            }
        }
        /* athena non è salita di livello */
    }

    //After chose worker
    public void choseWorker(UndecoratedWorker chosenWorker){  /* aggiornare i tile */
        this.chosenWorker = chosenWorker;
        initialTile = chosenWorker.getPosition();
        finalTile = null;
        builtTile = null;
        resetCanMoveUp();
    }

    private void resetCanMoveUp() {  /* ripristinare canMoveUp se è Athena */
        if(chosenWorker instanceof Athena){
            canMoveUp = true;
        }
    }

    // methods that interacts with the turn controller for the turn succession,eventually for the chronobreak of the turns
    // i check di validità delle mosse sono chiamati dal moveController e dal buildController con la logica nella classe Tile
    //!! metodo per azzerare tutti i counter di tutti i worker all'inizio di ogni turno


}

