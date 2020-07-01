package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

/**
 * This is the worker for the God Prometheus, decorated under WorkerDecorator abstract class.
 * <p></p>
 * If Prometheus's Worker does not move up, it may build both before and after moving.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Prometheus extends WorkerDecorator {

    /**
     * Creates a worker with Prometheus' God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Prometheus(UndecoratedWorker worker) {
        super(worker);  /* Prometheus ask directly Build or Move */
    }

    private int buildCounter = 0;
    private int moveCounter = 0;
    private boolean used = false;


    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * if the worker bestowed with Prometheus' God Powers doesn't move up he can choose to build both before and after moving.
     */
    @Override
    public boolean canMove(Tile t) {
        if(buildCounter == 0){
            if(!used && !getGodPower() && moveCounter == 0 && canBuild(t) && super.canMove(t)){
                setGodPower(true);
            }
            return super.canMove(t);
        }else {
            /* build first -> cannot move up */
            return (super.canMove(t) && getPosition().getBlockLevel() >= t.getBlockLevel());
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void move(Tile t) {
        moveCounter++;
        super.move(t);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * if the worker bestowed with Prometheus' God Powers doesn't move up he can build a block both before and after moving.
     */
    @Override
    public void buildBlock(Tile t) {
        buildCounter++;
        super.buildBlock(t);

    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * if the worker bestowed with Prometheus' God Powers doesn't move up he can build a dome both before and after moving.
     */
    @Override
    public void buildDome(Tile t) {
        buildCounter++;
        super.buildDome(t);
    }
    /**
     * {@inheritDoc}
     *<p></p>
     * If parameter "use" is <code>true</code>, the worker choose to build first, if <code>false</code> the worker
     * choose to move first.
     */
    @Override
    public void useGodPower(boolean use) {
        if(use){
        /* build */
            setState(2);
        }else{
            setState(1);  /* move */
        }
        used = true;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void nextState(){
        if(buildCounter == 1 && moveCounter == 0){
            setState(1);
        }else {
            super.nextState();
            /* reset counter at the end of the turn */
            if(getState() == 0){
                moveCounter = 0;
                buildCounter = 0;
                used = false;
            }
        }
    }

    /**
     * Check if Prometheus' worker can build a block or a dome again.
     * @return A boolean: <code>true</code> if the "build" operation is available, otherwise <code>false</code>.
     */
    private boolean canBuild(Tile t){
        return (canBuildBlock(t) || canBuildDome(t));
    }


}
