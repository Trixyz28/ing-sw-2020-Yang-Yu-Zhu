package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

public class Demeter extends WorkerDecorator {

    public Demeter (UndecoratedWorker worker){
        super(worker);
    }


    private Tile originalBuild = new Tile();
    private int buildCounter = 0;


    @Override
    public boolean canBuildBlock(Tile t) {
        if (buildCounter == 0) {
            return super.canBuildBlock(t);
        }
        else{
            return canBuildBlockDemeter(t);
        }
    }

    @Override
    public void buildBlock(Tile t) {
        super.buildBlock(t);
        if(buildCounter == 0) {
            buildCounter = 1;
            originalBuild = t;
            if(canBuild()) {
                setGodPower(true);
            }
        }
    }

    @Override
    public boolean canBuildDome(Tile t) {
        if( buildCounter == 0){
            return super.canBuildDome(t);
        }
        else{
            return canBuildDomeDemeter(t);
        }
    }

    @Override
    public void buildDome(Tile t) {
        super.buildDome(t);
        if (buildCounter == 0) {
            buildCounter = 1;
            if(canBuild()) {
                setGodPower(true);
            }
        }
    }

    @Override
    public void nextState() {
        super.nextState();
        buildCounter = 0;
        originalBuild = null;
    }


    public boolean canBuildBlockDemeter(Tile t){
        if(t == originalBuild){
            return false;
        } else{
            return super.canBuildBlock(t);

        }
    }

    public boolean canBuildDomeDemeter(Tile t){
        if(t == originalBuild){
            return false;
        } else{
            return super.canBuildDome(t);

        }
    }


    private boolean canBuild(){
        for(Tile t : getAdjacentTiles()){
            if(canBuildBlock(t) || canBuildDome(t)){
                return true;
            }
        }
        return false;
    }



}
