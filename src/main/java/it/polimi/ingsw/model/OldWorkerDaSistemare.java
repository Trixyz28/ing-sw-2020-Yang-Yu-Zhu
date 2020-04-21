package it.polimi.ingsw.model;

// da sistemare (rimane una classe con lista che associa ogni tile della mappa con un worker di un player(attributes)
public class Worker {


    private int belongToPlayer;

    private int workerID;
    private String workerType;


    private Tile currentPosition;


   //metodi da spostare nel decorator pattern
    public void canMove(Tile currentPosition) {

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
