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


    /*
    @Test
    public void testMove() {
        worker.setPosition(board.getTile(2,4));
        Tile destination = board.getTile(2,3);



        if(worker.canMove(destination)) {
            worker.move(destination);
        }
        assertTrue(conditions.checkMoveCondition(worker.getPosition(), destination));

        destination = board.getTile(2,2);
        destination.setBlockLevel(1);
        worker.move(destination);
        assertFalse(conditions.checkMoveCondition(worker.getPosition(), destination));

    }*/


}