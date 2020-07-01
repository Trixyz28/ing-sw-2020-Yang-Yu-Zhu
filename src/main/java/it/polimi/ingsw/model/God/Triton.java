package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
/**
 * This is the worker for the God Triton, decorated under WorkerDecorator abstract class.
 * <p></p>
 * Each time Triton's Worker moves into a perimeter space, it may immediately move again.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Triton extends WorkerDecorator {

    /**
     * Creates a worker with Triton's God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Triton(UndecoratedWorker worker) {
        super(worker);
    }


    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Triton's God Powers can move again after a "move" operation if he's in a perimeter <code>Tile</code>.
     */
    @Override
    public void move(Tile t) {
        super.move(t);
        if (t.perimeterTile()) {
            for(Tile tile : getAdjacentTiles()){
                if(canMove(tile)){
                    setGodPower(true);
                    break;
                }
            }
        }
    }

}


