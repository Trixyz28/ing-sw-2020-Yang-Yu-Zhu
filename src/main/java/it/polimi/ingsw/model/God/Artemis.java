package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;



public class Artemis extends WorkerDecorator {

    public Artemis (UndecoratedWorker worker){
        super(worker);
    }

    private Tile originalTile = new Tile();
    private int moveCounter = 0;


    @Override
    public boolean canMove(Tile t) {
        if(moveCounter == 1) {  /* second Move */
            if(t == originalTile){
                return false;
            }
        }
        return super.canMove(t);

    }


    @Override
    public void move(Tile t) {
        if(moveCounter == 1){
            super.move(t);
        }
        if(moveCounter == 0){   /* first move */
            /* salvare posizione iniziale prima della move */
            originalTile = getPosition();
            super.move(t);
            moveCounter++;
            for(Tile tile : getAdjacentTiles()){
                if(canMove(tile)){
                    /* attivare godPower */
                    setGodPower(true);
                    break;
                }
            }
        }


    }

    @Override
    public void nextState() {
        /* ripristinare counter dopo le eventuali move*/
        super.nextState();
        moveCounter = 0;
    }

}