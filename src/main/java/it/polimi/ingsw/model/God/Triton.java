package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

public class Triton extends WorkerDecorator {

    public Triton(UndecoratedWorker worker) {
        super(worker);
    }

    @Override
    public void move(Tile t) {
        if (t.perimerterTile()) {
            setGodPower(true);
        }
        super.move(t);
    }

    @Override
    public int useGodPower(boolean use) {
        if(!use){
            setGodPower(false);
        }
        return 1;
    }
}


