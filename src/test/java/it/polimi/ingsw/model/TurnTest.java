package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

public class TurnTest extends TestCase {

    Player player = new Player();
    Turn turn = new Turn(player);

    @Test
    public void testTurnNumber() {
        turn.setTurnNumber(10);
        assertEquals(10,turn.getTurnNumber());
    }

    @Test
    public void testInitialTile() {
        Tile t = new Tile();
        turn.setInitialTile(t);
        assertEquals(t,turn.getInitialTile());
    }

    @Test
    public void testFinalTile() {
        Tile t = new Tile();
        turn.setFinalTile(t);
        assertEquals(t,turn.getFinalTile());
    }

    @Test
    public void testBuiltTile() {
        Tile t = new Tile();
        turn.setBuiltTile(t);
        assertEquals(t,turn.getBuiltTile());
    }

}