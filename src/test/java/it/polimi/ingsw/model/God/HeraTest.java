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
        initial.setBlockLevel(2);
        dest.setBlockLevel(3);
        worker.setPosition(initial);
        worker.move(dest);
        Assert.assertEquals(dest, worker.getPosition());
        Assert.assertTrue(worker.checkWin(initial));
        Assert.assertFalse(conditions.checkWinCondition(dest));
    }

    @Test
    public void testLose() {
        initial.setBlockLevel(2);
        dest.setBlockLevel(3);
        UndecoratedWorker opponent = new NoGod(1, conditions);
        opponent.setPosition(initial);
        opponent.move(dest);
        Assert.assertFalse(opponent.checkWin(initial));
        conditions.update(worker.getBelongToPlayer());
        Assert.assertTrue(opponent.checkWin(initial));

    }
}