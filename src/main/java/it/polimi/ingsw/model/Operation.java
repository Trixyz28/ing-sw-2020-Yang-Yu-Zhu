package it.polimi.ingsw.model;

import java.io.Serializable;

public class Operation implements Serializable {

    //type of the operation: 1-move, 2-build
    private final String player;
    private int type;
    private int row;
    private int column;

    public Operation(final Player player,int type,int row,int column) {
        this.player = player.getPlayerNickname();
        this.type = type;
        this.row = row;
        this.column = column;
    }

    public String getPlayer() {
        return player;
    }

    public int getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }


}
