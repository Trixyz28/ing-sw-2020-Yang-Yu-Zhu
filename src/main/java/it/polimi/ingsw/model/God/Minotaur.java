package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;

/**
 * This is the worker for the God Minotaur, decorated under WorkerDecorator abstract class.
 * <p></p>
 * Minotaur's Worker may move into an opponent Worker’s space,
 * if their Worker can be forced one space straight backwards to an unoccupied space at any level.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Minotaur extends WorkerDecorator {

    private final List<UndecoratedWorker> totalWorkers;
    /**
     * Creates a worker with Minotaur's God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     * @param totalWorkerList Variable that presents all the workers in the game.
     */
    public Minotaur (UndecoratedWorker worker, List<UndecoratedWorker> totalWorkerList){
        super(worker);
        totalWorkers = totalWorkerList;
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Minotaur's God Powers can select to move to an opponent's <code>Tile</code> if
     * the opponent worker can be forced straight backwards one space in an unoccupied space at any level.
     */
    @Override
    public boolean canMove(Tile t) {
        if(getPosition().isAdjacentTo(t) && !t.isDomePresence() && t.getBlockLevel()-getPosition().getBlockLevel()<=1) {
            if(getConditions().checkMoveCondition(getPosition(), t)) {
                if(t.isOccupiedByWorker()){
                    UndecoratedWorker opponent = t.getWorker(totalWorkers);
                    /* found a worker on tile*/
                    if(opponent.getBelongToPlayer() != getBelongToPlayer()){
                        Tile forcedTile = getForcedTile(t);
                        /* push conditions */
                        if(forcedTile != null && !forcedTile.isDomePresence() && !forcedTile.isOccupiedByWorker()){
                            /* add only if forcedTile is free */
                            return true;
                        }
                    }
                }else{
                    /* add free tiles */
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * If there's an opponent worker in the destination <code>Tile</code> and if
     * the opponent worker can be forced straight backwards one space in an unoccupied space at any level, it will be.
     */
    @Override
    public void move(Tile t) {
        if(t.isOccupiedByWorker()){
            /* change opponents' position */
            t.getWorker(totalWorkers).setPosition(getForcedTile(t));
        }
        super.move(t);
    }

    /**
     * Checks which <code>Tile</code> the opponent worker (which
     * can be forced straight backwards one space in an unoccupied space at any level) would finish in.
     * @param destination Variable that indicates the <code>Tile</code> which is the destination of the Minotaur worker.
     * @return The <code>Tile</code> where the opponent worker is forced to, if he can't be forced returns <code>Null</code>.
     */
    private Tile getForcedTile(Tile destination){
        int forcedRow = destination.getRow()+(destination.getRow()-getPosition().getRow());
        int forcedColumn = destination.getColumn()+(destination.getColumn()-getPosition().getColumn());
        if(forcedRow>=0 && forcedColumn >=0 && forcedRow <5 && forcedColumn <5) {
            for(Tile t : destination.getAdjacentTiles()){  /* find the tile */
                if(t.getRow() == forcedRow && t.getColumn() == forcedColumn){
                    return t;  /* forced tile */
                }
            }
        }
        return null;
    }

}
