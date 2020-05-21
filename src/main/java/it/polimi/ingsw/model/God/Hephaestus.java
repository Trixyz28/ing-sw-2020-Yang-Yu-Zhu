package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;


public class Hephaestus extends WorkerDecorator {

    public Hephaestus (UndecoratedWorker worker){

        super(worker);
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        return super.canBuildBlock(t);
    }

    @Override
    public void buildBlock(Tile t) {
        super.buildBlock(t);
        /*
        view.Hephaestus;
        if (answerFromView == true) {
            super.buildBlock(t);
        }

         */
    }


}
