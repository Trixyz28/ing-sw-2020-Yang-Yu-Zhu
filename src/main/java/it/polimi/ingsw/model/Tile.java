package it.polimi.ingsw.model;

public class Tile {



    private int row;
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }



    private int column;
    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }



    //Block level on the tile, default 0
    private int blockLevel;
    public int getBlockLevel() {
        return blockLevel;
    }
    public void setBlockLevel(int blockLevel) {
        this.blockLevel = blockLevel;
    }



    private boolean occupiedByWorker;
    public boolean isOccupiedByWorker() {
        return occupiedByWorker;
    }
    public void setOccupiedByWorker(boolean occupiedByWorker) {
        this.occupiedByWorker = occupiedByWorker;
    }



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
