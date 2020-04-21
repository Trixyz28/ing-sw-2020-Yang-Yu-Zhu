package it.polimi.ingsw.model;

public class Turn {

    //Current player
    private Player currentPlayer;

    //Number of the turn
    private int turnNumber;

    //Chosen worker by the current player
    private Worker chosenWorker;

    //Initial tile of the move
    private Tile initialTile;

    //Final tile of the move
    private Tile finalTile;

    //Tile where there it was built
    private Tile initialTile;


    //Turn constructor: il turno viene creato a inizio match con turn number 0 ed è gestito dal turnController
    public Turn(Player currentPlayer) {
        this.turnNumber = 0;
        this.currentPlayer = currentPlayer;
    }


    //get() of the turn number
    public int getTurnNumber() {

        return turnNumber;
    }

    //set() of the trun number
    public void setTurnNumber(int turnNumber) {

        this.turnNumber = turnNumber;
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
    public void setBuiltTile(Tile buildTile) {

        this.builtTile = buildTile;
    }

    // methods that interacts with the turn controller for the turn succession,eventually for the chronobreak of the turns
    // i check di validità delle mosse sono chiamati dal moveController e dal buildController con la logica nella classe Tile
}

