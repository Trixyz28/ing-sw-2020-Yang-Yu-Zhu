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
        worker.setPosition(board.getTile(0,2));
        worker.move(board.getTile(0,1));
        Assert.assertTrue(board.getTile(0,1).isOccupiedByWorker());
    }

    @Test
    public void testBuildBlock() {
        worker.setPosition(board.getTile(0,2));
        Tile build = board.getTile(0,1);

        Assert.assertTrue(worker.canBuildBlock(build));
        worker.buildBlock(build);

        Assert.assertFalse(worker.canBuildBlock(build));
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,3)));

        worker.buildBlock(board.getTile(0,3));
        Assert.assertTrue(worker.getGodPower());
        worker.nextState();  /* aggiornare counter */
        board.getTile(1,2).setBlockLevel(3);
        Assert.assertTrue(worker.canBuildDome(board.getTile(1,2)));
        worker.buildDome(board.getTile(1,2));
        Assert.assertTrue(worker.getGodPower());

    }

    @Test
    public void testBuildDome() {
        worker.nextState();
        Assert.assertEquals(1, worker.getState());
        worker.setPosition(board.getTile(4,4));
        Tile build = board.getTile(4,3);
        build.setBlockLevel(3);
        board.getTile(3,3).setOccupiedByWorker(true);
        board.getTile(3,4).setDomePresence(true);

        Assert.assertTrue(worker.canBuildDome(build));
        Assert.assertFalse(worker.getGodPower());
        worker.buildDome(build);

        Assert.assertFalse(worker.canBuildDome(build));
        Assert.assertFalse(worker.canBuildBlock(build));
        Assert.assertFalse(worker.canBuildDome(board.getTile(3,4)));
        Assert.assertFalse(worker.canBuildBlock(board.getTile(3,4)));
        Assert.assertFalse(worker.canBuildDome(board.getTile(3,3)));
        Assert.assertFalse(worker.canBuildBlock(board.getTile(3,3)));
        Assert.assertFalse(worker.getGodPower());

    }

}