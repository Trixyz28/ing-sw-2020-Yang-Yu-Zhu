package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;


public class Apollo extends WorkerDecorator {

    private List<UndecoratedWorker> totalWorkers;

    public Apollo (UndecoratedWorker worker, List<UndecoratedWorker> totalWorkerList){
        super(worker);
        totalWorkers = totalWorkerList;
    }


    @Override
    public List<Tile> canMove() {

        List<Tile> tempList = new ArrayList<>();
        for (Tile tile : getPosition().getAdjacentTiles()){  /* without !occupiedByWorker condition */
            if(availableApolloToMove(tile)){
                tempList.add(tile);
            }
        }

        /*

        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                Tile tempTile = getTile(i,j);
                boolean adjTile = availableApolloToMove(tempTile);
                if (adjTile == true){
                    tempList.add(tempTile);
                }
            }
        }

         */

        return tempList;
    }


    @Override
    public void move(Tile t) {

        if(!t.isOccupiedByWorker()){
            super.move(t);
        }
        else {  /* non reimpostare isOccupiedByWorker false alla posizione precedente */
            //t.setOccupiedByWorker(true);
            getOpponent(t).setPosition(getPosition());  /* scambiare posizione */
            setPosition(t);
            /*
            NoGod transferWorker = new NoGod();
            // transferWorker = getTileWorker(t);
            transferWorker.move(super.getPosition());
            super.move(t);

             */
        }

    }

    private UndecoratedWorker getOpponent(Tile tile){
        for(UndecoratedWorker w : totalWorkers){
            if(w.getPosition() == tile){  /* trovato worker sulla tile */
                return w;
            }
        }
        return null;
    }

    public boolean availableApolloToMove(Tile dest) {
        if(getPosition().isAdjacentTo(dest) && !dest.isDomePresence()
                && dest.getBlockLevel() - getPosition().getBlockLevel()<=1 ) {
            if(getConditions().canMoveUp() || getPosition().getBlockLevel() >= dest.getBlockLevel()) {
                if(dest.isOccupiedByWorker()){
                    UndecoratedWorker opponent = getOpponent(dest);  /* trovato worker che occupa la tile */
                    if(!(opponent instanceof Apollo)){
                        return true;
                    }
                }else {
                    return true;
                }
            }
        }

        return false;
    }

}
