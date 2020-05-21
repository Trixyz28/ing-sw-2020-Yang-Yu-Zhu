package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Athena extends WorkerDecorator {

    public Athena (UndecoratedWorker worker){
        super(worker);
    }

    @Override
    public void move(Tile t){
        //if athena worker moved up ( check move controller) then modify AthenaRule in object Conditions
        //normal move + if worker moves up: change AthenaRule to true.
        super.move(t);
        boolean athenaBoolean = this.getPosition().movedUp(getPosition(),t);
        // setAthenaRule(athenaBoolean);
    }

    @Override
    public List<Tile> canMove(Tile t) {

        return super.canMove(t);
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

        return super.canBuildDome(t);
    }

    @Override
    public void buildDome(Tile t) {
        super.buildDome(t);
    }

    //also in turnController if player has athena as god set athenaRule false every time the turn is passed to the player
    //setAthenaRule(true);
}


