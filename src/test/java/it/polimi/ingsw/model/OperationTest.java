package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

public class OperationTest extends TestCase {

    Player player = new Player("A");

    Operation operation = new Operation(player,2,1,0);

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
    public void testPlayer() {
        assertEquals("A",player.getPlayerNickname());
    }

}