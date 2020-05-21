package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

public class PlayerTest extends TestCase {

    Player player = new Player("Abc");


    @Test
    public void testPlayerNickname() {
        assertEquals("Abc",player.getPlayerNickname());
    }

    @Test
    public void testPlayerID() {
        player.setPlayerID(1);
        assertEquals(1,player.getPlayerID());
    }

    @Test
    public void testChallenger() {
        player.setChallenger(true);
        assertTrue(player.isChallenger());
    }

    @Test
    public void testGodChoice() {
        player.godChoice("APOLLO");
        assertEquals("APOLLO",player.getGodCard());
    }


    public void testCreateWorker() {
    }

    public void testChooseWorker() {

    }

    public void testDeleteWorker() {
    }
}