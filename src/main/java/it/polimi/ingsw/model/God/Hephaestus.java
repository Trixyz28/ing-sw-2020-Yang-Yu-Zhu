package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;


public class Hephaestus extends WorkerDecorator {

    public Hephaestus (UndecoratedWorker worker){

        super(worker);
    }


    @Override
    public List<Tile> canMove(Tile t) {
        return super.canMove();
    }


    @Override
    public void move(Tile t) {
    }

    @Override
    public boolean canBuildBlock(Tile t) {
     return super.canBuildBlock();
    }

    @Override
    public void buildBlock(Tile t) {
        super.buildBlock;
        view.Hapheastus;
        if (answerFromView == true) {
            super.buildBlock();
        }
    }

    @Override
    public boolean canBuildDome(Tile t) {
      return super.canBuildDome();
    }

    @Override
    public void buildDome(Tile t) {
    }





}
