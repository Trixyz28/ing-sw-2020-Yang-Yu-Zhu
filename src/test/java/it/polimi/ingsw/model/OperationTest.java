package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

public class OperationTest extends TestCase {


    Operation operation = new Operation(2,1,0);

    @Test
    public void testType() {
        assertEquals(2,operation.getType());
    }

    @Test
    public void testRow() {
        assertEquals(1,operation.getRow());
    }

    @Test
    public void testColumn() {
        assertEquals(0,operation.getColumn());
    }

    @Test
    public void testSetPosition() {
        operation.setPosition(3,2);
        assertEquals(3,operation.getRow());
        assertEquals(2,operation.getColumn());
    }

}