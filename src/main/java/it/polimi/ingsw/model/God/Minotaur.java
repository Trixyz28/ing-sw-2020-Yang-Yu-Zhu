package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;


public class Minotaur extends WorkerDecorator {

    public Minotaur (UndecoratedWorker worker){

        super(worker);
    }


    @Override
    public List<Tile> canMove(boolean canMoveUp) {

        List<Tile> tempList = new ArrayList<>();
        for (Tile tile : getPosition().getAdjacentTiles()){
            if(!tile.isDomePresence() && tile.getBlockLevel()-getPosition().getBlockLevel()<=1 ){
                if(canMoveUp || getPosition().getBlockLevel() >= tile.getBlockLevel()) {
                    if(tile.isOccupiedByWorker()){  /* condizioni spinta */
                        int forcedRow = tile.getRow()+(tile.getRow()-getPosition().getRow());
                        int forcedColumn = tile.getColumn()+(tile.getColumn()-getPosition().getColumn());
                        if(forcedRow>=0 && forcedColumn >=0 && forcedRow <5 && forcedColumn <5) {
                            for(Tile t : tile.getAdjacentTiles()){  /* trovare la tile della spinta */
                                if(t.getRow() == forcedRow && t.getColumn() == forcedColumn){
                                    if(!t.isOccupiedByWorker() && !t.isDomePresence()){
                                        tempList.add(tile);  /* aggiungere il tile che ha come adiacente t */
                                    }
                                    break;
                                }
                            }
                        }
                    }else{  /* aggiungere tile liberi */
                        tempList.add(tile);
                    }
                }
            }
        }
        return tempList;
    }




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
        super.move(t);
        //minotaurMove(t,this.position);
    }


    /*

    public boolean availableMinotaurToMove(Tile dest,Tile minotaur) {
        if(this.position.adjacentTile(dest) && !dest.isDomePresence() && headbuttMinotaur(dest,minotaur)
                && dest.getBlockLevel()-this.position.getBlockLevel()<=1 ) {
            return true;
        }

        return false;
    }
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
