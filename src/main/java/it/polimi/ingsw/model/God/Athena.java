package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;

/**
 * This is the worker for the God Athena, decorated under WorkerDecorator abstract class.
 * <p></p>
 * If one of Athena's Workers moved up on the last turn, opponent Workers cannot move up this turn.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Athena extends WorkerDecorator {

    /**
     * Creates a worker with Athena's God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Athena (UndecoratedWorker worker){
        super(worker);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Athena's God Power sets Athena God Rule to <code>True</code> if the worker moves up.
     */
    @Override
    public void move(Tile t){
        //if athena worker moved up then modify AthenaRule in class Conditions
        //normal move + if worker moves up: change AthenaRule to true.
        if(getPosition().getBlockLevel() < t.getBlockLevel()){
            getConditions().setAthenaRule(true);
        }
        super.move(t);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Athena's God Power sets Athena God Rule to <code>False</code> at the start of his turn.
     */
    @Override
    public boolean canMove(Tile t) {
        getConditions().setAthenaRule(false);
        return super.canMove(t);
    }


}


