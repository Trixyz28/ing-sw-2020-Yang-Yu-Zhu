package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

public class Hestia extends WorkerDecorator {
    public Hestia (UndecoratedWorker worker){
        super(worker);
    }

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
            //view.DemeterBuild;
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
    public int useGodPower(boolean use) {
        if(!use){
            setGodPower(false);
        }
        return 2;   /* operation type 2 = BUILD */
    }

    public boolean canBuildBlockHestia(Tile t){
        if(perimerterTile(t)){
            return false;
        }
        else{
            return super.canBuildBlock(t);

        }
    }

    public boolean canBuildDomeHestia(Tile t){
        if(perimerterTile(t)){
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

    private boolean perimerterTile(Tile t){
        return (t.getRow() == 0 || t.getRow() == 4 || t.getColumn() == 0 || t.getRow() == 4);
    }

}
