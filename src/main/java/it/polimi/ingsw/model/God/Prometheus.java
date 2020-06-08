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
        }else {  /* build first -> cannot move up */
            return (super.canMove(t) && getPosition().getBlockLevel() >= t.getBlockLevel());
        }
    }

    /*
    @Override
    public List<Tile> canMove() {
        if (buildCounter == 0) {
            List<Tile> tempList = super.canMove();
            if(tempList.size() != 0 && canBuild()){
                System.out.println("on");
                setGodPower(true);
            }
            return tempList;
        }else {  /* build first -> cannot move up
            List<Tile> tempList = new ArrayList<>();
            for(Tile t : super.canMove()){
                if(getPosition().getBlockLevel() >= t.getBlockLevel()){
                    tempList.add(t);
                }
            }
            return tempList;
        }
    }


     */

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
        used = true;
    }

    @Override
    public void nextState() {
        if(buildCounter == 1 && moveCounter == 0){
            setState(1);
        } else {
            super.nextState();
            if(getState() == 0){  /* ripristinare counter fine turno */
                moveCounter = 0;
                buildCounter = 0;
                used = false;
            }
        }
    }


    private boolean canBuild(Tile t){
        return (canBuildBlock(t) || canBuildDome(t));
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
