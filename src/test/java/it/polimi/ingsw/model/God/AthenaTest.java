package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Test;

public class AthenaTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    Athena worker = new Athena(new NoGod(0, conditions));
    private boolean Tile;


    @Test
    public void testMove() {
        worker.setPosition(board.getTile(2,4));
        Tile destination = board.getTile(2,3);

        for(Tile t : worker.canMove()) {
            if(t.equals(destination)) {
                worker.move(t);
                break;
            }
        }
        assertTrue(conditions.canMoveUp());

        destination = board.getTile(2,2);
        destination.setBlockLevel(1);
        worker.move(destination);
        assertFalse(conditions.canMoveUp());

    }


}