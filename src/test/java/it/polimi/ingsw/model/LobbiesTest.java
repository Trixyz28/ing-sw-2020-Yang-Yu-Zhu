package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

public class LobbiesTest extends TestCase{

    Lobbies lobbies = new Lobbies();

    @Test
    public void testPlayerNumber() {
        lobbies.setTotalPlayersNumber(2);
        assertEquals(2,lobbies.getTotalPlayersNumber());
    }

    @Test
    public void testCurrentLobby() {
        Lobby lobby = new Lobby();
        lobbies.setCurrentLobby(lobby);
        assertEquals(lobby,lobbies.getCurrentLobby());
    }


}