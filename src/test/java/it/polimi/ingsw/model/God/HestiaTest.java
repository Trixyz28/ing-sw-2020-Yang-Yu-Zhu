package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class HestiaTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Hestia worker = new Hestia(new NoGod(0, conditions));

    @Test
    public void testBuildBlock() {
        worker.setPosition(board.getTile(1,1));
        Tile build = board.getTile(2,2);
        Tile perimeter = board.getTile(0,2);
        Assert.assertTrue(worker.canBuildBlock(build));
        Assert.assertTrue(worker.canBuildBlock(perimeter));
        Assert.assertFalse(worker.getGodPower());
        worker.buildBlock(build);
        Assert.assertTrue(worker.getGodPower());
        Assert.assertTrue(worker.canBuildBlock(build));
        Assert.assertFalse(worker.canBuildBlock(perimeter));
    }

    @Test
    public void testBuildDome() {
        worker.setPosition(board.getTile(1,1));
        Tile build = board.getTile(2,2);
        Tile perimeter = board.getTile(0,2);
        perimeter.setBlockLevel(3);
        build.setBlockLevel(3);
        Assert.assertTrue(worker.canBuildDome(perimeter));
        Assert.assertFalse(worker.getGodPower());
        worker.buildDome(perimeter);
        Assert.assertTrue(worker.getGodPower());
        Assert.assertTrue(worker.canBuildDome(build));
        Assert.assertFalse(worker.canBuildDome(perimeter));
    }

    @Test
    public void testBuild() {
        worker.nextState();
        Assert.assertEquals(1, worker.getState());
        worker.setPosition(board.getTile(0,0));
        board.getTile(1,1).setDomePresence(true);
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,1)));
        worker.buildBlock(board.getTile(0,1));
        Assert.assertFalse(worker.getGodPower());

    }

}