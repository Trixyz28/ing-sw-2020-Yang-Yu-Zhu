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
        worker.setPosition(board.getTile(2,4));
        Tile tile1 = board.getTile(2,3);
        Tile tile2 = board.getTile(2,2);
        tile1.setBlockLevel(0);


        Assert.assertTrue(worker.canMove(tile1));
        worker.move(tile1);
        Assert.assertTrue(conditions.checkMoveCondition(tile1, tile2));


        tile2.setBlockLevel(1);
        worker.move(tile2);
        Assert.assertFalse(conditions.checkMoveCondition(tile1, tile2));

    }


}