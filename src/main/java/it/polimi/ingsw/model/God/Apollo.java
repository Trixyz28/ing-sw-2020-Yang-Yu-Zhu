package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;


public class Apollo extends WorkerDecorator {

    public Apollo (UndecoratedWorker worker){
        super(worker);
    }


    @Override
    public boolean canMove(Tile t) {
        //specific move: he can move to non occupied or occupied tiles
        //normal canMove + apolloCanMove
        return false;
    }

    @Override
    public void move(Tile t) {
        //specific move: if he moves to an occupied tile he can exchange spot with the other worker
        //normal move()+apolloMove() if there's a worker in the tile it's moving to
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        return false;
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

    private void apolloCanMove(){
            //checks adjacent tiles +/-1 height difference (normal can move would check same tiles minus occupied tiles)
    }



    private void apolloMove(){
            //move the worker to the selected tile and exchange the two workers
    }


}
