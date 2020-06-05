package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Test;


public class PanTest extends TestCase {

    Conditions conditions = new Conditions();
    Pan worker = new Pan(new NoGod(1, conditions));

    @Test
    public void testPanCheck() {
        Tile t = new Tile();
        t.setBlockLevel(0);
        worker.setPosition(t);

        Tile from = new Tile();
        from.setBlockLevel(2);
        assertTrue(worker.checkWin(from));

        from.setBlockLevel(1);
        assertFalse(worker.checkWin(from));

    }


}