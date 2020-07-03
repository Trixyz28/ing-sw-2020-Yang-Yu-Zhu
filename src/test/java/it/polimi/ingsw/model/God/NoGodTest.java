package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class NoGodTest extends TestCase {

    Conditions condition = new Conditions();
    UndecoratedWorker noGod = new NoGod(0, condition);
    Board board = new Board();
    Tile t = new Tile();
    Tile destination = new Tile();


    @Test
    public void testPosition() {
        /* t initially free */
        t = board.getTile(2,1);
        Assert.assertFalse(t.isOccupiedByWorker());

        /* set position worker on (2,1) */
        noGod.setPosition(t);
        /* (2,1) occupied by noGod */
        Assert.assertEquals(t,noGod.getPosition());
        Assert.assertTrue(t.isOccupiedByWorker());
    }


    @Test
    public void testBuild() {
        testPosition();
        /* worker (2,1) */
        destination.setRow(1);
        destination.setColumn(2);
        destination.setBlockLevel(2);

        /* worker can build block on (1,2).level = 2 */
        Assert.assertTrue(noGod.canBuildBlock(destination));
        Assert.assertFalse(noGod.canBuildDome(destination));

        /* build block on (1,2) */
        noGod.buildBlock(destination);
        /* (1,2).level = 3 -> available to build dome */
        Assert.assertFalse(noGod.canBuildBlock(destination));
        Assert.assertTrue(noGod.canBuildDome(destination));

        /* build dome */
        Assert.assertFalse(t.isDomePresence());
        noGod.buildDome(destination);
        Assert.assertTrue(destination.isDomePresence());
    }


    @Test
    public void testGodPower() {
        noGod.setState(1);
        Assert.assertEquals(1, noGod.getState());
        /* GodPower ON -> state = 0 (waiting for answer) */
        noGod.setGodPower(true);
        Assert.assertTrue(noGod.getGodPower());
        Assert.assertEquals(0, noGod.getState());

        /* GodPower OFF */
        noGod.useGodPower(false);
        noGod.nextState();
        Assert.assertEquals(2, noGod.getState());
        Assert.assertFalse(noGod.getGodPower());
        /* state 2 -> 0 */
        noGod.nextState();
        Assert.assertEquals(0, noGod.getState());

    }

    @Test
    public void testConditions() {
        Assert.assertEquals(condition,noGod.getConditions());
    }


}