package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Atlas extends WorkerDecorator {

    private Tile buildTile;

    public Atlas (UndecoratedWorker worker){
        super(worker);
    }

    @Override
    public boolean canBuildDome(Tile t) {
        if(getPosition().isAdjacentTo(t) && !t.isDomePresence() && !t.isOccupiedByWorker()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        if(super.canBuildBlock(t) && canBuildDome(t)){
            buildTile = t;
            setGodPower(true);
        }
        return super.canBuildBlock(t);
    }

    @Override
    public int useGodPower(boolean use) {
        if(use){
            buildDome(buildTile);
        }else {
            buildBlock(buildTile);
        }
        setGodPower(false);
        return 2;
    }
}
