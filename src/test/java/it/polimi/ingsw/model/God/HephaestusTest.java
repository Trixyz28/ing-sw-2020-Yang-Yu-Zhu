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
        /* worker (0,0) */
        worker.setPosition(board.getTile(0,0));
        worker.nextState();
        Assert.assertEquals(1, worker.getState());

        /* worker build block on (0,1) */
        Tile build = board.getTile(0,1);
        Assert.assertTrue(worker.canBuildBlock(build));
        Assert.assertFalse(worker.canBuildDome(build));
        worker.buildBlock(build);
        Assert.assertEquals(1,build.getBlockLevel());
        /* GodPower active -> build a block again */
        Assert.assertTrue(worker.getGodPower());
        Assert.assertEquals(2, worker.getState());
        /* Hephaestus can only build a block again on the built tile */
        Assert.assertFalse(worker.canBuildBlock(board.getTile(0,2)));
        /* another block */
        worker.useGodPower(true);
        Assert.assertEquals(2,build.getBlockLevel());

        /* hepaestus can't build again a dome -> counter > 0 */
        build.setBlockLevel(3);
        Assert.assertFalse(worker.canBuildDome(build));
    }

    @Test
    public void testBuildBlock2() {
        /* worker on (0,0) */
        worker.setPosition(board.getTile(0,0));
        worker.nextState();
        Assert.assertEquals(1, worker.getState());
        Tile build = board.getTile(0,1);
        /* set cannot active power conditions */
        build.setBlockLevel(2);

        /* build on (0,1) */
        Assert.assertTrue(worker.canBuildBlock(build));
        worker.buildBlock(build);
        Assert.assertEquals(3,build.getBlockLevel());
        /* godPower inactive */
        Assert.assertFalse(worker.canBuildBlock(build));
        Assert.assertFalse(worker.getGodPower());
    }


}