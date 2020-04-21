package it.polimi.ingsw.model;

public class Tile {


    private int row;
    private int column;

    //Block level on the tile, default 0
    private int blockLevel;

    private boolean occupiedByWorker;
    private boolean domePresence;


    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }


    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }


    public int getBlockLevel() {
        return blockLevel;
    }
    public void setBlockLevel(int blockLevel) {
        this.blockLevel = blockLevel;
    }


    public boolean isOccupiedByWorker() {
        return occupiedByWorker;
    }
    public void setOccupiedByWorker(boolean occupiedByWorker) {
        this.occupiedByWorker = occupiedByWorker;
    }


    public boolean isDomePresence() {
        return domePresence;
    }
    public void setDomePresence(boolean domePresence) {
        this.domePresence = domePresence;
    }


    public boolean adjacentTile(Tile dest) {
        if(this.row - dest.row <=1 && this.row - dest.row >=-1
           && this.column - dest.column <=1 && this.column - dest.column >=-1) {
            return true;
        }

        return false;
    }

    public boolean availableToMove(Tile dest) {
        if(adjacentTile(dest) && !dest.domePresence && !dest.occupiedByWorker
                && dest.getBlockLevel()-this.getBlockLevel()<=1 ) {
            return true;
        }

        return false;
    }

    public boolean availableToBuild(Tile dest) {
        if(adjacentTile(dest) && !dest.domePresence && !dest.occupiedByWorker) {
            return true;
        }

        return false;
    }


    public void addBlockLevel() {
        blockLevel++;
    }


    public void blockToDome() {
        domePresence = true;
    }


    public void freeWorker() {
        occupiedByWorker = false;
    }

    public void setWorker() {
        occupiedByWorker = true;
    }

}
