package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;


public class Demeter extends WorkerDecorator {

    public Demeter (UndecoratedWorker worker){

        super(worker);
    }


    private Tile originalBuild = new Tile();
    private int counter = 0;


    @Override
    public List<Tile> canMove(Tile t) {
        return super.canMove();
    }


    @Override
    public void move(Tile t) {
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        if (getCounter == 0) {
            return super.canBuildBlock();
            view.DemeterBuild;
        }
        else{
            return canBuildBlockDemeter();
        }
    }

    @Override
    public void buildBlock(Tile t) {
            setCounter(1);
            setOriginaBuild(t);
            super.buildBlock;
    }

    @Override
    public boolean canBuildDome(Tile t) {
        if( getCounter() == 0){
            return super.canBuildDome();
            view.DemeterBuild;
        }
        else{
            return canBuildDomeDemeter();
        }
    }

    @Override
    public void buildDome(Tile t) {
        setCounter(1);
        setOriginaBuild(t);
        super.buildDome;

    }

    public Tile getOriginalBuild() {
        return originalBuild;
    }

    public void setOriginaBuild(Tile t) {
        this.originalBuild = t;
    }


    public int getCounter() {
        return counter;
    }

    public void setCounter(int i) {
        this.counter = i;
    }

    public boolean canBuildBlockDemeter(Tile t){
        if(this.originalBuild == t){
            return false;
        }
        else{
            return super.canBuildBlock();

        }
    }

    public boolean canBuildBlockDemeter(Tile t){
        if(this.originalBuild == t){
            return false;
        }
        else{
            return super.canBuildDome();

        }
    }



}
