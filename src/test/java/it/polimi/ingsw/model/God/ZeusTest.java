package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class ZeusTest extends TestCase {

    Conditions conditions = new Conditions();
    Zeus worker = new Zeus(new NoGod(0, conditions));
    Board board = new Board();

    @Test
    public void testCanBuildBlock() {
        /* worker (0,0) */
        worker.setPosition(board.getTile(0,0));
        /* worker can Op on his position */
        Assert.assertTrue(worker.getAdjacentTiles().contains(worker.getPosition()));
        /* build on (0,0) */
        Assert.assertTrue(worker.canBuildBlock(worker.getPosition()));
        worker.buildBlock(worker.getPosition());
        Assert.assertEquals(1, board.getTile(0,0).getBlockLevel());

    }
}