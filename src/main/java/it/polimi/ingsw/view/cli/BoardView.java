package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Tile;

import java.io.Serializable;


public class BoardView implements Serializable {

    private Tile[][] map;
    private WorkerView[] workerList;
    private int chosenWorkerID;


    public BoardView(Tile[][] map, WorkerView[] workerList, int chosenWorkerID) {
        this.map = map;
        this.workerList = workerList;
        this.chosenWorkerID = chosenWorkerID;
    }


    public Tile getTile(int row,int column) {
        return map[row][column];
    }

    public int getChosenWorkerID() {
        return chosenWorkerID;
    }

    public WorkerView[] getWorkerList() {
        return workerList;
    }

}
