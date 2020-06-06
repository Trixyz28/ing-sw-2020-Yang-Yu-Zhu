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
            List<Tile> tempList = super.canMove();
            if(tempList.size() != 0 && canBuild()){
                System.out.println("on");
                setGodPower(true);
            }
            return tempList;
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
            setGodPower(false);
        }
        moveCounter++;
        super.move(t);
    }


    @Override
    public void buildBlock(Tile t) {
        buildCounter++;
        super.buildBlock(t);

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
    }

    @Override
    public void useGodPower(boolean use) {
        if(use){
            if(buildCounter == 0) { /* build */
                setState(2);
            }
        }else {
            setState(1);  /* move */
        }
    }

    @Override
    public void nextState() {
        if(buildCounter == 1 && moveCounter == 0){
            setState(1);
        }else if(getState() == 0 && canMove().size() == 0){   /* inizio turn Prometheus non può muovere*/
            setState(2);
        } else {
            super.nextState();
            if(getState() == 0){  /* ripristinare counter fine turno */
                moveCounter = 0;
                buildCounter = 0;
            }
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
