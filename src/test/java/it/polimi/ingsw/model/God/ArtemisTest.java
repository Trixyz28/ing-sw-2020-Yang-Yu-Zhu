package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;


public class ArtemisTest {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Artemis worker = new Artemis(new NoGod(1, conditions));


    /*
    @Test
    public void testMove() {
        worker.setPosition(board.getTile(1,1));
        //assertEquals(8,worker.movableList().size());

        worker.move(board.getTile(1,2));
        Assert.assertTrue(board.getTile(1, 2).isOccupiedByWorker());
        Assert.assertSame(board.getTile(1, 1), worker.getOriginalTile());
        Assert.assertTrue(worker.getGodPower());
        //assertEquals(7,worker.movableList().size());

        worker.move(board.getTile(2,3));
        Assert.assertFalse(board.getTile(1, 2).isOccupiedByWorker());
        Assert.assertTrue(board.getTile(2, 3).isOccupiedByWorker());

    }*/



    @Test
    public void testCanBuildBlock() {
        worker.setPosition(board.getTile(1,1));
        Assert.assertTrue(worker.canBuildBlock(board.getTile(1, 0)));
    }


}