package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;



public class Prometheus extends WorkerDecorator {

    public Prometheus(UndecoratedWorker worker) {
        super(worker);  /* Prometheus chiedere direttamente Buil or Move */
    }

    private int buildCounter = 0;
    private int moveCounter = 0;
    private boolean used = false;


    @Override
    public boolean canMove(Tile t) {
        if(buildCounter == 0){
            if(!used && !getGodPower() && moveCounter == 0 && canBuild(t)){
                setGodPower(true);
            }
            return super.canMove(t);
        }else {
            /* build first -> cannot move up */
            return (super.canMove(t) && getPosition().getBlockLevel() >= t.getBlockLevel());
        }
    }

    @Override
    public void move(Tile t) {
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
        buildCounter++;
        super.buildDome(t);
    }

    @Override
    public void useGodPower(boolean use) {
        if(use){
        /* build */
            setState(2);
        }else{
            setState(1);  /* move */
        }
        used = true;
    }

    @Override
    public void nextState(){
        if(buildCounter == 1 && moveCounter == 0){
            setState(1);
        }else {
            super.nextState();
            /* ripristinare counter fine turno */
            if(getState() == 0){
                moveCounter = 0;
                buildCounter = 0;
                used = false;
            }
        }
    }


    private boolean canBuild(Tile t){
        return (canBuildBlock(t) || canBuildDome(t));
    }


}
