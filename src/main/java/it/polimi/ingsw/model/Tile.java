package it.polimi.ingsw.model;

public class Tile {

    private String tileRow;

    private String tileColumn;

    //Block level on the tile, default 0
    private int blockLevel;

    private boolean occupiedByWorker;


    private boolean domePresence;
    public boolean isDomePresence() {
        return domePresence;
    }

    public void setDomePresence(boolean domePresence) {
        this.domePresence = domePresence;
    }





    public void getTilePosition(){


    }

    public void adjacentTile() {

    }

    public void availableToMove() {

    }

    public void availableToBuild() {

    }

    //Block++
    public int addBlockLevel() {

        return blockLevel;
    }

    //Build dome
    public void blockToDome() {

    }


    public void freeWorker() {

    }

    public void setWorker() {

    }


}
