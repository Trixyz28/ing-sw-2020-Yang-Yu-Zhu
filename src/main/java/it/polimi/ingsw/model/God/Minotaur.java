package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;


public class Minotaur extends WorkerDecorator {

    public Minotaur (UndecoratedWorker worker){

        super(worker);
    }


    @Override
    public List<Tile> canMove(Tile t) {

        ArrayList<String> tempList = new ArrayList<String>();
        tempList = super.canMove();


        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                T = getTile(i,j);
                adjTile = availableMinotaurToMove(T,this.position);
                if (adjTile == true){
                    tempList.add(T);
                }
            }
        }

        return tempList;

    }


    @Override
    public void move(Tile t) {
        super.move();
        minotaurMove(t,this.position);
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        return super.canBuildBlock();
    }

    @Override
    public void buildBlock(Tile t) {
        super.buildBlock;
    }

    @Override
    public boolean canBuildDome(Tile t) {
        return super.canBuildDome();
    }

    @Override
    public void buildDome(Tile t) {
    }


    public boolean availableMinotaurToMove(Tile dest,Tile minoutar) {
        if(adjacentTile(dest) && !dest.domePresence && headbuttMinotaur(dest,minotaur)
                && dest.getBlockLevel()-this.getBlockLevel()<=1 ) {
            return true;
        }

        return false;
    }
    //dest= destionation tile, minotaur = tile where the minotaur worker is
    public boolean headbuttMinotaur(Tile dest, Tile minotaur){
        return minotaurTile(dest,minotaur).isOccupiedWorker() && minotaurTile(dest,minotaur).isDomePresence()

    }
    //minotaur is original tile where Minotaur worker was and mover is the headbutted worker's tile
    public boolean minotaurMove(Tile mover, Tile minotaur ){
        NoGod transferWorker = new NoGod();
        transferWorker = getTileWorker(mover);
        transferWorker.move(minotaurTile(mover,minotaur));
    }
    //tile where the other worker is headbutted to
    public Tile minotaurTile(Tile dest, Tile minotaur){
        int x = getRow(dest) + getRow(dest) - getRow(minotaur);
        int y = getColumn(dest) + getColumn(dest) - getColumnd(minotaur);

        return getTile(x,y);
    }
}
