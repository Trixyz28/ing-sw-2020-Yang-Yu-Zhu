package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;

/**
 * This is the worker for the God Apollo, decorated under the WorkerDecorator abstract class.
 * <p></p>
 * Apollo's Worker may move into an opponent Worker’s space by forcing their Worker to the space the Apollo's Worker just vacated.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Apollo extends WorkerDecorator {

    //List of all the workers in the game
    private final List<UndecoratedWorker> totalWorkers;

    /**
     * Creates a worker with Apollo's God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     * @param totalWorkerList Variable that links all the workers in the game.
     */
    public Apollo (UndecoratedWorker worker, List<UndecoratedWorker> totalWorkerList){
        super(worker);
        totalWorkers = totalWorkerList;
    }


    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Apollo's God Powers can select to move to an opponent's <code>Tile</code> even if it's occupied:
     * the 2 workers will swap places.
     */
    @Override
    public boolean canMove(Tile t) {
        if(getPosition().isAdjacentTo(t) && !t.isDomePresence()
                && t.getBlockLevel() - getPosition().getBlockLevel()<=1 ) {
            if(getConditions().checkMoveCondition(getPosition(), t)) {
                if(t.isOccupiedByWorker()){
                    UndecoratedWorker opponent = t.getWorker(totalWorkers);
                    // if the worker is found in the destination tile
                    if(opponent.getBelongToPlayer() != getBelongToPlayer()){
                        return true;
                    }
                }else {
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
     * If there's an opponent worker in the destination <code>Tile</code> it is swapped with the Apollo's worker.
     */
    @Override
    public void move(Tile t) {

        if(!t.isOccupiedByWorker()){
            super.move(t);
        } else {
            // swap positions
            t.getWorker(totalWorkers).setPosition(getPosition());
            setPosition(t);
        }

    }

}
