package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class HeraTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Hera worker = new Hera(new NoGod(0, conditions));
    Tile initial = board.getTile(1,1);
    Tile dest = board.getTile(0,2);

    @Test
    public void testCheckWin() {
        /* set win conditions */
        initial.setBlockLevel(2);
        dest.setBlockLevel(3);
        /* worker on (1,1) */
        worker.setPosition(initial);
        /* move to (0,2) */
        Assert.assertTrue(worker.canMove(dest));
        worker.move(dest);
        /* worker wins also moved to a perimeter tile */
        Assert.assertEquals(dest, worker.getPosition());
        Assert.assertTrue(worker.checkWin(initial));
        /* condition of opponent workers */
        Assert.assertFalse(conditions.checkWinCondition(dest));
    }

    @Test
    public void testOpponentCheckWin() {
        /* set win conditions */
        initial.setBlockLevel(2);
        dest.setBlockLevel(3);

        /* test opponent (1,1) */
        UndecoratedWorker opponent = new NoGod(1, conditions);
        opponent.setPosition(initial);
        /* move to (0,2) */
        Assert.assertTrue(opponent.canMove(dest));
        opponent.move(dest);
        /* cannot win -> Hera's Power */
        Assert.assertFalse(opponent.checkWin(initial));

        /* if Hera lose, update Hera's conditions */
        conditions.update(worker.getBelongToPlayer());
        /* opponent can win */
        Assert.assertTrue(opponent.checkWin(initial));

    }
}