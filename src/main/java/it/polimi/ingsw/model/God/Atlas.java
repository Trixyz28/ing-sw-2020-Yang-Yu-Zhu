package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Atlas extends WorkerDecorator {

    public Atlas (UndecoratedWorker worker){
        super(worker);
    }

    @Override
    public boolean canBuildDome(Tile t) {
        //System.out.println("He's Atlas he can build a dome everywhere!");
        if(getPosition().isAdjacentTo(t) && !t.isDomePresence() && !t.isOccupiedByWorker()) {
            return true;
        }
        return false;
    }

}
