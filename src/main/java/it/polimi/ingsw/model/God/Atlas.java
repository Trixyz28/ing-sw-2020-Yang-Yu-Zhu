package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Atlas extends WorkerDecorator {

    public Atlas (UndecoratedWorker worker){

        super(worker);
    }


    @Override
    public List<Tile> canMove(Tile t) {
        return super.canMove(t);
    }


    @Override
    public void move(Tile t) {
        super.move(t);
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        return super.canBuildBlock(t);
    }

    @Override
    public void buildBlock(Tile t) {
        super.buildBlock(t);
    }

    @Override
    public boolean canBuildDome(Tile t) {
        System.out.println("He's Atlas he can build a dome everywhere!");
        if(!t.isDomePresence() && !t.isOccupiedByWorker()) {
            return true;
        }
        return false;
    }

    @Override
    public void buildDome(Tile t) {
        super.buildDome(t);
    }




}
