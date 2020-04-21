package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

public class LobbyTest extends TestCase {

    Lobby lobby = new Lobby();

    @Test
    public void testLobbyID() {
        lobby.setLobbyID("A");
        assertEquals("A",lobby.getLobbyID());
    }

    /*
    @Test
    public void testAddPlayer() {
        lobby.addPlayer("Player1");
        assertEquals("Player1",lobby.getPlayersNameList().get(0));
    }

    @Test
    public void testIsFull() {
        lobby.setAvailablePlayers(2);
        assertTrue(lobby.isFull());
    }

    @Test
    public void testAvailablePlayers() {
        lobby.setAvailablePlayers(1);
        assertEquals(1,lobby.getAvailablePlayers());
    }
*/
}