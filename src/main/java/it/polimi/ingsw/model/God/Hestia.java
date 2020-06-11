package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

public class Hestia extends WorkerDecorator {
    public Hestia (UndecoratedWorker worker){
        super(worker);
    }

    private int buildCounter = 0;

    @Override
    public boolean canBuildBlock(Tile t) {
        if (buildCounter == 0) {
            return super.canBuildBlock(t);
        }
        else{
            return canBuildBlockHestia(t);
        }
    }

    @Override
    public void buildBlock(Tile t) {
        if(buildCounter == 0) {
            buildCounter = 1;
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
        }
        else{
            return canBuildDomeHestia(t);
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

    @Override
    public void nextState() {
        super.nextState();
        buildCounter = 0;
    }

    private boolean canBuildBlockHestia(Tile t){
        if(t.perimeterTile()){
            return false;
        }
        else{
            return super.canBuildBlock(t);

        }
    }

    private boolean canBuildDomeHestia(Tile t){
        if(t.perimeterTile()){
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
