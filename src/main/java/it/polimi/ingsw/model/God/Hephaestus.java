package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;


public class Hephaestus extends WorkerDecorator {

    public Hephaestus (UndecoratedWorker worker){

        super(worker);
    }


    @Override
    public List<Tile> canMove(Tile t) {
        return super.canMove(t);
    }


    @Override
    public void move(Tile t) {
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        return super.canBuildBlock(t);
    }

    @Override
    public void buildBlock(Tile t) {
        super.buildBlock(t);
        view.Hephaestus;
        if (answerFromView == true) {
            super.buildBlock(t);
        }
    }

    @Override
    public boolean canBuildDome(Tile t) {
        return super.canBuildDome(t);
    }

    @Override
    public void buildDome(Tile t) {
    }





}
