package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;


public class Atlas extends WorkerDecorator {

    public Atlas (UndecoratedWorker worker){

        super(worker);
    }


    @Override
    public List<Tile> canMove(Tile t) {
        return super.canMove();
    }


    @Override
    public void move(Tile t) {
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        return super.canBuildBlock();
    }

    @Override
    public void buildBlock(Tile t) {
    }

    @Override
    public boolean canBuildDome(Tile t) {

        System.out.println("He's Atlas he can build a dome everywhere!");
        return true;
    }

    @Override
    public void buildDome(Tile t) {

    }




}
