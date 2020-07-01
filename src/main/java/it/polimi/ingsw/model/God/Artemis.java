package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;


/**
 * This is the worker for the God Artemis, decorated under WorkerDecorator abstract class.
 * <p></p>
 * Artemis' Worker may move one additional time, but not back to its initial space.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Artemis extends WorkerDecorator {

    /**
     * Creates a worker with Artemis' God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Artemis (UndecoratedWorker worker){
        super(worker);
    }

    private Tile originalTile = new Tile();
    //Counter to check the index of the move
    private int moveCounter = 0;

    /**
     * {@inheritDoc}
     * <P>
     * Additionally:
     * <p>
     * the worker bestowed with Artemis' God Powers can choose to move again after a "move" operation in the same turn but can't move
     * back to the original <code>Tile</code>.
     */
    @Override
    public boolean canMove(Tile t) {
        if(moveCounter == 1) {  /* second Move */
            if(t == originalTile){
                return false;
            }
        }
        return super.canMove(t);

    }

    /**
     * {@inheritDoc}
     * <P>
     * Additionally:
     * <p>
     * the worker bestowed with Artemis' God Powers can move again after a "move" operation in the same turn but can't move
     * back to the original <code>Tile</code>.
     */
    @Override
    public void move(Tile t) {
        if(moveCounter == 1){
            super.move(t);
        }
        if(moveCounter == 0){   /* first move */
            /* Save the initial position before the move */
            originalTile = getPosition();
            super.move(t);
            moveCounter++;
            for(Tile tile : getAdjacentTiles()){
                if(canMove(tile)){
                    /* activates godPower */
                    setGodPower(true);
                    break;
                }
            }
        }


    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Resets the counter to 0 before the "build" operation;
     */
    @Override
    public void nextState() {
        super.nextState();
        moveCounter = 0;
    }

}