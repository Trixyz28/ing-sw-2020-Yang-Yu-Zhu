package it.polimi.ingsw.model.God;


import it.polimi.ingsw.model.Tile;

import java.util.List;

/**
 * This is the abstract class which implements the interface for the Decorator Pattern and decorates all the workers.
 * <p></p>
 * All the methods can be overridden by particular workers in their own classes, if not overridden in their
 * own specified class, as the Decorator Pattern implements, the methods functionality is based on the class
 * NoGod,which is the "basic" behaviour of all the workers in case they are not influenced by a God Power.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public abstract class WorkerDecorator implements UndecoratedWorker {

    //Undecorated worker associated
    protected UndecoratedWorker worker;

    /**
     * Creates a <code>Worker</code> with the specified attributes.
     * @param worker Variable that represents the yet-to-be decorated worker.
     */
    public WorkerDecorator(UndecoratedWorker worker){
        this.worker = worker;
        setGodPower(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(Tile t){
        this.worker.move(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMove(Tile t){
        return this.worker.canMove(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildBlock(Tile t) {
        this.worker.buildBlock(t);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBuildBlock(Tile t) {
        return this.worker.canBuildBlock(t);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void buildDome(Tile t) {
        this.worker.buildDome(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBuildDome(Tile t) {
        return this.worker.canBuildDome(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tile getPosition() {
        return this.worker.getPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(Tile t) {
        this.worker.setPosition(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getGodPower() {
        return this.worker.getGodPower();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGodPower(boolean b) {
        this.worker.setGodPower(b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Conditions getConditions() {
       return this.worker.getConditions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useGodPower(boolean use) {
        this.worker.useGodPower(use);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBelongToPlayer() {
        return this.worker.getBelongToPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkWin(Tile initialTile) {
        return this.worker.checkWin(initialTile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextState() {
        this.worker.nextState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(int state) {
        this.worker.setState(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getState() {
        return this.worker.getState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tile> getAdjacentTiles() {
        return this.worker.getAdjacentTiles();
    }


}