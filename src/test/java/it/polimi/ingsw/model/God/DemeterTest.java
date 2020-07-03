package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class DemeterTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Demeter worker = new Demeter(new NoGod(0, conditions));


    @Test
    public void testMove() {
        /* worker (0,2) */
        worker.setPosition(board.getTile(0,2));
        /* normal move to (0,1) */
        Assert.assertTrue(worker.canMove(board.getTile(0,1)));
        worker.move(board.getTile(0,1));
        Assert.assertTrue(board.getTile(0,1).isOccupiedByWorker());
    }

    @Test
    public void testBuildBlock() {
        /* worker (0,2) */
        worker.setPosition(board.getTile(0,2));
        Tile build = board.getTile(0,1);

        /* build on (0,1) */
        Assert.assertTrue(worker.canBuildBlock(build));
        worker.buildBlock(build);

        /* Power ON -> can build an additional time */
        Assert.assertTrue(worker.getGodPower());
        /* Demeter can't build again to the same tile (0,1) */
        Assert.assertFalse(worker.canBuildBlock(build));

        /* build on (0,3) */
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,3)));
        worker.buildBlock(board.getTile(0,3));

        /* update counters */
        worker.nextState();

        /* tests to build a dome */
        worker.setGodPower(false);
        /* set build dome conditions */
        board.getTile(1,2).setBlockLevel(3);
        /* build dome on (1,2) */
        Assert.assertTrue(worker.canBuildDome(board.getTile(1,2)));
        worker.buildDome(board.getTile(1,2));
        /* power on -> can build again */
        Assert.assertTrue(worker.getGodPower());

    }

    @Test
    public void testBuildDome() {
        /* worker (4,4) */
        worker.setPosition(board.getTile(4,4));
        worker.nextState();
        Assert.assertEquals(1, worker.getState());

        /* set build dome conditions */
        Tile build = board.getTile(4,3);
        build.setBlockLevel(3);
        /* set can't build again conditions */
        board.getTile(3,3).setOccupiedByWorker(true);
        board.getTile(3,4).setDomePresence(true);

        /* worker can build dome on (4,3) */
        Assert.assertTrue(worker.canBuildDome(build));
        Assert.assertFalse(worker.getGodPower());
        worker.buildDome(build);

        /* power inactive -> no other tiles available to build again */
        Assert.assertFalse(worker.canBuildDome(build));
        Assert.assertFalse(worker.canBuildBlock(build));
        Assert.assertFalse(worker.canBuildDome(board.getTile(3,4)));
        Assert.assertFalse(worker.canBuildBlock(board.getTile(3,4)));
        Assert.assertFalse(worker.canBuildDome(board.getTile(3,3)));
        Assert.assertFalse(worker.canBuildBlock(board.getTile(3,3)));
        Assert.assertFalse(worker.getGodPower());

    }

}