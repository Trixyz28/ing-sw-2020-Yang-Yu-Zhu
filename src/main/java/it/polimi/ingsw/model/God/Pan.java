package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Pan extends WorkerDecorator {

    public Pan (UndecoratedWorker worker){

        super(worker);
    }

    @Override
    public boolean checkWin(Tile initialTile) {
        return super.checkWin(initialTile) || (getConditions().checkWinCondition(getPosition()) && panCheck(initialTile));
    }

    //T was the destination, jumpedFrom was the original tile; if blocklevel jumpedFrom - t >= 2 pan win
    private boolean panCheck(Tile jumpedFrom){
        if(jumpedFrom.getBlockLevel() - getPosition().getBlockLevel() >= 2) {
            return true;
        }
        else{
            return false;
        }
    }



}