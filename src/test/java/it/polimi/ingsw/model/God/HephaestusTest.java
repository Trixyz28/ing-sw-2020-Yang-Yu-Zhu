package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Test;

public class HephaestusTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Hephaestus worker = new Hephaestus(new NoGod(conditions));

    @Test
    public void testMove() {
        worker.setPosition(board.getTile(0,0));
        worker.move(board.getTile(0,1));
        assertTrue(board.getTile(0,1).isOccupiedByWorker());
    }

    @Test
    public void testBuildBlock() {
        worker.setPosition(board.getTile(0,0));
        Tile build = board.getTile(0,1);
        worker.buildBlock(build);
        assertEquals(1,board.getTile(0,1).getBlockLevel());
        worker.buildBlock(board.getTile(1,0));
        assertEquals(2,build.getBlockLevel());
        assertEquals(0,board.getTile(1,0).getBlockLevel());
    }


}