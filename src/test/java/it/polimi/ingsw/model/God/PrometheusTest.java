package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Turn;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class PrometheusTest extends TestCase {

    Conditions conditions = new Conditions();
    Prometheus worker = new Prometheus(new NoGod(2, conditions));
    Board board = new Board();

    @Test
    public void testGodPower() {

        /* worker (0,0) */
        worker.setPosition(board.getTile(0,0));
        /* Prometheus can move/build to (0,1) (1,0) (1,1) */
        Assert.assertEquals(3, new Turn(new Player("A")).movableList(worker).size());
        /* godPower active -> can build + can move */
        Assert.assertTrue(worker.getGodPower());

        /* test Prometheus(0,0) vs Limus(1,1) */
        Limus limus = new Limus(new NoGod(1, conditions));
        limus.setPosition(board.getTile(1,1));

        /* Prometheus can move to (0,1) (1,0) */
        Assert.assertEquals(2, new Turn(new Player("A")).movableList(worker).size());
        /* Prometheus cannot build on any adjacent tiles -> can only move */
        Assert.assertFalse(worker.getGodPower());

    }

    @Test
    public void testMoveBuild() {
        /* worker (1,2) */
        worker.setPosition(board.getTile(1,2));
        /* move first -> state = 1 */
        worker.useGodPower(false);
        Assert.assertEquals(1, worker.getState());

        /* move to (1,3) */
        Assert.assertTrue(worker.canMove(board.getTile(1,3)));
        worker.move(board.getTile(1,3));
        Assert.assertTrue(board.getTile(1,3).isOccupiedByWorker());
        /* godPower inactive (already used) */
        Assert.assertFalse(worker.getGodPower());

        /* build state -> 2 */
        worker.nextState();
        Assert.assertEquals(2,worker.getState());
        /* build on (2,2) */
        Assert.assertTrue(worker.canBuildBlock(board.getTile(2,2)));
        worker.buildBlock(board.getTile(2,2));
        assertEquals(1,board.getTile(2,2).getBlockLevel());

        worker.nextState();
        Assert.assertEquals(0, worker.getState());

    }

    @Test
    public void testBuildMove() {
        /* set can build dome condition */
        board.getTile(2,2).setBlockLevel(3);
        /* worker(1,2) */
        worker.setPosition(board.getTile(1,2));
        Assert.assertTrue(worker.canMove(board.getTile(1,3)));
        /* GodPower active -> can move + can build */
        Assert.assertTrue(worker.getGodPower());

        /* build first */
        worker.useGodPower(true);
        Assert.assertEquals(0, worker.getState());
        worker.setGodPower(false);
        Assert.assertEquals(2, worker.getState());

        /* build dome on (2,2)  */
        Assert.assertTrue(worker.canBuildDome(board.getTile(2,2)));
        worker.buildDome(board.getTile(2,2));
        assertTrue(board.getTile(2,2).isDomePresence());

        /* move state -> 1 */
        worker.nextState();
        Assert.assertEquals(1, worker.getState());

        /* check can move up condition after build */
        board.getTile(2,1).setBlockLevel(1);
        /* worker can't move up */
        Assert.assertFalse(worker.canMove(board.getTile(2,1)));

        /* move to (1,2) */
        Assert.assertTrue(worker.canMove(board.getTile(1,3)));
        worker.move(board.getTile(1,3));

    }



}