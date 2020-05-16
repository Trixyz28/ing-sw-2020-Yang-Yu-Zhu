package it.polimi.ingsw.model;

public class Operation {

    //type of the operation: 1-move, 2-build
    private final Player player;
    private int type;
    private int row;
    private int column;

    public Operation(Player player, int type,int row,int column) {
        this.player = player;
        this.type = type;
        this.row = row;
        this.column = column;
    }

    public Player getPlayer() {
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

}
