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
        Assert.assertEquals(0, worker.getState());
        worker.setPosition(board.getTile(1,1));
        worker.nextState();
        Assert.assertEquals(1, worker.getState());

        Assert.assertTrue(worker.canMove(board.getTile(1,2)));
        worker.move(board.getTile(1,2));
        Assert.assertTrue(board.getTile(1, 2).isOccupiedByWorker());
        Assert.assertTrue(worker.getGodPower());

        Assert.assertFalse(worker.canMove(board.getTile(1,1)));
        worker.move(board.getTile(2,3));
        Assert.assertFalse(board.getTile(1, 2).isOccupiedByWorker());
        Assert.assertTrue(board.getTile(2, 3).isOccupiedByWorker());

    }

    @Test
    public void testMove2() {
        worker.nextState();
        Assert.assertEquals(1, worker.getState());
        board.getTile(0,2).setOccupiedByWorker(true);
        board.getTile(1,0).setOccupiedByWorker(true);
        board.getTile(1,1).setOccupiedByWorker(true);
        board.getTile(1,2).setOccupiedByWorker(true);
        worker.setPosition(board.getTile(0,0));
        Assert.assertTrue(worker.canMove(board.getTile(0,1)));
        worker.move(board.getTile(0,1));

        Assert.assertFalse(worker.getGodPower());
    }




}