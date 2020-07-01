package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
/**
 * This is the worker for the God Hera, decorated under WorkerDecorator abstract class.
 * <p></p>
 * An opponent cannot win by moving into a perimeter space
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Hera extends WorkerDecorator{
    /**
     * Creates a worker with Hera's God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Hera (UndecoratedWorker worker){
        super(worker);
        getConditions().setHeraPlayerID(getBelongToPlayer());
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * Hera workers can win when moving up in a perimeter <code>Tile</code>, opponents'workers cannot.
     */
    @Override
    public boolean checkWin(Tile initialTile) {
        return (initialTile.getBlockLevel() == 2 && getPosition().getBlockLevel()==3);
    }
}
