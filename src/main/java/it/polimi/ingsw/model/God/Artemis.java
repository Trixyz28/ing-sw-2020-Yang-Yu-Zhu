package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Artemis extends WorkerDecorator {

    public Artemis (UndecoratedWorker worker){

        super(worker);
    }

    private Tile originalTile = new Tile();
    private int counter = 0;

    @Override
    public List<Tile> canMove(Tile t) {

        if(counter == 1) {
            setOriginalTile(super.getPosition());
            List<Tile> tempList;
            Tile tempTile = new Tile();

            tempList = super.canMove(t);

            for (int i = 0; i < tempList.size(); i++) {
                tempTile = tempList.get(i);
                if (tempTile == originalTile) {
                    tempList.remove(i);
                }
            }
            return tempList;
        }
        else{
            return super.canMove(t);
        }
    }


    @Override
    public void move(Tile t) {
        if(counter == 1){
            super.move(t);
        }
        if(counter == 0){
            super.move(t);
            setCounter(1);
            //view.MoveArtemis;
            //richiede un move ulteriore per il worker scelto con lo stesso worker
        }


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