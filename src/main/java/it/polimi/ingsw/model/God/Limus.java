package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

public class Limus extends WorkerDecorator {

    public Limus(UndecoratedWorker worker){
        super(worker);
        getConditions().addLimusWorker(this);
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        return getPosition().availableToBuild(t) && t.getBlockLevel() < 3;
    }
}
