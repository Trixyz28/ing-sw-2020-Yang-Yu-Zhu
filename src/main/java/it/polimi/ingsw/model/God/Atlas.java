package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;



public class Atlas extends WorkerDecorator {

    private Tile buildTile;

    private boolean used;

    public Atlas (UndecoratedWorker worker){
        super(worker);
    }

    @Override
    public boolean canBuildDome(Tile t) {
        if(used && getGodPower()){
            return t == buildTile;
        }
        if(getPosition().isAdjacentTo(t) && !t.isDomePresence() && !t.isOccupiedByWorker()) {
            if(getConditions().checkBuildCondition(t)) {
                return true;
            }else {
                return super.canBuildDome(t);
            }
        }
        return false;
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        if(used && getGodPower()){
            return t == buildTile;
        }
        return super.canBuildBlock(t);
    }

    @Override
    public void buildBlock(Tile t) {
        /* se può buildare Block di sicuro può buildare Dome */
        buildTile = t;
        used = true;
        setGodPower(true);
    }

    @Override
    public int getState() {
        if (getGodPower()) {  /* per print Board */
            return 2;
        }else{
            return super.getState();
        }
    }


    @Override
    public void useGodPower(boolean use) {
        if(use){
            super.buildDome(buildTile);
        }else {
            super.buildBlock(buildTile);
        }
        setGodPower(false);
        used = false;
        buildTile = null;
    }
}
