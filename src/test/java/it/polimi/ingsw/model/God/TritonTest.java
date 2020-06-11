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
        Assert.assertFalse(worker.getGodPower());
    }

    @Test
    public void testMove2() {
        worker.setPosition(board.getTile(0,0));
        Assert.assertTrue(worker.canMove(board.getTile(1,0)));
        worker.move(board.getTile(1,0));
        Assert.assertTrue(worker.getGodPower());
    }
}