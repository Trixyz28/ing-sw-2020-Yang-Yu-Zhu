package it.polimi.ingsw.lobby;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;


public class LobbyHandlerTest {

    LobbyHandler lobbyHandler = new LobbyHandler();
    LobbyController lobbyController = new LobbyController(lobbyHandler);

    @Test
    public void testAddPlayer() {
        lobbyHandler.addPlayer("A");
        Assert.assertTrue(lobbyHandler.getPlayerList().size() == 1);
        Assert.assertTrue(lobbyHandler.getPlayerList().get(0).equals("A"));

        Assert.assertFalse(lobbyController.canUseNickname("A"));
        Assert.assertTrue(lobbyController.canUseNickname("B"));

    }

    @Test
    public void testRemovePlayer() {
        lobbyHandler.addPlayer("A");
        lobbyHandler.addPlayer("B");
        lobbyHandler.removePlayer("A");
        Assert.assertEquals("B", lobbyHandler.getPlayerList().get(0));
    }


    @Test
    public void testCheckAvailableLobbyFalse() {
        lobbyHandler.newLobby("A",2);
        lobbyController.joinLobby("B");

        Assert.assertFalse(lobbyHandler.checkAvailableLobby());

    }

    @Test
    public void testCheckAvailableLobbyTrue() {
        lobbyHandler.newLobby("A",2);

        Assert.assertTrue(lobbyHandler.checkAvailableLobby());
    }


    @Test
    public void testCreateLobby() {
        int lobbyID = lobbyController.createLobby("A",3);
        Assert.assertTrue(lobbyID == 0);
        Assert.assertTrue(lobbyHandler.getLobbyList().get(lobbyID).getLobbyPlayersNumber() == 3);
        Assert.assertEquals("A", lobbyHandler.getLobbyList().get(lobbyID).getPlayersNameList().get(0));
    }

    @Test
    public void testJoinLobby() {

        int lobby0 = lobbyHandler.newLobby("A",3);

        int lobbyID = lobbyController.joinLobby("B");
        Assert.assertEquals("B", lobbyHandler.getLobbyList().get(lobbyID).getPlayersNameList().get(1));
        Assert.assertFalse(lobbyHandler.getLobbyList().get(lobbyID).isFull());

        lobbyController.joinLobby("C");
        Assert.assertEquals("C", lobbyHandler.getLobbyList().get(lobbyID).getPlayersNameList().get(2));
        Assert.assertTrue(lobbyHandler.getLobbyList().get(lobbyID).isFull());

        Assert.assertEquals(lobby0, lobbyID);
    }

    @Test
    public void testFindLobby() {
        int lobby0 = lobbyHandler.newLobby("A",3);
        Assert.assertEquals(lobbyHandler.findLobby(0),lobbyHandler.getLobbyList().get(0));
        Assert.assertEquals(0,lobby0);
        int lobby1 = lobbyHandler.newLobby("B",2);
        Assert.assertEquals(1,lobby1);

    }

    @Test
    public void testRemoveLobby() {

        testFindLobby();

        lobbyController.removeLobby(0);
        lobbyController.removeLobby(1);
        Assert.assertEquals(0,lobbyHandler.getLobbyList().size());

        int lobby2 = lobbyHandler.newLobby("C",2);
        Assert.assertEquals(2,lobby2);
        lobbyController.removeLobby(2);
        Assert.assertEquals(null,lobbyHandler.findLobby(2));
    }
}