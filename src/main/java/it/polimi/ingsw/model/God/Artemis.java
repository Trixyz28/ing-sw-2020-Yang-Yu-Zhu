package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;


public class Artemis extends WorkerDecorator {

    public Artemis (UndecoratedWorker worker){

        super(worker);
    }

    private Tile originalTile = new Tile();
    private int counter = 0;

    @Override
    public List<Tile> canMove(Tile t) {

        if(counter == 1) {
            setOriginalTile(super.position);
            public ArrayList<Tile> tempList = new ArrayList<Tile>();
            public tempTile =new Tile();

            tempList = super.canMove();

            for (i = 0; i < tempList.size(); i++) {
                tempTile = get(i).tempList;
                if (tempTile == originalTile) {
                    remove(i);
                }
            }
            return tempList;
        }
        else{
            return super.canMove();
        }
    }


    @Override
    public void move(Tile t) {
        if(counter == 1){
            super.move();
        }
        if(counter == 0){
            super.move();
            setCounter(1);
            view.MoveArtemis;
            //richiede un move ulteriore per il worker scelto con lo stesso worker
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

    public Tile getOriginalTile() {
            return originalTile;
        }

     public void setOriginalTile(Tile t) {
            this.originalTile = t;
        }

    public int getCounter() {
            return counter;
        }

     public void setCounter(int i) {
            this.counter = i;
        }
}