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
                    UndecoratedWorker opponent = getWorker(t,totalWorkers);  /* trovato worker sulla tile */
                    if(opponent.getBelongToPlayer() != getBelongToPlayer()){
                        Tile forcedTile = getForcedTile(t);
                        /* condizioni spinta */
                        if(forcedTile != null && !forcedTile.isDomePresence() && !forcedTile.isOccupiedByWorker()){
                            return true;  /* add solo se forcedTile libero */
                        }
                    }
                }else{  /* aggiungere tile liberi */
                    return true;
                }
            }
        }

        return false;
    }

    /*
    @Override
    public List<Tile> canMove() {

        List<Tile> tempList = new ArrayList<>();
        for (Tile t : getPosition().getAdjacentTiles()){
            if(availableMinotaurToMove(t)){
                tempList.add(t);
            }
        }
        return tempList;
    }



     */


/*
        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                Tile tempTile = getTile(i,j);
                boolean adjTile = availableMinotaurToMove(tempTile,this.position);
                if (adjTile == true){
                    tempList.add(tempTile);
                }
            }
        }

        return tempList;

    }
*/

    @Override
    public void move(Tile t) {
        if(t.isOccupiedByWorker()){
            getWorker(t,totalWorkers).setPosition(getForcedTile(t));  /* cambiare posizione avversario */
        }
        super.move(t);
    }
/*
    private UndecoratedWorker getOpponent(Tile tile){
        for(UndecoratedWorker w : totalWorkers){
            if(w.getPosition() == tile){  /* trovato worker sulla tile
                return w;
            }
        }
        return null;
    }
*/
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

/*
    public boolean availableMinotaurToMove(Tile dest) {
        if(getPosition().isAdjacentTo(dest) && !dest.isDomePresence() && dest.getBlockLevel()-getPosition().getBlockLevel()<=1) {
            if(getConditions().canMoveUp() || getPosition().getBlockLevel() >= dest.getBlockLevel()) {
                if(dest.isOccupiedByWorker()){
                    UndecoratedWorker opponent = getWorker(dest,totalWorkers);  /* trovato worker sulla tile
                    if(opponent.getBelongToPlayer() != getBelongToPlayer()){
                        Tile forcedTile = getForcedTile(dest);
                        /* condizioni spinta
                        if(forcedTile != null && !forcedTile.isDomePresence() && !forcedTile.isOccupiedByWorker()){
                            return true;  /* add solo se forcedTile libero
                        }
                    }

                }else{  /* aggiungere tile liberi
                    return true;
                }
            }
        }

        return false;
    }
    */
    /*
    //dest= destionation tile, minotaur = tile where the minotaur worker is
    public boolean headbuttMinotaur(Tile dest, Tile minotaur){
        return minotaurTile(dest,minotaur).isOccupiedByWorker() && minotaurTile(dest,minotaur).isDomePresence();

    }
    //minotaur is original tile where Minotaur worker was and mover is the headbutted worker's tile


    public boolean minotaurMove(Tile mover, Tile minotaur){
        NoGod transferWorker = new NoGod();
        transferWorker = getTileWorker(mover);
        transferWorker.move(minotaurTile(mover,minotaur));
    }

    //tile where the other worker is headbutted to
    public Tile minotaurTile(Tile dest, Tile minotaur){
        int x = dest.getRow() + dest.getRow() - minotaur.getRow();
        int y = dest.getColumn() + dest.getColumn() - minotaur.getColumn();

        return getTile(x,y);
    }

     */
}
