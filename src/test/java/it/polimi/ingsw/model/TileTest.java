package it.polimi.ingsw.model;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Tests of the <code>Tile</code>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class TileTest extends TestCase {


    /**
     * Initialize the Tile : (before testing)
     * <p>
     *     &nbsp - create a new Tile
     *     <br>
     *     &nbsp - create a new destination Tile
     *     <p>
     * </p>
     */
    Tile t = new Tile();
    Tile destination = new Tile();

    /**
     * Test of <code>setRow</code>:
     * set the row value
     * <p>
     *     results:
     *     <br>
     *     &nbsp - After the setRow the row value of the tile is 5 as expected
     *     <br>
     * </p>
     */
    @Test
    public void testRow() {
        t.setRow(5);
        assertEquals(5,t.getRow());
    }

    /**
     * Test of <code>setColumn</code>:
     * set the column value
     * <p>
     *     results:
     *     <br>
     *     &nbsp - After the setColumn the column value of the tile is 5 as expected
     *     <br>
     * </p>
     */
    @Test
    public void testColumn() {
        t.setColumn(5);
        assertEquals(5,t.getColumn());
    }

    /**
     * Test of <code>setBlockLevel</code>:
     * set the block level
     * <p>
     *     results:
     *     <br>
     *     &nbsp - After the setBlockLevel the blockLevel value of the tile is 5 as expected
     *     <br>
     * </p>
     */
    @Test
    public void testBlockLevel() {
        t.setBlockLevel(3);
        assertEquals(3,t.getBlockLevel());
    }

    /**
     * Test of <code>setOccupiedByWorker</code>:
     * set the OccupiedByWorker boolean
     * <p>
     *     results:
     *     <br>
     *     &nbsp - After the setOccupiedByWorker the value at issue of the tile is <code>true </code> as expected
     *     <br>
     * </p>
     */
    @Test
    public void testOccupiedByWorker() {
        t.setOccupiedByWorker(true);
        assertTrue(t.isOccupiedByWorker());
    }

    /**
     * Test of <code>setDomePresence</code>:
     * set the DomePresence boolean
     * <p>
     *     results:
     *     <br>
     *     &nbsp - After the setDomePresence the value at issue of the tile is <code>true </code> as expected
     *     <br>
     * </p>
     */
    @Test
    public void testSetDomePresence() {
        t.setDomePresence(true);
        assertTrue(t.isDomePresence());
    }

    /**
     * Test of <code>setOccupiedByWorker</code>:
     * checks if a tile is adjacent
     * <p>
     *     results:
     *     <br>
     *     &nbsp - t and destination are only adjacent in the case (1,1),(2,2)
     *     <br>
     *     &nbsp -tile t (1,1) -tile destination (2,3),(1,1),(2,2).
     *     <br>
     * </p>
     */
    @Test
    public void testIsAdjacentTo() {
        t.setRow(1);
        t.setColumn(1);

        destination.setRow(2);
        destination.setColumn(3);
        assertFalse(t.isAdjacentTo(destination));

        destination.setRow(1);
        destination.setColumn(1);
        assertFalse(t.isAdjacentTo(destination));

        destination.setRow(2);
        destination.setColumn(2);
        assertTrue(t.isAdjacentTo(destination));

    }

    /**
     * Test of <code>availableToMove</code>:
     * checks if a tile is available to move
     * <p>
     *     results:
     *     <br>
     *     &nbsp - destination is available to move only in the first half of the test.
     *     <br>
     *     &nbsp - in the second half the block height difference is >1
     *     <br>
     * </p>
     */
    @Test
    public void testAvailableToMove() {
        testIsAdjacentTo();
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

    /**
     * Test of <code>availableToBuild</code>:
     * checks if a tile is available to build
     * <p>
     *     results:
     *     <br>
     *     &nbsp - destination is available to move only in the first half of the test.
     *     <br>
     *     &nbsp - in the second half it's occupied by a opponent worker
     *     <br>
     * </p>
     */
    @Test
    public void testAvailableToBuild() {
        testIsAdjacentTo();

        destination.setDomePresence(false);
        destination.setOccupiedByWorker(false);
        assertTrue(t.availableToBuild(destination));

        destination.setOccupiedByWorker(true);
        assertFalse(t.availableToBuild(destination));
    }

    /**
     * Test of <code>addBlockLevel</code>:
     * checks if a tile is available to have a block added
     * <p>
     *     results:
     *     <br>
     *     &nbsp - t changed block height from 1 to 2
     *     <br>
     * </p>
     */
    @Test
    public void testAddBlockLevel() {
        t.setBlockLevel(1);
        t.addBlockLevel();
        assertEquals(2,t.getBlockLevel());
    }

    /**
     * Test of <code>BlockToDome</code>:
     * checks if a tile is available to have a dome built on
     * <p>
     *     results:
     *     <br>
     *     &nbsp - t has a dome
     *     <br>
     * </p>
     */
    @Test
    public void testBlockToDome() {
        t.blockToDome();
        assertTrue(t.isDomePresence());
    }

    /**
     * Test of <code>FreeWorker</code>:
     * checks if a tile can be freed by its worker
     *     results:
     *     <br>
     *     &nbsp - t doesn't have a worker anymore
     *     <br>
     * </p>
     */
    @Test
    public void testFreeWorker() {
        t.freeWorker();
        assertFalse(t.isOccupiedByWorker());
    }

    /**
     * Test of <code>SetWorker</code>:
     * checks if a tile can be occupied by a worker
     *     results:
     *     <br>
     *     &nbsp - t does have a worker now
     *     <br>
     * </p>
     */
    @Test
    public void testSetWorker() {
        t.putWorker();
        assertTrue(t.isOccupiedByWorker());
    }

}