package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;


public class Hephaestus extends WorkerDecorator {

    private int buildCounter = 0;
    private Tile builtTile;

    public Hephaestus (UndecoratedWorker worker){
        super(worker);
    }


    @Override
    public boolean canBuildBlock(Tile t) {
        if(getGodPower()){
            return t == builtTile;
        }
        if (buildCounter == 0) {
            return super.canBuildBlock(t);
        }else {
            if(builtTile.getBlockLevel() < 3){  /* second Block */
                return true;
            }
        }
        return false;
    }

    @Override
    public void buildBlock(Tile t) {
        if(buildCounter == 0) {  /* first Build */
            super.buildBlock(t);
            builtTile = t;
            buildCounter = 1;
            if(canBuildBlock(t)) {
                setGodPower(true);
            }
        } else {  /* second Build */
            buildCounter = 0;
            super.buildBlock(builtTile);
        }
    }

    @Override
    public int getState() {  /* per print Board */
        if (getGodPower()) {
            setGodPower(false);
            int state = super.getState();
            setGodPower(true);
            return state;
        }else{
            return super.getState();
        }
    }

    @Override
    public void useGodPower(boolean use) {
        if(use){
            buildBlock(builtTile);
        }
        setGodPower(false);
        buildCounter = 0;
        builtTile = null;
    }
}
