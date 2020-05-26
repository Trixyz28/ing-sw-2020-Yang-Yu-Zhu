package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class NoGodTest extends TestCase {

    UndecoratedWorker noGod = new NoGod();
    Board board = new Board();
    Tile t = new Tile();
    Tile destination = new Tile();


    @Test
    public void testPosition() {
        t = board.getTile(2,1);
        t.setBlockLevel(1);

        noGod.setPosition(t);
        assertEquals(t,noGod.getPosition());
    }

    @Test
    public void testMove() {
        board.initializeTiles();
        testPosition();
        destination = board.getTile(1,2);
        destination.setBlockLevel(2);

        List<Tile> movableTiles = new ArrayList<>();
        movableTiles = noGod.canMove(true);

        boolean flag = false;
        for(Tile tile : movableTiles) {
            if(tile.equals(destination)) {
                flag = true;
            }
        }

        assertTrue(flag);

        assertTrue(noGod.getPosition().isOccupiedByWorker());
        noGod.move(destination);
        assertFalse(board.getTile(2,1).isOccupiedByWorker());

    }

    @Test
    public void testBuild() {
        testPosition();
        destination.setRow(1);
        destination.setColumn(2);
        destination.setBlockLevel(2);

        assertTrue(noGod.canBuildBlock(destination));
        assertFalse(noGod.canBuildDome(destination));

        noGod.buildBlock(destination);
        assertFalse(noGod.canBuildBlock(destination));
        assertTrue(noGod.canBuildDome(destination));

        assertFalse(t.isDomePresence());
        noGod.buildDome(destination);
        assertTrue(destination.isDomePresence());
    }





    @Test
    public void testGodPower() {
        noGod.setGodPower(true);
        assertTrue(noGod.getGodPower());
    }


}