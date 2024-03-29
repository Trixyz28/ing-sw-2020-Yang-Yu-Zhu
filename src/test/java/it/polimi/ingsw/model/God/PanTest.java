package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;


public class PanTest extends TestCase {

    Conditions conditions = new Conditions();
    Pan worker = new Pan(new NoGod(1, conditions));

    @Test
    public void testPanCheck() {
        Tile t = new Tile();
        t.setBlockLevel(0);
        worker.setPosition(t);

        /* from level 2 to level 0 -> win */
        Tile from = new Tile();
        from.setBlockLevel(2);
        Assert.assertTrue(worker.checkWin(from));

        /* from level 1 to level 0 -> cannot win */
        from.setBlockLevel(1);
        Assert.assertFalse(worker.checkWin(from));

    }


}