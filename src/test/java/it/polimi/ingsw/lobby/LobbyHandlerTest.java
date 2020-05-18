package it.polimi.ingsw.lobby;

import junit.framework.TestCase;
import org.junit.Test;


public class LobbyHandlerTest extends TestCase {

    LobbyHandler lobbyHandler = new LobbyHandler();
    LobbyController lobbyController = new LobbyController(lobbyHandler);

    @Test
    public void testAddPlayer() {
        lobbyHandler.addPlayer("A");
        assertTrue(lobbyHandler.getPlayerList().size()==1);
        assertTrue(lobbyHandler.getPlayerList().get(0).equals("A"));

        assertFalse(lobbyController.canUseNickname("A"));
        assertTrue(lobbyController.canUseNickname("B"));

    }

    @Test
    public void testCheckAvailableLobbyFalse() {
        lobbyHandler.newLobby("A",2);
        lobbyController.joinLobby("B");

        assertFalse(lobbyHandler.checkAvailableLobby());

    }

    @Test
    public void testCheckAvailableLobbyTrue() {
        lobbyHandler.newLobby("A",2);

        assertTrue(lobbyHandler.checkAvailableLobby());
    }


    @Test
    public void testCreateLobby() {
        int lobbyID = lobbyController.createLobby("A",3);
        assertTrue(lobbyID==0);
        assertTrue(lobbyHandler.getLobbyList().get(lobbyID).getLobbyPlayersNumber()==3);
        assertEquals("A", lobbyHandler.getLobbyList().get(lobbyID).getPlayersNameList().get(0));
        assertEquals("0000", lobbyHandler.getLobbyList().get(lobbyID).getPlayersNameList().get(1));
        assertEquals("0000", lobbyHandler.getLobbyList().get(lobbyID).getPlayersNameList().get(2));
    }

    @Test
    public void testJoinLobby() {

        lobbyHandler.newLobby("A",3);

        int lobbyID = lobbyController.joinLobby("B");
        assertEquals("B", lobbyHandler.getLobbyList().get(lobbyID).getPlayersNameList().get(1));
        assertFalse(lobbyHandler.getLobbyList().get(lobbyID).isFull());

        lobbyController.joinLobby("C");
        assertEquals("C", lobbyHandler.getLobbyList().get(lobbyID).getPlayersNameList().get(2));
        assertTrue(lobbyHandler.getLobbyList().get(lobbyID).isFull());
    }
}