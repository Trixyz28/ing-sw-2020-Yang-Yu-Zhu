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
        if(getPosition().getBlockLevel() < t.getBlockLevel()){
            getConditions().setAthenaRule(true);
        }
        super.move(t);
        // setAthenaRule(athenaBoolean);
    }

    @Override
    public List<Tile> canMove() {
        getConditions().setAthenaRule(false);
        return super.canMove();
    }


    //also in turnController if player has athena as god set athenaRule false every time the turn is passed to the player
    //setAthenaRule(true);
}


