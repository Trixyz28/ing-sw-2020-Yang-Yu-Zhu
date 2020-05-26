package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Artemis extends WorkerDecorator {

    public Artemis (UndecoratedWorker worker){
        super(worker);
    }

    private Tile originalTile = new Tile();
    private int moveCounter = 0;

    @Override
    public List<Tile> canMove() {

        if(moveCounter == 1) {  /* second Move */
            List<Tile> tempList;


            tempList = super.canMove();

            for (int i = 0; i < tempList.size(); i++) {
                Tile tempTile = tempList.get(i);
                if (tempTile == originalTile) {
                    tempList.remove(i);
                    break;
                }
            }
            return tempList;
        }
        else{  /* first Move */
            setOriginalTile(super.getPosition());
            return super.canMove();
        }
    }


    @Override
    public void move(Tile t) {
        if(moveCounter == 1){
            super.move(t);
        }
        if(moveCounter == 0){
            super.move(t);
            moveCounter++;
            if(canMove().size() != 0) {
                setGodPower(true);  /* attivare godPower */
            }
            //view.MoveArtemis;
            //richiede un move ulteriore per il worker scelto con lo stesso worker
        }


    }

    @Override
    public boolean canBuildBlock(Tile t) {
        moveCounter = 0;  /* ripristinare counter dopo le eventuali move*/
        return super.canBuildBlock(t);
    }

    public Tile getOriginalTile() {
        return originalTile;
    }

    private void setOriginalTile(Tile t) {
        this.originalTile = t;
    }
    /*
    public int getCounter() {
        return counter;
    }

    public void setCounter(int i) {
        this.counter = i;
    }
     */
}