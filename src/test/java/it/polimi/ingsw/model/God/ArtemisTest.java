package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;


public class ArtemisTest {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Artemis worker = new Artemis(new NoGod(1, conditions));



    @Test
    public void testMove() {
        /* set worker position (1,1) */
        Assert.assertEquals(0, worker.getState());
        worker.setPosition(board.getTile(1,1));
        /* state 0 -> 1 */
        worker.nextState();
        Assert.assertEquals(1, worker.getState());

        /* worker(1,1) moves to (1,2) */
        Assert.assertTrue(worker.canMove(board.getTile(1,2)));
        worker.move(board.getTile(1,2));
        Assert.assertTrue(board.getTile(1, 2).isOccupiedByWorker());
        Assert.assertEquals(board.getTile(1,2), worker.getPosition());
        /* GodPower on -> move again */
        Assert.assertTrue(worker.getGodPower());

        /* worker can't move back to the initial position (1,1) */
        Assert.assertFalse(worker.canMove(board.getTile(1,1)));

        /* worker(1,2) moves to (2,3) */
        Assert.assertTrue(worker.canMove(board.getTile(2,3)));
        worker.move(board.getTile(2,3));
        Assert.assertFalse(board.getTile(1, 2).isOccupiedByWorker());
        Assert.assertTrue(board.getTile(2, 3).isOccupiedByWorker());

    }

    @Test
    public void testMove2() {
        worker.setPosition(board.getTile(0,0));
        /* state 0 -> 1 */
        worker.nextState();
        Assert.assertEquals(1, worker.getState());

        /* set worker can't move again conditions */
        board.getTile(0,2).setOccupiedByWorker(true);
        board.getTile(1,0).setOccupiedByWorker(true);
        board.getTile(1,1).setOccupiedByWorker(true);
        board.getTile(1,2).setOccupiedByWorker(true);

        /* worker(0,0) moves to (0,1)  */
        Assert.assertTrue(worker.canMove(board.getTile(0,1)));
        worker.move(board.getTile(0,1));

        /* GodPower inactive -> no tiles to move again */
        Assert.assertFalse(worker.getGodPower());
    }




}