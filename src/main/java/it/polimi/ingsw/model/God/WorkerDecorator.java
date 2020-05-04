package it.polimi.ingsw.model.God;


import it.polimi.ingsw.model.Tile;


public abstract class WorkerDecorator implements UndecoratedWorker {

    //undecorated worker associated
    private UndecoratedWorker worker;

    public WorkerDecorator(UndecoratedWorker worker) {

    }

    // standard constructors
    // esempio con move()


    @Override
    public void move(Tile t){

       // return worker.decorate();
    }
    // canMove();
    // buildBlock();
    // canBuildBlock();
    // buildDome();
    // canBuildDome();
}