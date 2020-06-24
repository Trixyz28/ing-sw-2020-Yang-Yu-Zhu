package it.polimi.ingsw.model;

import java.io.Serializable;

public class Operation implements Serializable {

    //type of the operation: 1-move, 2-build
    private int type;
    private int row;
    private int column;

    public Operation(int type,int row,int column) {
        this.type = type;
        this.row = row;
        this.column = column;
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
