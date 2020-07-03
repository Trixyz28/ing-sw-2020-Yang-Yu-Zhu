package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.Apollo;
import it.polimi.ingsw.model.God.Artemis;
import it.polimi.ingsw.model.God.UndecoratedWorker;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class PlayerTest extends TestCase {

    Player player = new Player("Abc");


    /**
     * Test: simple player name testing
     */
    @Test
    public void testPlayerNickname() {
        assertEquals("Abc",player.getPlayerNickname());
    }

    /**
     * Test: simple playerID testing
     */
    @Test
    public void testPlayerID() {
        player.setPlayerID(1);
        assertEquals(1,player.getPlayerID());
    }

    /**
     * Test: setting challenger
     */
    @Test
    public void testChallenger() {
        player.setChallenger(true);
        assertTrue(player.isChallenger());
    }

    /**
     * Test: god choice
     */
    @Test
    public void testGodChoice() {
        player.setGodCard("APOLLO");
        assertEquals("APOLLO",player.getGodCard());
    }

    /**
     * Test: create workers and setup workerList
     */
    @Test
    public void testCreateApollo() {
        /* create 2 workers Apollo */
        ArrayList<UndecoratedWorker> totalWorkerList = new ArrayList<>();
        player.createWorker("APOLLO", null, totalWorkerList);
        assertTrue(player.getWorkerList().get(0) instanceof Apollo);
        assertTrue(player.chooseWorker(0) instanceof Apollo);
        assertTrue(player.getWorkerList().get(1) instanceof Apollo);
        assertTrue(player.chooseWorker(1) instanceof Apollo);
    }

    /**
     * Test: create a single worker
     */
    @Test
    public void testCreateArtemis() {
        ArrayList<UndecoratedWorker> totalWorkerList = new ArrayList<>();
        player.createWorker("ARTEMIS", null, totalWorkerList);
        assertTrue(player.getWorkerList().get(0) instanceof Artemis);
    }

    /**
     * Test: simulating a worker delete process
     */
    @Test
    public void testDeleteWorker() {
        testCreateApollo();
        Tile t1 = new Tile();
        t1.setRow(0);
        t1.setColumn(1);

        Tile t2 = new Tile();
        t2.setRow(3);
        t2.setColumn(3);

        /* set positions */
        player.getWorkerList().get(0).setPosition(t1);
        player.getWorkerList().get(1).setPosition(t2);
        assertTrue(t1.isOccupiedByWorker());

        /* delete workers */
        player.deleteWorker();
        assertFalse(t1.isOccupiedByWorker());
        assertFalse(t2.isOccupiedByWorker());
    }




}