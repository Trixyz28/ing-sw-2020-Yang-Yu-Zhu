package it.polimi.ingsw.model.God;

//interfaccia estesa da tutte le divinità decoratrici

import it.polimi.ingsw.model.Tile;

import java.util.List;
/**
 * This is the interface that implements the Decorator Pattern for Workers and God Powers.
 * <p></p>
 * The Decorator Pattern grants new behaviors to be added to an individual object
 * without affecting other objects from the same class.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public interface UndecoratedWorker {

    /**
     * Checks if the worker can move to a specific <code>Tile</code>.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     * @return A boolean: <code>true</code> if the "move" operation is available, otherwise <code>false</code>.
     */
    boolean canMove(Tile t);

    /**
     * Handles the "move" operation of the worker.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     */
    void move(Tile t);

    /**
     * Checks if the worker can build a block on a specific <code>Tile</code>.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     * @return A boolean: <code>true</code> if the "build" operation is available, otherwise <code>false</code>.
     */
    boolean canBuildBlock(Tile t);

    /**
     * Handles the "build block" operation of the worker.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     */
    void buildBlock(Tile t);

    /**
     * Checks if the worker can build a dome on a specific <code>Tile</code>.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     * @return A boolean: <code>true</code> if the "build dome" operation is available, otherwise <code>false</code>.
     */
    boolean canBuildDome(Tile t);

    /**
     * Handles the "build dome" operation of the worker.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     */
    void buildDome(Tile t);

    /**
     * Gets the position of the worker at issue.
     * @return The <code>Tile</code> where the worker is positioned upon.
     */
    Tile getPosition();

    /**
     * Sets the position of a worker in a specified <code>Tile</code>.
     * @param t The destination <code>Tile</code> where the worker must be put on.
     */
    void setPosition(Tile t);

    /**
     * Checks the GodPower of the worker associated.
     * @return A boolean: <code>true</code> if GodPower at issue is active,
     *                     otherwise <code>false</code>.
     */
    boolean getGodPower();

    /**
     * Sets the GodPower of the worker associated.
     * @param b A boolean: <code>true</code> if the GodPower at issue is active,
     *                     otherwise <code>false</code>.
     */
    void setGodPower(boolean b);

    /**
     * Gets the particular conditions of specific Gods.
     * @return The conditions class is where the info about particular conditions of specific Gods
     *          is stored.
     */
    Conditions getConditions();

    /**
     * Handles the use of the GodPower of specific Gods.
     * @param use A boolean: <code>true</code> if the worker activates a particular condition,
     *                        otherwise <code>false</code>.
     */
    void useGodPower(boolean use);

    /**
     * Checks which player the worker at issue is associated to.
     * @return An integer that represents the player ID which the worker is associated with.
     */
    int getBelongToPlayer();

    /**
     * Check if any winning condition is satisfied.
     * <p></p>
     * The main win condition is for the worker of the player to "move" from a level 2 block to a level 3 block.
     * <p></p>
     * @param initialTile Variable that represents the initial <code>Tile</code> of the "move" operation.
     * @return A boolean: <code>true</code> if any winning condition is satisfied,
     *                    otherwise <code>false</code>.
     */
    boolean checkWin(Tile initialTile);

    /**
     * Brings the worker to the next state in the turn.
     */
    void nextState();

    /**
     * Sets the worker to a particular state in the turn.
     * @param state Variable that represents the index of the state in the turn.
     *              <p>
     *              If index equals 0: waiting;
     *              <p>
     *              If index equals 1: moving;
     *              <p>
     *              If index equals 2: building;
     *              <p>
     *              If index equals 3: using another worker;
     *              <p>
     */
    void setState(int state);

    /**
     * Gets the state in the specified turn.
     * @return An integer that represents the number of the state in the turn.
     *         <p>
     *         If index equals 0: waiting;
     *         <p>
     *         If index equals 1: moving;
     *         <p>
     *         If index equals 2: building;
     *         <p>
     *         If index equals 3: using another worker;
     *         <p>
     */
    int getState();

    /**
     * Analyzes all the adjacent tiles near the position of the worker.
     * @return A list of all the adjacent tiles near the position of the worker.
     */
    List<Tile> getAdjacentTiles();

}