package it.polimi.ingsw.model.God;


import it.polimi.ingsw.model.Tile;


public abstract class WorkerDecorator implements UndecoratedWorker {

    //undecorated worker associated
    protected UndecoratedWorker worker;

    public WorkerDecorator( UndecoratedWorker worker){
        this.worker = worker;
    }

    //standard constructors

    @Override
    public void move(Tile t){
        return this.worker.move();

    }

    @Override
    public boolean canMove(){
        return this.worker.canMove;
    }

    @Override
    public void buildBlock(
            return this.worker.buildBlock;
    )

    @Override
    public boolean canBuildBlock(
           return this.worker.canBuildblock;
   )

     @Override
    public void buildDome{
        return this.worker.buildDome;
    }
   @Override
    public boolean canBuildDome{
        return this.worker.canBuildDome;
    }

}