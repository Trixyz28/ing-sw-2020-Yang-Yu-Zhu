package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.GameMessage;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class GameMessageTest extends TestCase {

    GameMessage gameMessage = new GameMessage("hello");


    /**
     * Test: simple gameMessage getting
     */
    @Test
    public void testMessage() {
        Assert.assertEquals("hello",gameMessage.getMessage());
    }

    /**
     * Test: simple answer setting
     */
    @Test
    public void testAnswer() {
        gameMessage.setAnswer("hellooo");
        Assert.assertEquals("hellooo",gameMessage.getAnswer());

    }

}