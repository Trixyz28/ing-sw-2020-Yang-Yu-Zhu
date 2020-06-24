package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Apollo extends WorkerDecorator {

    private List<UndecoratedWorker> totalWorkers;

    public Apollo (UndecoratedWorker worker, List<UndecoratedWorker> totalWorkerList){
        super(worker);
        totalWorkers = totalWorkerList;
    }


    @Override
    public boolean canMove(Tile t) {
        if(getPosition().isAdjacentTo(t) && !t.isDomePresence()
                && t.getBlockLevel() - getPosition().getBlockLevel()<=1 ) {
            if(getConditions().checkMoveCondition(getPosition(), t)) {
                if(t.isOccupiedByWorker()){
                    UndecoratedWorker opponent = t.getWorker(totalWorkers);
                    /* trovato worker che occupa la tile */
                    if(opponent.getBelongToPlayer() != getBelongToPlayer()){
                        return true;
                    }
                }else {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void move(Tile t) {

        if(!t.isOccupiedByWorker()){
            super.move(t);
        } else {
            /* scambiare posizione */
            t.getWorker(totalWorkers).setPosition(getPosition());
            setPosition(t);
        }

    }

}
