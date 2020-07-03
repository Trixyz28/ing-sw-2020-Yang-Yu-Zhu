package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class AtlasTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Atlas worker = new Atlas(new NoGod(2, conditions));


    @Test
    public void testBuildBlock() {

        Assert.assertEquals(0, worker.getState());
        /* worker (0,1) */
        worker.setPosition(board.getTile(0, 1));

        worker.nextState();
        Assert.assertEquals(1, worker.getState());

        worker.nextState();
        Assert.assertEquals(2, worker.getState());

        /* worker (0,1) can build on (1,1) */
        Assert.assertTrue(worker.canBuildBlock(board.getTile(1, 1)));
        Assert.assertFalse(worker.canBuildBlock(board.getTile(2, 2)));
        worker.buildBlock(board.getTile(1, 1));
        /* GodPower ON -> can build block or dome */
        Assert.assertTrue(worker.getGodPower());
        Assert.assertEquals(2, worker.getState());
        /* if GodPower is active, Atlas can build only on the chosen tile (1,1) */
        Assert.assertTrue(worker.canBuildBlock(board.getTile(1, 1)));
        Assert.assertFalse(worker.canBuildBlock(board.getTile(0, 2)));
        Assert.assertEquals(0,board.getTile(1, 1).getBlockLevel());

        /* build block */
        worker.useGodPower(false);
        Assert.assertEquals(1,board.getTile(1, 1).getBlockLevel());

    }

    @Test
    public void testBuildDome(){
        /* worker on (1,1) */
        worker.setPosition(board.getTile(1, 1));
        /* opponent = Limus (2,2) */
        Limus opponent = new Limus(new NoGod(0, conditions));
        opponent.setPosition(board.getTile(2, 2));

        board.getTile(2, 1).setBlockLevel(3);
        /* can build on (0,1) */
        Assert.assertTrue(worker.canBuildDome(board.getTile(0, 1)));
        /* Tile adjacent to Limus position */
        Assert.assertFalse(worker.canBuildDome(board.getTile(1, 2)));
        /* (2,2) occupied by Limus */
        Assert.assertFalse(worker.canBuildDome(board.getTile(2, 2)));
        /* Tile adjacent to Limus -> but can build a complete tower */
        Assert.assertTrue(worker.canBuildDome(board.getTile(2, 1)));
        /* can build on (0,2) */
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,2)));

        /* build on (0,1).level = 0 -> block or dome */
        worker.buildBlock(board.getTile(0, 1));
        Assert.assertTrue(worker.getGodPower());
        /* if GodPower is active, Atlas can build only on the chosen tile (0,1) */
        Assert.assertTrue(worker.canBuildDome(board.getTile(0, 1)));
        Assert.assertFalse(worker.canBuildDome(board.getTile(0, 2)));

        /* waiting for answer to use or not godPower */
        Assert.assertEquals(0,board.getTile(0, 1).getBlockLevel());

        /* build dome */
        worker.useGodPower(true);
        Assert.assertTrue(board.getTile(0, 1).isDomePresence());
    }


}