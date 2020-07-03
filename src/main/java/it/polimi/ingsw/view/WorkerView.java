package it.polimi.ingsw.view;

import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.model.Tile;

import java.io.Serializable;
import java.util.List;

/**
 * Class that implements the WorkerView of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class WorkerView implements Serializable {

    private boolean positionSet = false;

    private Tile position;
    private int state;
    private int belongToPlayer;
    private int workerID;

    private List<Tile> movableList;
    private List<Tile> buildableList;

    /**
     *Creates a <code>WorkerView</code> with the specified attributes.
     * @param worker Variable that represents the current worker with the WorkerView.
     */
    public WorkerView(UndecoratedWorker worker) {
        if(worker.getPosition()!=null) {
            this.positionSet = true;
            this.position = worker.getPosition();
            this.state = 0;
            this.belongToPlayer = worker.getBelongToPlayer();
        }
    }

    /**
     *Checks the position of the worker with the WorkerView.
     * @return A boolean: <code>true</code> if the position is set, otherwise <code>false</code>.
     */
    public boolean isPositionSet() {
        return positionSet;
    }

    /**
     *Gets the <code>Tile</code> of the worker with the WorkerView.
     * @return The <code>Tile</code> where the worker at issue is positioned.
     */
    public Tile getPosition() {
        return position;
    }

    /**
     *Gets the state of the turn of the worker with the WorkerView.
     * @return The state of the turn of the worker at issue.
     */
    public int getState() {
        return state;
    }

    /**
     *Sets the state of the turn of the worker with the WorkerView.
     * @param state The state that needs to be set for the worker at issue.
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     *Gets the list of tiles where the worker with the WorkerView can move to.
     * @return The current list of <code>Tile</code> objects where the worker at issue can be moved to.
     */
    public List<Tile> getMovableList() {
        return movableList;
    }

    /**
     *Sets the list of tiles where the worker with the WorkerView can move to.
     * @param movableList A list of <code>Tile</code> objects where the worker at issue can be moved to.
     */
    public void setMovableList(List<Tile> movableList) {
        this.movableList = movableList;
    }

    /**
     *Gets the list of tiles where the worker with the WorkerView can build on.
     * @return The current list of <code>Tile</code> objects where the worker at issue can build on.
     */
    public List<Tile> getBuildableList() {
        return buildableList;
    }

    /**
     *Sets the list of tiles where the worker with the WorkerView can build on.
     * @param buildableList A list of <code>Tile</code> objects where the worker at issue can build on.
     */
    public void setBuildableList(List<Tile> buildableList) {
        this.buildableList = buildableList;
    }

    /**
     *Gets the player which the worker with the WorkerView belongs to.
     * @return An integer that indicates the ID of the player at issue.
     */
    public int getBelongToPlayer() {
        return belongToPlayer;
    }

    /**
     *Gets the ID of the worker with the WorkerView.
     * @return An integer that indicates the ID of the worker at issue.
     */
    public int getWorkerID() {
        return workerID;
    }

    /**
     *Sets the ID of the worker with the WorkerView.
     * @param workerID variable that represents the integer that equals the ID of the worker at issue.
     */
    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

}
