package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class AthenaTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Athena worker = new Athena(new NoGod(0, conditions));



    @Test
    public void testMove() {
        /* worker(2,4) */
        worker.setPosition(board.getTile(2,4));
        Tile tile1 = board.getTile(2,3);
        Tile tile2 = board.getTile(2,2);
        tile1.setBlockLevel(0);

        /* worker moves to (2,3) */
        Assert.assertTrue(worker.canMove(tile1));
        worker.move(tile1);
        /* worker didn't move up -> no can't move up conditions */
        Assert.assertTrue(conditions.checkMoveCondition(tile1, tile2));

        /* (2,2).level = 1 */
        tile2.setBlockLevel(1);
        Assert.assertTrue(worker.canMove(tile2));
        worker.move(tile2);
        /* worker moved up -> can't move up condition ON (to opponent players) */
        Assert.assertFalse(conditions.checkMoveCondition(tile1, tile2));

    }


}