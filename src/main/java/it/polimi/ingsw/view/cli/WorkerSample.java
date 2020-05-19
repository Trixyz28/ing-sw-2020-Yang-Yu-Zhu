package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Tile;

public class WorkerSample {

    private int belongToPlayer;
    private Tile position;

    public int getBelongToPlayer() {
        return belongToPlayer;
    }

    public void setBelongToPlayer(int belongToPlayer) {
        this.belongToPlayer = belongToPlayer;
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
    }

}
