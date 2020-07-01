package it.polimi.ingsw.model;

import java.io.Serializable;
/**
 * Class that is used to represent the operations move or build actions.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Operation implements Serializable {

    //type of the operation: 1-move, 2-build
    private int type;
    private int row;
    private int column;

    /**
     *  Creates a <code>operation</code> with the specified attributes.
     * @param type Variable that indicates the type of the operation.
     * @param row Variable that indicates the row of the destination <code>Tile</code>.
     * @param column Variable that indicates the column of the destination <code>Tile</code>.
     */
    public Operation(int type,int row,int column) {
        this.type = type;
        this.row = row;
        this.column = column;
    }
    /**
     * Gets the type of the operation.
     * @return The integer type encapsulated in the class.
     */
    public int getType() {
        return type;
    }

    /**
     * Gets the the row of the destination <code>Tile</code>.
     * @return The integer row encapsulated in the class.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column of the destination <code>Tile</code>.
     * @return The integer column encapsulated in the class.
     */
    public int getColumn() {
        return column;
    }

    /**
     *Sets the row and the column of the destination <code>Tile</code> in the object.
     * @param row Variable that indicates the row of the destination <code>Tile</code>.
     * @param column Variable that indicates the column of the destination <code>Tile</code>.
     */
    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

}
