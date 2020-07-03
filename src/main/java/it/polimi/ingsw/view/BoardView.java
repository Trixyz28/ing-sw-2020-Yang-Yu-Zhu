package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.io.Serializable;

/**
 * Class that implements the BoardView of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class BoardView implements Serializable {

    private final Tile[][] map;
    private final WorkerView[] workerList;
    private final int chosenWorkerID;

    private final String currentName;
    private final int currentID;
    private final String currentGod;

    /**
     * Creates a <code>BoardView</code> with the specified attributes.
     * @param map Variable that represents the map of the current game.
     * @param workerList Variable that represents worker list of the current game.
     * @param player Variable that represents the player of the current client.
     * @param chosenWorkerID Variable that represents the ID of the chosen worker of the current player.
     */
    public BoardView(Tile[][] map, WorkerView[] workerList, Player player, int chosenWorkerID) {
        this.map = map;
        this.workerList = workerList;
        this.chosenWorkerID = chosenWorkerID;
        this.currentName = player.getPlayerNickname();
        this.currentID = player.getPlayerID();
        this.currentGod = player.getGodCard();
    }

    /**
     *Gets a <code>Tile</code> of the BoardView.
     * @param row Variable that indicates the row of the <code>Tile</code> at issue.
     * @param column Variable that indicates the column of the <code>Tile</code> at issue.
     * @return A <code>Tile</code> of the BoardView with the parameters as coordinates.
     */
    public Tile getTile(int row,int column) {
        return map[row][column];
    }

    /**
     *Gets the chosen Worker from the BoardView.
     * @return An integer of the chosen worker.
     */
    public int getChosenWorkerID() {
        return chosenWorkerID;
    }

    /**
     *Gets the list of available workers of the BoardView.
     * @return An array of the available workersView.
     */
    public WorkerView[] getWorkerList() {
        return workerList;
    }

    /**
     *Gets the name of the current player that has the BoardView.
     * @return A string that represents the name of the current player.
     */
    public String getCurrentName() {
        return currentName;
    }

    /**
     **Gets the ID of the current player that has the BoardView.
     * @return An integer that represents the ID of the current player.
     */
    public int getCurrentID() {
        return currentID;
    }

    /**
     **Gets the God of the current player that has the BoardView.
     * @return A string that represents the God of the current player.
     */
    public String getCurrentGod() {
        return currentGod;
    }

}
