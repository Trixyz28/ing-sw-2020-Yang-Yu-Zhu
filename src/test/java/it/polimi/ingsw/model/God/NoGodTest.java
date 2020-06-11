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
        t = board.getTile(2,1);
        t.setBlockLevel(1);

        noGod.setPosition(t);
        assertEquals(t,noGod.getPosition());
    }


    @Test
    public void testBuild() {
        testPosition();
        destination.setRow(1);
        destination.setColumn(2);
        destination.setBlockLevel(2);

        Assert.assertTrue(noGod.canBuildBlock(destination));
        Assert.assertFalse(noGod.canBuildDome(destination));

        noGod.buildBlock(destination);
        Assert.assertFalse(noGod.canBuildBlock(destination));
        Assert.assertTrue(noGod.canBuildDome(destination));

        Assert.assertFalse(t.isDomePresence());
        noGod.buildDome(destination);
        Assert.assertTrue(destination.isDomePresence());
    }


    @Test
    public void testGodPower() {
        noGod.setState(1);
        Assert.assertEquals(1, noGod.getState());
        noGod.setGodPower(true);
        Assert.assertTrue(noGod.getGodPower());
        Assert.assertEquals(0, noGod.getState());
        noGod.useGodPower(false);
        noGod.nextState();
        Assert.assertEquals(2, noGod.getState());
        Assert.assertFalse(noGod.getGodPower());
        noGod.nextState();
        Assert.assertEquals(0, noGod.getState());

    }

    @Test
    public void testConditions() {
        Assert.assertEquals(condition,noGod.getConditions());
    }


}