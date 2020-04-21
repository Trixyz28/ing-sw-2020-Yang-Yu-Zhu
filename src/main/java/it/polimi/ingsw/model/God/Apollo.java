package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;


public class Apollo extends WorkerDecorator {

    public Apollo (UndecoratedWorker worker){
        super(worker);
    }

    @Override
    public void canMove() {
        //specific move: he can move to non occupied or occupied tiles
        //normal canMove + apolloCanMove
    }

    @Override
    public void buildBlock() {

    }

    @Override
    public void canBuildBlock() {

    }

    @Override
    public void buildDome() {

    }

    @Override
    public void canBuildDome() {

    }

    private void apolloCanMove(){
            //checks adjacent tiles +/-1 height difference (normal can move would check same tiles minus occupied tiles)

        }

    @Override
    public void move() {
        //specific move: if he moves to an occupied tile he can exchange spot with the other worker
        //normal move()+apolloMove() if there's a worker in the tile it's moving to
    }
    private void apolloMove(){
            //move the worker to the selected tile and exchange the two workers
        }

}
