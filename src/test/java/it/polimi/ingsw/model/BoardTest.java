package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BoardTest extends TestCase {

    Board board = new Board();

    @Test
    public void testInitializeTiles() {
        board.initializeTiles();

        Tile t = board.getTile(1,1);
        assertSame(t, board.getMap()[1][1]);

        assertEquals(1,t.getRow());
        assertEquals(1,t.getColumn());
        assertEquals(0,t.getBlockLevel());
        assertFalse(t.isOccupiedByWorker());
        assertFalse(t.isDomePresence());


    }


    @Test
    public void testAdjacent() {
        board.initializeTiles();

        Tile t1 = board.getTile(1,1);
        List adjacentTot1 = board.getAdjacentList(t1);

        assertEquals(8,adjacentTot1.size());
        assertSame(board.getTile(0,0),adjacentTot1.get(0));
        assertSame(board.getTile(0,1),adjacentTot1.get(1));
        assertSame(board.getTile(0,2),adjacentTot1.get(2));
        assertSame(board.getTile(1,0),adjacentTot1.get(3));
        assertSame(board.getTile(1,2),adjacentTot1.get(4));
        assertSame(board.getTile(2,0),adjacentTot1.get(5));
        assertSame(board.getTile(2,1),adjacentTot1.get(6));
        assertSame(board.getTile(2,2),adjacentTot1.get(7));


        Tile t2 = board.getTile(4,4);
        List adjacentTot2 = board.getAdjacentList(t2);

        assertEquals(3,adjacentTot2.size());
        assertSame(board.getTile(3,3),adjacentTot2.get(0));
        assertSame(board.getTile(3,4),adjacentTot2.get(1));
        assertSame(board.getTile(4,3),adjacentTot2.get(2));

    }


}