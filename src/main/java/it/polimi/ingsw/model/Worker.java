package it.polimi.ingsw.model;

public abstract class Worker {

    private String belongToPlayer;

    private int workerID;

    private Tile currentPosition;

    private String positionRow;

    private String positionColumn;



    private int workerHeight;
    public int getWorkerHeight() {
        return workerHeight;
    }

    public void setWorkerHeight(int workerHeight) {
        this.workerHeight = workerHeight;
    }




    public void getPosition() {

    }
    public void setPosition() {

    }




    public void canMove(Tile t) {

    }

    public void canMoveUp(Tile t) {

    }

    public void move() {

    }

    public void checkWinningMove() {

    }

    public void canBuild() {

    }

    public void build() {

    }

    public void buildBlock() {

    }

    public void buildDome() {

    }

    public void removeWorker() {

    }

    public void endOperation() {

    }


}
