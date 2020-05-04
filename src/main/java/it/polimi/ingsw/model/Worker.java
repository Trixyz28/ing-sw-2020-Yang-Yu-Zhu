package it.polimi.ingsw.model;


// da sistemare (rimane una classe con lista che associa ogni tile della mappa con un worker di un player(attributes)
public class Worker {


    private int belongToPlayer;

    private int workerID;

    private String workerType;

    private Tile currentPosition;

    //get() the currentPosition
    public Tile getCurrentPosition() {
        return currentPosition;
    }

    //metodi da spostare nel decorator pattern
    public boolean canMove() {
        return true;
    }

    public void canMoveUp(Tile t) {

    }

    public void move(Tile t) {


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





}
