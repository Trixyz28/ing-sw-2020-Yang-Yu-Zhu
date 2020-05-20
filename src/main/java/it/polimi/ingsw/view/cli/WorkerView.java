package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.model.Tile;

import java.io.Serializable;
import java.util.List;


public class WorkerView implements Serializable {

    private boolean positionSet = false;
    private Tile position;
    private List<Tile> canMove;
    private List<Tile> canBuild;

    public WorkerView(UndecoratedWorker worker) {
        if(worker.getPosition()!=null) {
            positionSet = true;
            this.position = worker.getPosition();

        }
    }

    public boolean isPositionSet() {
        return positionSet;
    }

    public Tile getPosition() {
        return position;
    }

    public List<Tile> getCanMove() {
        return canMove;
    }

    public List<Tile> getCanBuild() {
        return canBuild;
    }

}
