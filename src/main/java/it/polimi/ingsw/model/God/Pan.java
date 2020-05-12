package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;


public class Pan extends WorkerDecorator {

    public Pan (UndecoratedWorker worker){

        super(worker);
    }


    @Override
    public List<Tile> canMove(Tile t) {
        return super.canMove();
    }


    @Override
    public void move(Tile t) {
        super.move();
        panCheck(t,this.position);
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
        return canBuildDome();
    }

    @Override
    public void buildDome(Tile t) {

    }

    //T was the destination, jumpedFrom was the original tile; if blocklevel jumpedFrom - t = 2 pan win
    public void panCheck(Tile t, Tile jumpedFrom){
        if( getBlockLevel(jumpedFrom) - getBlockLevel(t) == 2) {
            panWinningCondition();
            //on view, pans wins
        }
        else{
            return;
        }
    }



}