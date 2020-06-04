package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Test;

public class DemeterTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Demeter worker = new Demeter(new NoGod(0, conditions));


    @Test
    public void testMove() {
        worker.setPosition(board.getTile(0,2));
        worker.move(board.getTile(0,1));
        assertTrue(board.getTile(0,1).isOccupiedByWorker());
    }

    @Test
    public void testBuildBlock() {
        worker.setPosition(board.getTile(0,2));
        Tile build = board.getTile(0,1);

        assertTrue(worker.canBuildBlock(build));
        worker.buildBlock(build);
        assertSame(build,worker.getOriginalBuild());

        assertFalse(worker.canBuildBlock(build));
        assertTrue(worker.canBuildBlock(board.getTile(0,3)));

        worker.buildBlock(board.getTile(0,3));
        assertSame(board.getTile(0,1),worker.getOriginalBuild());
        board.getTile(1,2).setBlockLevel(3);
        assertTrue(worker.canBuildDome(board.getTile(1,2)));
        worker.buildDome(board.getTile(1,2));

    }

}