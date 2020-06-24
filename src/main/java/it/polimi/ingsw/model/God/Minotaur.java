package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;


public class Minotaur extends WorkerDecorator {

    private List<UndecoratedWorker> totalWorkers;

    public Minotaur (UndecoratedWorker worker, List<UndecoratedWorker> totalWorkerList){
        super(worker);
        totalWorkers = totalWorkerList;
    }


    @Override
    public boolean canMove(Tile t) {
        if(getPosition().isAdjacentTo(t) && !t.isDomePresence() && t.getBlockLevel()-getPosition().getBlockLevel()<=1) {
            if(getConditions().checkMoveCondition(getPosition(), t)) {
                if(t.isOccupiedByWorker()){
                    UndecoratedWorker opponent = t.getWorker(totalWorkers);
                    /* trovato worker sulla tile */
                    if(opponent.getBelongToPlayer() != getBelongToPlayer()){
                        Tile forcedTile = getForcedTile(t);
                        /* condizioni spinta */
                        if(forcedTile != null && !forcedTile.isDomePresence() && !forcedTile.isOccupiedByWorker()){
                            /* add solo se forcedTile libero */
                            return true;
                        }
                    }
                }else{
                    /* aggiungere tile liberi */
                    return true;
                }
            }
        }

        return false;
    }



    @Override
    public void move(Tile t) {
        if(t.isOccupiedByWorker()){
            /* cambiare posizione avversario */
            t.getWorker(totalWorkers).setPosition(getForcedTile(t));
        }
        super.move(t);
    }


    private Tile getForcedTile(Tile destination){
        int forcedRow = destination.getRow()+(destination.getRow()-getPosition().getRow());
        int forcedColumn = destination.getColumn()+(destination.getColumn()-getPosition().getColumn());
        if(forcedRow>=0 && forcedColumn >=0 && forcedRow <5 && forcedColumn <5) {
            for(Tile t : destination.getAdjacentTiles()){  /* trovare la tile della spinta */
                if(t.getRow() == forcedRow && t.getColumn() == forcedColumn){
                    return t;  /* forced tile */
                }
            }
        }
        return null;
    }

}
