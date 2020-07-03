package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.UndecoratedWorker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Class that is used to represent the single Tile of the Board.
 * <p></p>
 * Each Tile can host only one worker. Any worker can build on a Tile except on particular conditions set by a God.
 * <p>
 * On a single Tile can be built : level-1 block, level-2 block, level-3 block or a dome. Level-0  is ground level.
 * <p>
 * All the Tiles start level-0 at the start of the game.
 * <p></p>
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
    private List<Tile> adjacentTiles;

    /**
     * Creates a <code>Tile</code> with the specified attributes.
     */
    public Tile() {
        setBlockLevel(0);
        setOccupiedByWorker(false);
        setDomePresence(false);
        adjacentTiles = new ArrayList<>();
    }


    //Row getter&setter

    /**
     *Gets the row value of the selected <code>Tile</code>.
     * @return An integer that represents the row value of the selected <code>Tile</code>.
     */
    public int getRow() {
        return row;
    }

    /**
     *Sets the row value of the selected <code>Tile</code>.
     * @param row Variable that represents the row value that needs to be set.
     */
    public void setRow(int row) {
        this.row = row;
    }


    //Column getter&setter

    /**
     *Gets the column value of the selected <code>Tile</code>.
     * @return An integer that represents the column value of the selected <code>Tile</code>.
     */
    public int getColumn() {
        return column;
    }

    /**
     *Sets the column value of the selected <code>Tile</code>.
     * @param column Variable that represents the column value that needs to be set.
     */
    public void setColumn(int column) {
        this.column = column;
    }


    //Blocklevel getter&setter

    /**
     *Gets the block level value of the selected <code>Tile</code>.
     * @return An integer that represents the block level value of the selected <code>Tile</code>.
     */
    public int getBlockLevel() {
        return blockLevel;
    }

    /**
     *Sets the block level value of the selected <code>Tile</code>.
     * @param blockLevel Variable that represents the block level value that needs to be set.
     */
    public void setBlockLevel(int blockLevel) {
        this.blockLevel = blockLevel;
    }


    //OccupiedByWorker getter&setter

    /**
     *Checks if the selected <code>Tile</code> is occupied or not.
     * @return A boolean: <code>true</code> if the <code>Tile</code>is occupied, otherwise <code>false</code>.
     */
    public boolean isOccupiedByWorker() {
        return occupiedByWorker;
    }

    /**
     *Sets the selected <code>Tile</code> as or occupied or not occupied.
     * @param occupiedByWorker Variable that is a boolean.
     */
    public void setOccupiedByWorker(boolean occupiedByWorker) {
        this.occupiedByWorker = occupiedByWorker;
    }


    //DomePresence getter&setter

    /**
     *Checks if the selected <code>Tile</code> has a dome or not.
     * @return A boolean: <code>true</code> if the <code>Tile</code> has a dome, otherwise <code>false</code>.
     */
    public boolean isDomePresence() {
        return domePresence;
    }

    /**
     *Sets the selected <code>Tile</code> with an eventual dome.
     * @param domePresence Variable that is a boolean.
     */
    public void setDomePresence(boolean domePresence) {
        this.domePresence = domePresence;
    }


    //AdjacentTiles getter

    /**
     *Gets a list of all the adjacent <code>Tile</code> objects to the selected <code>Tile</code>.
     * @return A list of tiles that are all adjacent to the current <code>Tile</code>.
     */
    public List<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }


    //Return true if the tile destination is adjacent to the present tile

    /**
     *Checks if a parameter <code>Tile</code> is adjacent to the current one.
     * @param dest Variable that represents the destination <code>Tile</code>.
     * @return A boolean: <code>true</code> if the destination <code>Tile</code> is adjacent, otherwise <code>false</code>.
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
     *Checks if it's possible for a worker of a player to "move" to the parameter <code>Tile</code>.
     * @param dest Variable that represents the destination <code>Tile</code>.
     * @return A boolean: <code>true</code> if the "move" operation is possible, otherwise <code>false</code>.
     */
    public boolean availableToMove(Tile dest) {
        return isAdjacentTo(dest) && !dest.domePresence && !dest.occupiedByWorker
                && dest.getBlockLevel()-this.getBlockLevel()<=1;
    }


    //Return true if it is possible to build on the tile destination

    /**
     *Checks if it's possible for a worker of a player to "build" in the parameter <code>Tile</code>.
     * @param dest Variable that represents the destination <code>Tile</code>.
     * @return A boolean: <code>true</code> if the "build" operation is possible, otherwise <code>false</code>.
     */
    public boolean availableToBuild(Tile dest) {
        return isAdjacentTo(dest) && !dest.domePresence && !dest.occupiedByWorker;
    }


    //Increment the block level (build a floor)

    /**
     *Adds 1 to the block level variable of the tile after a worker has built a block on the <code>Tile</code>.
     */
    public void addBlockLevel() {
        this.blockLevel++;
    }


    //Build a dome on the tile

    /**
     *Changes to <code>True</code> the variable DomePresence after a worker has built a dome on the <code>Tile</code>.
     */
    public void blockToDome() {
        this.setDomePresence(true);
    }


    //The tile is now occupied by a worker

    /**
     *Changes to <code>True</code> the variable OccupiedByWorker after a worker has moved on the <code>Tile</code>.
     */
    public void putWorker() {
        this.setOccupiedByWorker(true);
    }

    //The tile is now free

    /**
     *Changes to <code>False</code> the variable OccupiedByWorker after a worker has moved out the <code>Tile</code>.
     */
    public void freeWorker() {
        this.setOccupiedByWorker(false);
    }

    //Return true if it is perimeter Tile

    /**
     *Checks if the current <code>Tile</code> is a perimeter tile.
     * @return A boolean: <code>true</code> if it's a perimeter tile, otherwise <code>false</code>.
     */
    public boolean perimeterTile(){
        return (getRow() == 0 || getRow() == 4 || getColumn() == 0 || getColumn() == 4);
    }

    //Return woker on this Tile

    /**
     *Checks the worker that is currently situated on the current <code>Tile</code>.
     * @param totalWorkers Variable that represents a list of all the workers in the game.
     * @return The worker that is situated in the current <code>Tile</code>, otherwise <code>Null</code>.
     */
    public UndecoratedWorker getWorker(List<UndecoratedWorker> totalWorkers){
        for(UndecoratedWorker w : totalWorkers){
            if(this == w.getPosition()){
                /* found worker on current tile*/
                return w;
            }
        }
        return null;
    }
}
