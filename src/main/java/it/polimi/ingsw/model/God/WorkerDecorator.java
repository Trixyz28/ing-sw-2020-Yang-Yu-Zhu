package it.polimi.ingsw.model.God;


import it.polimi.ingsw.model.Tile;

import java.util.List;


public abstract class WorkerDecorator implements UndecoratedWorker {

    //undecorated worker associated
    protected UndecoratedWorker worker;

    public WorkerDecorator(UndecoratedWorker worker){
        this.worker = worker;
        setGodPower(false);  /* godPower ancora attivato */
    }

    //standard constructors

    @Override
    public void move(Tile t){
        this.worker.move(t);
    }

    @Override
    public List<Tile> canMove(){
        return this.worker.canMove();
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

    @Override
    public Tile getPosition() {
        return this.worker.getPosition();
    }

    @Override
    public void setPosition(Tile t) {
        this.worker.setPosition(t);
    }

    @Override
    public boolean getGodPower() {
        return this.worker.getGodPower();
    }

    @Override
    public void setGodPower(boolean b) {
        this.worker.setGodPower(b);
    }

    @Override
    public Conditions getConditions() {
       return this.worker.getConditions();
    }
}