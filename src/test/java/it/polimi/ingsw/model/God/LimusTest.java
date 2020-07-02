package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class LimusTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Limus worker = new Limus(new NoGod(0, conditions));

    @Test
    public void testCanBuildBlock() {
        /* worker (0,0) */
        worker.setPosition(board.getTile(0,0));
        /* can build normally */
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,1)));
        /* opponent worker cannot build next to Limus(0,0) */
        Assert.assertFalse(conditions.checkBuildCondition(board.getTile(1,1)));
    }

    @Test
    public void testLose() {
        /* test opponent worker (1,1) */
        NoGod opponent = new NoGod(1, conditions);
        opponent.setPosition(board.getTile(1,1));
        /* Limus worker on (0,0) */
        worker.setPosition(board.getTile(0,0));

        /* opponent worker cannot build next to Limus(0,0) */
        Assert.assertFalse(opponent.canBuildBlock(board.getTile(0,1)));
        /* if Limus loses, update Limus conditions */
        conditions.update(worker.getBelongToPlayer());
        /* opponent can build on (0,1) */
        Assert.assertTrue(opponent.canBuildBlock(board.getTile(0,1)));



    }
}