package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.GameMessage;
import junit.framework.TestCase;
import org.junit.Assert;
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
        Assert.assertEquals("hello",gameMessage.getMessage());
    }

    @Test
    public void testAnswer() {
        gameMessage.setAnswer("hellooo");
        Assert.assertEquals("hellooo",gameMessage.getAnswer());

    }

}