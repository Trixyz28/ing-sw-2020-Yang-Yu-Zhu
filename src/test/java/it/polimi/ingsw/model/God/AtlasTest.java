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
        worker.nextState();
        Assert.assertEquals(1, worker.getState());
        worker.nextState();
        worker.setPosition(board.getTile(0, 1));
        Assert.assertTrue(worker.canBuildBlock(board.getTile(1, 1)));
        Assert.assertFalse(worker.canBuildBlock(board.getTile(2, 2)));
        worker.buildBlock(board.getTile(1, 1));
        Assert.assertTrue(worker.getGodPower());
        Assert.assertEquals(2, worker.getState());
        Assert.assertTrue(worker.canBuildBlock(board.getTile(1, 1)));
        Assert.assertFalse(worker.canBuildBlock(board.getTile(0, 2)));
        Assert.assertEquals(0,board.getTile(1, 1).getBlockLevel());
        worker.useGodPower(false);
        Assert.assertEquals(1,board.getTile(1, 1).getBlockLevel());

    }

    @Test
    public void testBuildDome(){
        worker.setPosition(board.getTile(1, 1));
        /* opponent = Limus */
        Limus opponent = new Limus(new NoGod(0, conditions));
        opponent.setPosition(board.getTile(2, 2));
        board.getTile(1, 1).setBlockLevel(2);
        Assert.assertTrue(worker.canBuildDome(board.getTile(0, 1)));
        /* Tile adjacent to Limus position*/
        Assert.assertFalse(worker.canBuildDome(board.getTile(1, 2)));
        Assert.assertFalse(worker.canBuildDome(board.getTile(2, 2)));
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,2)));
        worker.buildBlock(board.getTile(0, 1));
        Assert.assertTrue(worker.getGodPower());
        Assert.assertTrue(worker.canBuildDome(board.getTile(0, 1)));
        Assert.assertFalse(worker.canBuildDome(board.getTile(0, 2)));
        Assert.assertEquals(0,board.getTile(0, 1).getBlockLevel());
        worker.useGodPower(true);
        Assert.assertTrue(board.getTile(0, 1).isDomePresence());
    }


}