package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class PrometheusTest extends TestCase {

    Conditions conditions = new Conditions();
    Prometheus worker = new Prometheus(new NoGod(4, conditions));
    Board board = new Board();

    @Test
    public void testMoveBuild() {
        worker.useGodPower(false);
        Assert.assertEquals(1, worker.getState());
        worker.setPosition(board.getTile(1,2));
        Assert.assertTrue(worker.canMove(board.getTile(1,3)));
        worker.move(board.getTile(1,3));
        Assert.assertTrue(board.getTile(1,3).isOccupiedByWorker());
        Assert.assertFalse(worker.getGodPower());
        worker.nextState();
        Assert.assertEquals(2,worker.getState());
        Assert.assertTrue(worker.canBuildBlock(board.getTile(2,2)));
        worker.buildBlock(board.getTile(2,2));
        assertEquals(1,board.getTile(2,2).getBlockLevel());
        worker.nextState();
        Assert.assertEquals(0, worker.getState());

    }

    @Test
    public void testBuildMove() {
        board.getTile(2,2).setBlockLevel(3);
        worker.setPosition(board.getTile(1,2));
        Assert.assertTrue(worker.canMove(board.getTile(1,3)));
        Assert.assertTrue(worker.getGodPower());
        worker.useGodPower(true);
        Assert.assertEquals(0, worker.getState());
        worker.setGodPower(false);
        Assert.assertEquals(2, worker.getState());
        Assert.assertTrue(worker.canBuildDome(board.getTile(2,2)));
        worker.buildDome(board.getTile(2,2));
        assertTrue(board.getTile(2,2).isDomePresence());
        worker.nextState();
        Assert.assertEquals(1, worker.getState());
        Assert.assertFalse(worker.canMove(board.getTile(2,2)));
        Assert.assertTrue(worker.canMove(board.getTile(1,3)));
        worker.move(board.getTile(1,3));

    }



}