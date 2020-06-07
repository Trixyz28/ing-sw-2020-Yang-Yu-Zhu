package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.model.Tile;

import java.io.Serializable;
import java.util.List;


public class WorkerView implements Serializable {

    private boolean positionSet = false;

    private String color;
    private Tile position;
    private int state;

    private List<Tile> movableList;
    private List<Tile> buildableList;

    public WorkerView(UndecoratedWorker worker) {
        if(worker.getPosition()!=null) {
            positionSet = true;
            this.position = worker.getPosition();
            state = 0;
        }
    }

    public boolean isPositionSet() {
        return positionSet;
    }

    public Tile getPosition() {
        return position;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

}
