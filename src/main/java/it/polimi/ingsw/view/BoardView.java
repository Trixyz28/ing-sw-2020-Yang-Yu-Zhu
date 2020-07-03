package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.io.Serializable;


public class BoardView implements Serializable {

    private final Tile[][] map;
    private final WorkerView[] workerList;
    private final int chosenWorkerID;

    private final String currentName;
    private final int currentID;
    private final String currentGod;


    public BoardView(Tile[][] map, WorkerView[] workerList, Player player, int chosenWorkerID) {
        this.map = map;
        this.workerList = workerList;
        this.chosenWorkerID = chosenWorkerID;
        this.currentName = player.getPlayerNickname();
        this.currentID = player.getPlayerID();
        this.currentGod = player.getGodCard();
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

    public String getCurrentName() {
        return currentName;
    }

    public int getCurrentID() {
        return currentID;
    }

    public String getCurrentGod() {
        return currentGod;
    }

}
