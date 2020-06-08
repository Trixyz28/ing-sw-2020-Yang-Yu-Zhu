package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Test;

public class PrometheusTest extends TestCase {

    Conditions conditions = new Conditions();
    Prometheus worker = new Prometheus(new NoGod(4, conditions));

    Board board = new Board();

    @Test
    public void testMove() {
        worker.setPosition(board.getTile(1,2));
        worker.move(board.getTile(1,3));
        assertTrue(board.getTile(1,3).isOccupiedByWorker());
    }

    @Test
    public void testBuildBlock() {
        worker.setPosition(board.getTile(1,2));
        worker.buildBlock(board.getTile(2,2));
        assertEquals(1,board.getTile(2,2).getBlockLevel());
    }


    @Test
    public void testBuildDome() {
        worker.setPosition(board.getTile(1,2));
        board.getTile(1,3).setBlockLevel(3);
        worker.buildDome(board.getTile(1,3));
        assertTrue(board.getTile(1,3).isDomePresence());
    }
/*
    @Test
    public void testCanMove() {
        worker.setPosition(board.getTile(1,2));
        Tile destination = board.getTile(1,3);
        destination.setBlockLevel(1);

        boolean flag = false;
        for(Tile tile : worker.movableList()) {
            if(tile.equals(destination)) {
                flag = true;
            }
        }
        assertTrue(flag);

        worker.buildBlock(board.getTile(1,1));

        flag = false;
        for(Tile tile : worker.movableList()) {
            if(tile.equals(destination)) {
                flag = true;
            }
        }
        assertFalse(flag);



    }




 */

}