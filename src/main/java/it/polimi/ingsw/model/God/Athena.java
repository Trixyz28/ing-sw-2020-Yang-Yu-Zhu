package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;


public class Athena extends WorkerDecorator {

    public Athena (UndecoratedWorker worker){
        super(worker);
    }

    @Override
    public void move(Tile t){
        //if athena worker moved up ( check move controller) then modify AthenaRule in object Conditions
        //normal move + if worker moves up: change AthenaRule to true.
    }

    @Override
    public boolean canMove(Tile t) {
        return true;
    }


    @Override
    public boolean canBuildBlock(Tile t) {
        return true;
    }

    @Override
    public void buildBlock(Tile t) {

    }

    @Override
    public boolean canBuildDome(Tile t) {
        return false;
    }

    @Override
    public void buildDome(Tile t) {

    }


    private void athenaRuling(){
        //also in turnController if player has athena as god set athenaRule false every time the turn is passed to the player
        //setAthenaRule(true);
    }
}


