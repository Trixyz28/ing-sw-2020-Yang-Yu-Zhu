package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the worker for the God Zeus, decorated under WorkerDecorator abstract class.
 * <p></p>
 * Zeus' Worker may build a block under itself.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Zeus extends WorkerDecorator{
    /**
     * Creates a worker with Zeus' God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Zeus (UndecoratedWorker worker){
        super(worker);
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Zeus' God Powers can choose to build a block under himself.
     */
    @Override
    public boolean canBuildBlock(Tile t) {
        return (getPosition() == t && getConditions().checkBuildCondition(t) && t.getBlockLevel() < 3 || super.canBuildBlock(t));
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * Zeus' god can build block under himself.
     */
    @Override
    public List<Tile> getAdjacentTiles() {
        List<Tile> adjacentTiles = new ArrayList<>();
        adjacentTiles.addAll(super.getAdjacentTiles());
        adjacentTiles.add(worker.getPosition());
        return adjacentTiles;
    }
}
