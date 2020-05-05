package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

public class MapTest extends TestCase {

    Map map = new Map();

    @Test
    public void testInitializeTiles() {
        map.initializeTiles();

        Tile t = map.getTile(1,1);
        assertSame(t,map.getMap()[1][1]);

        assertEquals(1,t.getRow());
        assertEquals(1,t.getColumn());
        assertEquals(0,t.getBlockLevel());
        assertFalse(t.isOccupiedByWorker());
        assertFalse(t.isDomePresence());


    }


}