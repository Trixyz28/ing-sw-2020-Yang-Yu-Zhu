package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.UndecoratedWorker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Class that is used to represent the Tile of the Board.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Tile implements Cloneable, Serializable {

    //Row value and Column value
    private int row;
    private int column;

    //Block level on the tile, default 0 (max.3)
    private int blockLevel;

    //Worker presence on the tile
    private boolean occupiedByWorker;

    //Dome presence on the tile
    private boolean domePresence;

    //List of all tiles adjacent to the present one on the map
    private final List<Tile> adjacentTiles;

    /**
     *
     */
    public Tile() {
        setBlockLevel(0);
        setOccupiedByWorker(false);
        setDomePresence(false);
        adjacentTiles = new ArrayList<>();
    }


    //Row getter&setter

    /**
     *
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     *
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }


    //Column getter&setter

    /**
     *
     * @return
     */
    public int getColumn() {
        return column;
    }

    /**
     *
     * @param column
     */
    public void setColumn(int column) {
        this.column = column;
    }


    //Blocklevel getter&setter

    /**
     *
     * @return
     */
    public int getBlockLevel() {
        return blockLevel;
    }

    /**
     *
     * @param blockLevel
     */
    public void setBlockLevel(int blockLevel) {
        this.blockLevel = blockLevel;
    }


    //OccupiedByWorker getter&setter

    /**
     *
     * @return
     */
    public boolean isOccupiedByWorker() {
        return occupiedByWorker;
    }

    /**
     *
     * @param occupiedByWorker
     */
    public void setOccupiedByWorker(boolean occupiedByWorker) {
        this.occupiedByWorker = occupiedByWorker;
    }


    //DomePresence getter&setter

    /**
     *
     * @return
     */
    public boolean isDomePresence() {
        return domePresence;
    }

    /**
     *
     * @param domePresence
     */
    public void setDomePresence(boolean domePresence) {
        this.domePresence = domePresence;
    }


    //AdjacentTiles getter

    /**
     *
     * @return
     */
    public List<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }


    //Return true if the tile destination is adjacent to the present tile

    /**
     *
     * @param dest
     * @return
     */
    public boolean isAdjacentTo(Tile dest) {
        if(this.row - dest.row <=1 && this.row - dest.row >=-1
           && this.column - dest.column <=1 && this.column - dest.column >=-1
           && !(this.row == dest.row && this.column == dest.column)) {
            return true;
        }

        return false;
    }


    //Return true if it is possible to move from the present tile to the tile destination

    /**
     *
     * @param dest
     * @return
     */
    public boolean availableToMove(Tile dest) {
        return isAdjacentTo(dest) && !dest.domePresence && !dest.occupiedByWorker
                && dest.getBlockLevel()-this.getBlockLevel()<=1;
    }


    //Return true if it is possible to build on the tile destination

    /**
     *
     * @param dest
     * @return
     */
    public boolean availableToBuild(Tile dest) {
        return isAdjacentTo(dest) && !dest.domePresence && !dest.occupiedByWorker;
    }


    //Increment the block level (build a floor)

    /**
     *
     */
    public void addBlockLevel() {
        this.blockLevel++;
    }


    //Build a dome on the tile

    /**
     *
     */
    public void blockToDome() {
        this.setDomePresence(true);
    }


    //The tile is now occupied by a worker

    /**
     *
     */
    public void putWorker() {
        this.setOccupiedByWorker(true);
    }

    //The tile is now free

    /**
     *
     */
    public void freeWorker() {
        this.setOccupiedByWorker(false);
    }

    //Return true if it is perimeter Tile

    /**
     *
     * @return
     */
    public boolean perimeterTile(){
        return (getRow() == 0 || getRow() == 4 || getColumn() == 0 || getColumn() == 4);
    }

    //Return woker on this Tile

    /**
     *
     * @param totalWorkers
     * @return
     */
    public UndecoratedWorker getWorker(List<UndecoratedWorker> totalWorkers){
        for(UndecoratedWorker w : totalWorkers){
            if(this == w.getPosition()){
                /* trovato worker sulla tile */
                return w;
            }
        }
        return null;
    }
}
