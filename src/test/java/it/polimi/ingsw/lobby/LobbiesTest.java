package it.polimi.ingsw.lobby;

import junit.framework.TestCase;
import org.junit.Test;


public class LobbiesTest extends TestCase {

    Lobbies lobbies = new Lobbies();

    @Test
    public void testAddPlayer() {
        lobbies.addPlayer("A");
        assertTrue(lobbies.getPlayerList().size()==1);
        assertTrue(lobbies.getPlayerList().get(0).equals("A"));

    }

    @Test
    public void testCheckAvailableLobbyFalse() {
        lobbies.addPlayer("A");
        lobbies.createLobby(2);
        lobbies.joinLobby("B");

        assertFalse(lobbies.checkAvailableLobby());

    }

    @Test
    public void testCheckAvailableLobbyTrue() {
        lobbies.addPlayer("A");
        lobbies.createLobby(2);

        assertTrue(lobbies.checkAvailableLobby());
    }


    @Test
    public void testCreateLobby() {
        lobbies.addPlayer("A");
        lobbies.createLobby(3);
        assertTrue(lobbies.getLobbyList().get(0).getLobbyID()==0);
        assertTrue(lobbies.getLobbyList().get(0).getLobbyPlayersNumber()==3);
        assertEquals("A",lobbies.getLobbyList().get(0).getPlayersNameList().get(0));
        assertEquals("0000",lobbies.getLobbyList().get(0).getPlayersNameList().get(1));
        assertEquals("0000",lobbies.getLobbyList().get(0).getPlayersNameList().get(2));

    }

    @Test
    public void testJoinLobby() {
        lobbies.addPlayer("A");
        lobbies.createLobby(3);

        lobbies.joinLobby("B");
        assertEquals("B",lobbies.getLobbyList().get(0).getPlayersNameList().get(1));
        assertFalse(lobbies.getLobbyList().get(0).isFull());

        lobbies.joinLobby("C");
        assertEquals("C",lobbies.getLobbyList().get(0).getPlayersNameList().get(2));
        assertTrue(lobbies.getLobbyList().get(0).isFull());
    }
}