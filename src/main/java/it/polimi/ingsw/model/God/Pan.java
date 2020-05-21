package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Pan extends WorkerDecorator {

    public Pan (UndecoratedWorker worker){

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
    }

    @Override
    public boolean canBuildDome(Tile t) {
        return canBuildDome(t);
    }

    @Override
    public void buildDome(Tile t) {
        super.buildDome(t);
    }

    //T was the destination, jumpedFrom was the original tile; if blocklevel jumpedFrom - t = 2 pan win
    public boolean panCheck(Tile jumpedFrom){
        if(jumpedFrom.getBlockLevel() - getPosition().getBlockLevel() == 2) {
            return true;
        }
        else{
            return false;
        }
    }



}