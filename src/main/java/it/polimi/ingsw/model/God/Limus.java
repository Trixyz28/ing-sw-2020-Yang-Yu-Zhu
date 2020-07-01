package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
/**
 * This is the worker for the God Limus, decorated under WorkerDecorator abstract class.
 * <p></p>
 * Opponent Workers cannot build on spaces neighboring your Workers, unless building a dome to create a Complete Tower.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Limus extends WorkerDecorator {

    /**
     * Creates a worker with Limus' God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Limus(UndecoratedWorker worker){
        super(worker);
        getConditions().addLimusWorker(this);
    }
    /**
    * {@inheritDoc}
    * <p></p>
    * Additionally:
    * <p>
    * Limus'worker can build on any available neighbouring space.
    */
    @Override
    public boolean canBuildBlock(Tile t) {
        return getPosition().availableToBuild(t) && t.getBlockLevel() < 3;
    }
}
