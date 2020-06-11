package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class HephaestusTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Hephaestus worker = new Hephaestus(new NoGod(0, conditions));


    @Test
    public void testBuildBlock() {
        worker.nextState();
        Assert.assertEquals(1, worker.getState());
        worker.setPosition(board.getTile(0,0));
        Tile build = board.getTile(0,1);
        Assert.assertTrue(worker.canBuildBlock(build));
        worker.buildBlock(build);
        Assert.assertEquals(1,build.getBlockLevel());
        Assert.assertTrue(worker.getGodPower());
        Assert.assertEquals(2, worker.getState());
        Assert.assertFalse(worker.canBuildBlock(board.getTile(0,2)));
        worker.useGodPower(true);
        Assert.assertEquals(2,build.getBlockLevel());
    }

    @Test
    public void testBuildBlock2() {
        worker.nextState();
        Assert.assertEquals(1, worker.getState());
        worker.setPosition(board.getTile(0,0));
        Tile build = board.getTile(0,1);
        build.setBlockLevel(2);
        Assert.assertTrue(worker.canBuildBlock(build));
        worker.buildBlock(build);
        Assert.assertEquals(3,build.getBlockLevel());
        Assert.assertFalse(worker.canBuildBlock(build));
        Assert.assertFalse(worker.getGodPower());
    }


}