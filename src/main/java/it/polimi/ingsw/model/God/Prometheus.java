package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;


public class Prometheus extends WorkerDecorator {

    public Prometheus(UndecoratedWorker worker) {
        super(worker);  /* Prometheus chiedere direttamente Buil or Move */
    }

    private int buildCounter = 0;
    private int moveCounter = 0;


    @Override
    public List<Tile> canMove() {
        if (buildCounter == 0) {
            if(canBuild()){
                setGodPower(true);
            }
            return super.canMove();
        }else {  /* build first -> cannot move up */
            List<Tile> tempList = new ArrayList<>();
            for(Tile t : super.canMove()){
                if(getPosition().getBlockLevel() >= t.getBlockLevel()){
                    tempList.add(t);
                }
            }
            return tempList;
        }
    }


    @Override
    public void move(Tile t) {
        if(buildCounter == 0) {
            moveCounter++;
            setGodPower(false);
        }
        super.move(t);
    }


    @Override
    public void buildBlock(Tile t) {
        buildCounter++;
        super.buildBlock(t);
        resetAfterBuild();

    }


    @Override
    public void buildDome(Tile t) {
        /*
        return super.buildDome(t);
        if (getCounter() == 1) {
            return super.canMove();
        }

         */
        buildCounter++;
        super.buildDome(t);
        resetAfterBuild();
    }

    @Override
    public int useGodPower(boolean use) {
        if(use && buildCounter == 0){
            return 2;
        }else {
            return 1;
        }
    }

    private void resetAfterBuild(){
        if(buildCounter == 2 || (moveCounter == 1 && buildCounter ==1)){
            buildCounter = 0;  /* ripristinare counter */
            moveCounter = 0;
        }
    }

    private boolean canBuild(){
        for(Tile t : getPosition().getAdjacentTiles()){
            if(canBuildBlock(t) || canBuildDome(t)){
                return true;
            }
        }
        return false;
    }

    /*
    public int getCounter() {
        return counter;
    }

    public void setCounter(int i){
        this.counter = i;
    }


     */

}
