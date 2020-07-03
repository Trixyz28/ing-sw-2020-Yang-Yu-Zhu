package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

/**
 * This is the worker for the God Hephaestus, decorated under WorkerDecorator abstract class.
 * <p></p>
 * Hephaestus' Worker may build one additional block (not dome) on top of his first block.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Hephaestus extends WorkerDecorator {

    private int buildCounter = 0;
    private Tile builtTile;

    /**
     * Creates a worker with Hephaestus' God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Hephaestus (UndecoratedWorker worker){
        super(worker);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Hephaestus' God Powers can choose to build a block on top of the block he already built.
     */
    @Override
    public boolean canBuildBlock(Tile t) {
        if(getGodPower()){
            return t == builtTile;
        }
        if (buildCounter == 0) {
            return super.canBuildBlock(t);
        }else {
            /* second Block */
            return builtTile.getBlockLevel() < 3;
        }
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Hephaestus' God Powers can build a block on top of the block he already built.
     */
    @Override
    public void buildBlock(Tile t) {
        if(buildCounter == 0) {  /* first Build */
            super.buildBlock(t);
            builtTile = t;
            buildCounter = 1;
            if(canBuildBlock(t)) {
                setGodPower(true);
            }
        } else {
            /* second Build */
            super.buildBlock(builtTile);
        }
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * Hephaestus' Worker can only build a dome on his first "build" operation of the turn if possible.
     */
    @Override
    public boolean canBuildDome(Tile t) {
        if(buildCounter == 0) {
            return super.canBuildDome(t);
        }else {
            return false;
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getState() {
        /* for print Board */
        if (getGodPower()) {
            return 2;
        }else{
            return super.getState();
        }
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * If the parameter "use" is <code>True</code> the additional block is built.
     */
    @Override
    public void useGodPower(boolean use) {
        if(use){
            buildBlock(builtTile);
        }
        setGodPower(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextState() {
        super.nextState();
        buildCounter = 0;
        builtTile = null;
    }
}
