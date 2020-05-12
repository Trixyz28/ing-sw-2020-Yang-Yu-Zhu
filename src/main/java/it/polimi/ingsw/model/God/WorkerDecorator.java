package it.polimi.ingsw.model.God;


import it.polimi.ingsw.model.Tile;

import java.util.List;


public abstract class WorkerDecorator implements UndecoratedWorker {

    //undecorated worker associated
    protected UndecoratedWorker worker;
    protected Tile position;

    public WorkerDecorator(UndecoratedWorker worker){
        this.worker = worker;
    }

    //standard constructors

    @Override
    public void move(Tile t){
        this.worker.move(t);
    }

    @Override
    public List<Tile> canMove(Tile t){
        return this.worker.canMove(t);
    }

    @Override
    public void buildBlock(Tile t) {
        this.worker.buildBlock(t);
    }


    @Override
    public boolean canBuildBlock(Tile t) {
        return this.worker.canBuildBlock(t);
    }


    @Override
    public void buildDome(Tile t) {
        this.worker.buildDome(t);
    }

    @Override
    public boolean canBuildDome(Tile t) {
        return this.worker.canBuildDome(t);
    }

}