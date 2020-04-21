package it.polimi.ingsw.model;

public abstract class WorkerDecorator implements UndecoratedWorker {

    //undecorated worker associated
    private UndecoratedWorker worker;

    // standard constructors
    // esempio con move()
    @override
    public void move(){

        return worker.decorate();
    }
    // canMove();
    // buildBlock();
    // canBuildBlock();
    // buildDome();
    // canBuildDome();
}