package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Prometheus extends WorkerDecorator {

    public Prometheus(UndecoratedWorker worker) {

        super(worker);
    }

    private int counter = 0;

    @Override
    public List<Tile> canMove(Tile t) {
        //counter 0= player can choose to build first

        if (getCounter() == 0) {
            //view.PrometheusFirstChoice();
        }
        //on view let choose if he want to build move first,if he builds he cant move up and can build again

        //counter set on 1(on choice) if he builds first
        if (getCounter() == 1) {
            List<Tile> tempList = super.canMove(t);

/*
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    T = getTile(i, j);
                    adjTile = prometheusLimitation(T);
                    if (adjTile == true) {
                        tempList.remove(T);
                    }
                }
            }
*/
            return tempList;
        } else {
            return super.canMove(t);
        }
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
        /*
        return super.buildBlock(t);
        if (getCounter() == 1) {
            return super.canMove();
        }

         */
        super.buildBlock(t);
    }

    @Override
    public boolean canBuildDome(Tile t) {
        return super.canBuildDome(t);
    }

    @Override
    public void buildDome(Tile t) {
        /*
        return super.buildDome(t);
        if (getCounter() == 1) {
            return super.canMove();
        }

         */
        super.buildDome(t);
    }

    public boolean prometheusLimitation (Tile dest){
        /*
        if (adjacentTile(dest) && !dest.domePresence && !dest.occupiedByWorker
                && dest.getBlockLevel() - this.getBlockLevel() <= 0) {
            return true;
        }
    */
        return false;


    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int i){
        this.counter = i;
    }


}
