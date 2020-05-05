package it.polimi.ingsw.lobby;

import it.polimi.ingsw.lobby.Lobby;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class LobbyTest extends TestCase {

    Lobby lobby = new Lobby();

    @Test
    public void testLobbyID() {
        lobby.setLobbyID(1);
        assertEquals(1,lobby.getLobbyID());
    }

    @Test
    public void testLobbyPlayersNumber() {
        lobby.setLobbyPlayersNumber(3);
        assertEquals(3,lobby.getLobbyPlayersNumber());
    }

    @Test
    public void testFull() {
        lobby.setFull(true);
        assertTrue(lobby.isFull());
        lobby.setFull(false);
        assertFalse(lobby.isFull());
    }


    @Test
    public void testPlayersNameList() {
        ArrayList<String> parameterList = new ArrayList<>();
        parameterList.add("A");
        parameterList.add("B");

        lobby.setPlayersNameList(parameterList);
        assertEquals("A",lobby.getPlayersNameList().get(0));
        assertEquals("B",lobby.getPlayersNameList().get(1));
    }

    @Test
    public void testAddPlayer() {
        lobby.setLobbyPlayersNumber(3);

        ArrayList<String> parameterList = new ArrayList<>();
        parameterList.add("A");
        parameterList.add("0000");
        parameterList.add("0000");
        lobby.setPlayersNameList(parameterList);

        lobby.addPlayer("B");
        assertEquals("B",lobby.getPlayersNameList().get(1));
        assertEquals(2,lobby.getAvailablePlayerNumber());

        lobby.addPlayer("C");
        assertEquals("C",lobby.getPlayersNameList().get(2));
        assertEquals(3,lobby.getAvailablePlayerNumber());
        assertTrue(lobby.isFull());

    }

}