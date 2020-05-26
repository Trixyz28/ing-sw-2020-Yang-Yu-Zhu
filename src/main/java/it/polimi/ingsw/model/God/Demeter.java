package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Demeter extends WorkerDecorator {

    public Demeter (UndecoratedWorker worker){
        super(worker);
    }


    private Tile originalBuild = new Tile();
    private int buildCounter = 0;

    @Override
    public void move(Tile t) {
        buildCounter = 0;
        super.move(t);
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        if (buildCounter == 0) {
            return super.canBuildBlock(t);
             //view.DemeterBuild;
        }
        else{
            return canBuildBlockDemeter(t);
        }
    }

    @Override
    public void buildBlock(Tile t) {
        if(buildCounter == 0) {
            buildCounter = 1;
            setOriginalBuild(t);
            if(canBuild()) {
                setGodPower(true);
            }
        }
        super.buildBlock(t);
    }

    @Override
    public boolean canBuildDome(Tile t) {
        if( buildCounter == 0){
            return super.canBuildDome(t);
            //view.DemeterBuild;
        }
        else{
            return canBuildDomeDemeter(t);
        }
    }

    @Override
    public void buildDome(Tile t) {
        if (buildCounter == 0) {
            buildCounter = 1;
            if(canBuild()) {
                setGodPower(true);
            }
        }
        super.buildDome(t);
    }

    public Tile getOriginalBuild() {
        return originalBuild;
    }

    private void setOriginalBuild(Tile t) {
        this.originalBuild = t;
    }
/*

    public int getCounter() {
        return counter;
    }

    public void setCounter(int i) {
        this.counter = i;
    }


 */
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


    private boolean canBuild(){
        for(Tile t : getPosition().getAdjacentTiles()){
            if(canBuildBlock(t) || canBuildDome(t)){
                return true;
            }
        }
        return false;
    }



}
