package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;
import java.util.List;


public class Apollo extends WorkerDecorator {

    public Apollo (UndecoratedWorker worker){

        super(worker);
    }


    @Override
    public List<Tile> canMove(Tile t) {

        List<Tile> tempList;
        tempList = super.canMove(t);


        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                Tile tempTile = getTile(i,j);
                boolean adjTile = availableApolloToMove(tempTile);
                if (adjTile == true){
                    tempList.add(tempTile);
                }
            }
        }

        return tempList;
    }


    @Override
    public void move(Tile t) {
        if(!t.isOccupiedByWorker()){
            super.move(t);
        }
        if(t.isOccupiedByWorker()){
            NoGod transferWorker = new NoGod();
            transferWorker = getTileWorker(t);
            transferWorker.move(super.position);
            super.move(t);

        }
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
        return super.canBuildDome(t);
    }

    @Override
    public void buildDome(Tile t) {

    }



    public boolean availableApolloToMove(Tile dest) {
        if(this.position.adjacentTile(dest) && !dest.isDomePresence()
                && dest.getBlockLevel()-this.position.getBlockLevel()<=1 ) {
            return true;
        }

        return false;
    }

}
