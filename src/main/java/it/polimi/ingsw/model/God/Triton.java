package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

public class Triton extends WorkerDecorator {

    public Triton(UndecoratedWorker worker) {
        super(worker);
    }

    @Override
    public void move(Tile t) {
        super.move(t);
        if (t.perimeterTile()) {
            for(Tile tile : getAdjacentTiles()){
                if(canMove(tile)){
                    setGodPower(true);
                    break;
                }
            }
        }
    }

}


