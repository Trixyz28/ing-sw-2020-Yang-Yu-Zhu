package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Demeter extends WorkerDecorator {

    public Demeter (UndecoratedWorker worker){

        super(worker);
    }


    private Tile originalBuild = new Tile();
    private int counter = 0;


    @Override
    public List<Tile> canMove(Tile t) {
        return super.canMove(t);
    }


    @Override
    public void move(Tile t) {
        super.move(t);
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        if (getCounter() == 0) {
            return super.canBuildBlock(t);
             //view.DemeterBuild;
        }
        else{
            return canBuildBlockDemeter(t);
        }
    }

    @Override
    public void buildBlock(Tile t) {
        if(counter == 0) {
            setCounter(1);
            setOriginalBuild(t);
            super.buildBlock(t);
        }else{
            setCounter(0);
            super.buildBlock(t);
        }
    }

    @Override
    public boolean canBuildDome(Tile t) {
        if( getCounter() == 0){
            return super.canBuildDome(t);
            //view.DemeterBuild;
        }
        else{
            return canBuildDomeDemeter(t);
        }
    }

    @Override
    public void buildDome(Tile t) {
        setCounter(1);
        setOriginalBuild(t);
        super.buildDome(t);

    }


    public Tile getOriginalBuild() {
        return originalBuild;
    }

    public void setOriginalBuild(Tile t) {
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
            return super.canBuildBlock(t);

        }
    }

    public boolean canBuildDomeDemeter(Tile t){
        if(this.originalBuild == t){
            return false;
        }
        else{
            return super.canBuildDome(t);

        }
    }



}
