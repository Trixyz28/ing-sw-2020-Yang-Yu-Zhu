package it.polimi.ingsw.lobby;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class LobbyTest {

    Lobby lobby = new Lobby(3);

    /**
     *
     */
    @Test
    public void testLobbyID() {
        lobby.setLobbyID(1);
        Assert.assertEquals(1, lobby.getLobbyID());
    }

    @Test
    public void testLobbyPlayersNumber() {
        Assert.assertEquals(3, lobby.getLobbyPlayersNumber());
    }

    @Test
    public void testFull() {
        lobby.setFull(true);
        Assert.assertTrue(lobby.isFull());
        lobby.setFull(false);
        Assert.assertFalse(lobby.isFull());
    }


    @Test
    public void testPlayersNameList() {
        ArrayList<String> parameterList = new ArrayList<>();
        parameterList.add("A");
        parameterList.add("B");

        lobby.setPlayersNameList(parameterList);
        Assert.assertEquals("A", lobby.getPlayersNameList().get(0));
        Assert.assertEquals("B", lobby.getPlayersNameList().get(1));
    }

    @Test
    public void testAddPlayer() {

        ArrayList<String> parameterList = new ArrayList<>();
        parameterList.add("A");
        lobby.setPlayersNameList(parameterList);

        lobby.addPlayer("B");
        Assert.assertEquals("B", lobby.getPlayersNameList().get(1));

        lobby.addPlayer("C");
        Assert.assertEquals("C", lobby.getPlayersNameList().get(2));
        Assert.assertTrue(lobby.isFull());

    }

    @Test
    public void testRemovePlayer() {
        testAddPlayer();
        lobby.removePlayer("B");
        Assert.assertEquals(2,lobby.getPlayersNameList().size());

    }

}