package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Turn;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class HestiaTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Hestia worker = new Hestia(new NoGod(0, conditions));

    @Test
    public void testBuildBlock() {
        /* worker (1,1) */
        worker.setPosition(board.getTile(1,1));
        Tile build = board.getTile(2,2);
        Tile perimeter = board.getTile(0,2);

        /* build on (2,2) */
        Assert.assertTrue(worker.canBuildBlock(build));
        Assert.assertTrue(worker.canBuildBlock(perimeter));
        Assert.assertFalse(worker.getGodPower());
        worker.buildBlock(build);

        /* GodPower active -> can build an additional time */
        Assert.assertTrue(worker.getGodPower());
        Assert.assertTrue(worker.canBuildBlock(build));
        /* cannot build on a perimeter tile */
        Assert.assertFalse(worker.canBuildBlock(perimeter));
    }

    @Test
    public void testBuildDome() {
        /* worker (1,1) */
        worker.setPosition(board.getTile(1,1));
        Tile build = board.getTile(2,2);
        Tile perimeter = board.getTile(0,2);

        /* set build dome conditions */
        perimeter.setBlockLevel(3);
        build.setBlockLevel(3);

        /* build dome on (0,2) */
        Assert.assertTrue(worker.canBuildDome(perimeter));
        Assert.assertFalse(worker.getGodPower());
        worker.buildDome(perimeter);

        /* GodPower active -> can build again */
        Assert.assertTrue(worker.getGodPower());
        Assert.assertTrue(worker.canBuildDome(build));
        Assert.assertFalse(worker.canBuildDome(perimeter));
    }

    @Test
    public void testBuild() {
        worker.nextState();
        Assert.assertEquals(1, worker.getState());
        /* worker (0,0) */
        worker.setPosition(board.getTile(0,0));
        /* set cannot active GodPower conditions */
        board.getTile(1,1).setDomePresence(true);

        /* build on (0,1) */
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,1)));
        worker.buildBlock(board.getTile(0,1));
        /* godPower inactive -> cannot build again */
        Assert.assertFalse(worker.getGodPower());

    }

}