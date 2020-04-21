package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

public class PlayerTest extends TestCase {

    Player player = new Player();


    @Test
    public void testPlayerNickname() {
        player.setPlayerNickname("Abc");
        assertEquals("Abc",player.getPlayerNickname());
    }

    @Test
    public void testPlayerID() {
        player.setPlayerID(1);
        assertEquals(1,player.getPlayerID());
    }

    @Test
    public void testChallenger() {
        player.setChallenger();
        assertTrue(player.isChallenger());
    }

    public void testDefineGodList() {
    }

    @Test
    public void testGodChoice() {
        player.godChoice("APOLLO");
        assertEquals("APOLLO",player.getGodCard());
    }

    public void testChooseStartPlayer() {
    }

    public void testCreateWorker() {
    }

    public void testChooseWorker() {
    }

    public void testLosingCondition() {
    }






    public void testDeleteWorker() {
    }
}