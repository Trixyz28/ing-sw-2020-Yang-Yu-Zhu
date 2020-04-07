package it.polimi.ingsw.model;

public class Turn {

    public Turn(Player currentPlayer) {
        this.turnNumber = 0;
        this.currentPlayer = currentPlayer;
    }

    private Player currentPlayer;

    private int turnNumber;
    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    private Worker chosenWorker;



    private Tile initialTile;
    public Tile getInitialTile() {
        return initialTile;
    }
    public void setInitialTile(Tile initialTile) {
        this.initialTile = initialTile;
    }


    private Tile finalTile;
    public Tile getFinalTile() {
        return finalTile;
    }

    public void setFinalTile(Tile finalTile) {
        this.finalTile = finalTile;
    }

    private Tile builtTile;
    public Tile getBuiltTile() {
        return builtTile;
    }

    public void setBuiltTile(Tile buildTile) {
        this.builtTile = buildTile;
    }



}
