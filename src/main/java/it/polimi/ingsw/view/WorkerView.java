package it.polimi.ingsw.view;

import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.model.Tile;

import java.io.Serializable;
import java.util.List;


public class WorkerView implements Serializable,Cloneable {

    private boolean positionSet = false;

    private Tile position;
    private int state;
    private int belongToPlayer;
    private int workerID;

    private List<Tile> movableList;
    private List<Tile> buildableList;


    public WorkerView(UndecoratedWorker worker) {
        if(worker.getPosition()!=null) {
            this.positionSet = true;
            this.position = worker.getPosition();
            this.state = 0;
            this.belongToPlayer = worker.getBelongToPlayer();
        }
    }

    public boolean isPositionSet() {
        return positionSet;
    }

    public Tile getPosition() {
        return position;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Tile> getMovableList() {
        return movableList;
    }
    public void setMovableList(List<Tile> movableList) {
        this.movableList = movableList;
    }

    public List<Tile> getBuildableList() {
        return buildableList;
    }
    public void setBuildableList(List<Tile> buildableList) {
        this.buildableList = buildableList;
    }

    public int getBelongToPlayer() {
        return belongToPlayer;
    }

    public int getWorkerID() {
        return workerID;
    }

    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

}
