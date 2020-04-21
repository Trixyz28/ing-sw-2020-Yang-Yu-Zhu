package it.polimi.ingsw.model.God;


public abstract class WorkerDecorator implements UndecoratedWorker {

    //undecorated worker associated
    private UndecoratedWorker worker;

    public WorkerDecorator(UndecoratedWorker worker) {

    }

    // standard constructors
    // esempio con move()


    @Override
    public void move(){

       // return worker.decorate();
    }
    // canMove();
    // buildBlock();
    // canBuildBlock();
    // buildDome();
    // canBuildDome();
}