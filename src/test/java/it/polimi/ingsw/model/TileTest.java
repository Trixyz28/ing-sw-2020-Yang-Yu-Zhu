package it.polimi.ingsw.model;

import junit.framework.TestCase;

import org.junit.Test;


public class TileTest extends TestCase {

    Tile t = new Tile();
    Tile destination = new Tile();


    @Test
    public void testRow() {
        t.setRow(5);
        assertEquals(5,t.getRow());
    }


    @Test
    public void testColumn() {
        t.setColumn(5);
        assertEquals(5,t.getColumn());
    }


    @Test
    public void testBlockLevel() {
        t.setBlockLevel(3);
        assertEquals(3,t.getBlockLevel());
    }


    @Test
    public void testOccupiedByWorker() {
        t.setOccupiedByWorker(true);
        assertTrue(t.isOccupiedByWorker());
    }


    @Test
    public void testSetDomePresence() {
        t.setDomePresence(true);
        assertTrue(t.isDomePresence());
    }


    @Test
    public void testAdjacentTile() {
        t.setRow(1);
        t.setColumn(1);

        destination.setRow(2);
        destination.setColumn(3);
        assertFalse(t.adjacentTile(destination));

        destination.setRow(2);
        destination.setColumn(2);
        assertTrue(t.adjacentTile(destination));

    }


    @Test
    public void testAvailableToMove() {
        testAdjacentTile();
        t.setBlockLevel(0);

        destination.setBlockLevel(1);
        destination.setDomePresence(false);
        destination.setOccupiedByWorker(false);
        assertTrue(t.availableToMove(destination));

        destination.setBlockLevel(2);
        destination.setDomePresence(false);
        destination.setOccupiedByWorker(false);
        assertFalse(t.availableToMove(destination));
    }


    @Test
    public void testAvailableToBuild() {
        testAdjacentTile();

        destination.setDomePresence(false);
        destination.setOccupiedByWorker(false);
        assertTrue(t.availableToBuild(destination));

        destination.setOccupiedByWorker(true);
        assertFalse(t.availableToBuild(destination));
    }


    @Test
    public void testAddBlockLevel() {
        t.setBlockLevel(1);
        t.addBlockLevel();
        assertEquals(2,t.getBlockLevel());
    }


    @Test
    public void testBlockToDome() {
        t.blockToDome();
        assertTrue(t.isDomePresence());
    }


    @Test
    public void testFreeWorker() {
        t.freeWorker();
        assertFalse(t.isOccupiedByWorker());
    }


    @Test
    public void testSetWorker() {
        t.setWorker();
        assertTrue(t.isOccupiedByWorker());
    }

}