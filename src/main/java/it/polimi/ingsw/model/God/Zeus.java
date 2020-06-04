package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

public class Zeus extends WorkerDecorator{
    public Zeus (UndecoratedWorker worker){
        super(worker);
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        return (getPosition() == t || super.canBuildBlock(t));
    }
}
