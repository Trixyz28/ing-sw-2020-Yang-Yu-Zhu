package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;


/**
 * This is the worker for the God Atlas, decorated under WorkerDecorator abstract class.
 * <p></p>
 * Atlas' Worker may build a dome at any level.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Atlas extends WorkerDecorator {

    private Tile buildTile;

    private boolean used;

    /**
     * Creates a worker with Atlas' God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Atlas (UndecoratedWorker worker){
        super(worker);
    }


    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Atlas's God Powers can build a dome on any level.
     */
    @Override
    public boolean canBuildDome(Tile t) {
        if(used && getGodPower()){
            return t == buildTile;
        }
        if(getPosition().isAdjacentTo(t) && !t.isDomePresence() && !t.isOccupiedByWorker()) {
            if(getConditions().checkBuildCondition(t)) {
                return true;
            }else {
                return super.canBuildDome(t);
            }
        }
        return false;
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Atlas' God Powers can build a block.
     * Used for the Ui.
     */
    @Override
    public boolean canBuildBlock(Tile t) {
        if(used && getGodPower()){
            return t == buildTile;
        }
        return super.canBuildBlock(t);
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Atlas' God Powers can activate the God Power to build a dome instead of a block.
     */
    @Override
    public void buildBlock(Tile t) {
        /* if Atlas can build a block then he can build a dome*/
        buildTile = t;
        used = true;
        setGodPower(true);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getState() {
        if (getGodPower()) {  /* to print the Board */
            return 2;
        }else{
            return super.getState();
        }
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * if the parameter "use" is <code>True</code>, the worker builds a Dome, otherwise a Block.
     */
    @Override
    public void useGodPower(boolean use) {
        if(use){
            super.buildDome(buildTile);
        }else {
            super.buildBlock(buildTile);
        }
        setGodPower(false);
        used = false;
        buildTile = null;
    }
}
