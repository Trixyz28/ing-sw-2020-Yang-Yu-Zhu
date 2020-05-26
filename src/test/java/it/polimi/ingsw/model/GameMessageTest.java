package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

public class GameMessageTest extends TestCase {

    Player player = new Player("A");
    GameMessage gameMessage = new GameMessage(player,"hello");

    @Test
    public void testPlayer() {
        assertEquals("A",gameMessage.getPlayer());
    }

    @Test
    public void testMessage() {
        assertEquals("hello",gameMessage.getMessage());
    }

    @Test
    public void testAnswer() {
        gameMessage.setAnswer("hellooo");
        assertEquals("hellooo",gameMessage.getAnswer());
    }

}