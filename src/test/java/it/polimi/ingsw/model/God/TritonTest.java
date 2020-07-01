package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class TritonTest extends TestCase {

    Conditions conditions = new Conditions();
    Triton worker = new Triton(new NoGod(0, conditions));
    Board board = new Board();

    @Test
    public void testMove() {
        worker.setPosition(board.getTile(0,0));
        Assert.assertTrue(worker.canMove(board.getTile(1,1)));
        worker.move(board.getTile(1,1));
        /* (1,1) isn't a perimeter tile */
        Assert.assertFalse(worker.getGodPower());
    }

    @Test
    public void testMove2() {
        worker.setPosition(board.getTile(0,0));
        Assert.assertTrue(worker.canMove(board.getTile(1,0)));
        worker.move(board.getTile(1,0));
        /* Triton can move again because (1,0) is perimeter tile and there are also other movable tiles */
        Assert.assertTrue(worker.getGodPower());
    }

    @Test
    public void testMove3() {
        worker.setPosition(board.getTile(0,1));
        board.getTile(0,1).setBlockLevel(2);
        board.getTile(1,1).setOccupiedByWorker(true);
        board.getTile(1,0).setOccupiedByWorker(true);
        Assert.assertTrue(worker.canMove(board.getTile(0,0)));
        worker.move(board.getTile(0,0));
        /* Triton can't move again because there aren't any movable tiles */
        Assert.assertFalse(worker.getGodPower());
    }
}