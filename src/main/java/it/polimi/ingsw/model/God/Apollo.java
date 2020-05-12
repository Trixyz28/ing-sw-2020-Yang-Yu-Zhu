package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;


public class Apollo extends WorkerDecorator {

    public Apollo (UndecoratedWorker worker){

        super(worker);
    }


    @Override
    public List<Tile> canMove(Tile t) {


        ArrayList<Tile> tempList = new ArrayList<Tile>();
        tempList = super.canMove();


        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                T = getTile(i,j);
                adjTile = availableApolloToMove(T);
                if (adjTile == true){
                    tempList.add(T);
                }
            }
        }

        return tempList;
    }


    @Override
    public void move(Tile t) {
        if(t.occupiedByWorker == false){
            return super.move();
        }
        if(t.occupiedByWorker == true){
            NoGod transferWorker = new NoGod();
            transferWorker = getTileWorker(t);
            transferWorker.move(super.this.position);
            super.move();

        }
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
        return super.canBuildDome();
    }

    @Override
    public void buildDome(Tile t) {

    }



    public boolean availableApolloToMove(Tile dest) {
        if(adjacentTile(dest) && !dest.domePresence
                && dest.getBlockLevel()-this.getBlockLevel()<=1 ) {
            return true;
        }

        return false;
    }

}
