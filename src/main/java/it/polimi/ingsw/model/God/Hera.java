package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

public class Hera extends WorkerDecorator{
    public Hera (UndecoratedWorker worker){
        super(worker);
        getConditions().setHeraPlayerID(getBelongToPlayer());
    }

    @Override
    public boolean checkWin(Tile initialTile) {
        return (initialTile.getBlockLevel() == 2 && getPosition().getBlockLevel()==3);
    }
}
