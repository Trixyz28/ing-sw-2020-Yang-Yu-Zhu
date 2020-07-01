package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

/**
 * This is the worker for the God Pan, decorated under WorkerDecorator abstract class.
 * <p></p>
 * Pan's workers also win if they move down two or more levels.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Pan extends WorkerDecorator {
    /**
     * Creates a worker with Pan's God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Pan (UndecoratedWorker worker){

        super(worker);
    }


    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * A worker under Pan can activate the Pan Win Condition: if he jumps down 2 levels or more.
     */
    @Override
    public boolean checkWin(Tile initialTile) {
        return super.checkWin(initialTile) || (getConditions().checkWinCondition(getPosition()) && panCheck(initialTile));
    }

    /**
     * Checks the Pan Win Condition.
     * @param jumpedFrom Variabile that indicates the original <code>Tile</code> from which the worker moved.
     * @return A boolean: <code>true</code> if the Pan Win Condition is met, otherwise <code>false</code>.
     */
    //T was the destination, jumpedFrom was the original tile; if blocklevel jumpedFrom - t >= 2 pan win
    private boolean panCheck(Tile jumpedFrom){
        return jumpedFrom.getBlockLevel() - getPosition().getBlockLevel() >= 2;
    }



}