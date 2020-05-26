package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import junit.framework.TestCase;
import org.junit.Test;

public class AtlasTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Atlas worker = new Atlas(new NoGod(conditions));

    @Test
    public void testBuild() {
        worker.setPosition(board.getTile(0, 1));
        assertTrue(worker.canBuildBlock(board.getTile(1, 1)));
        assertFalse(worker.canBuildBlock(board.getTile(2, 2)));

        board.getTile(1, 1).setBlockLevel(2);
        assertTrue(worker.canBuildDome(board.getTile(1, 1)));
        assertFalse(worker.canBuildDome(board.getTile(2,2)));
    }

}