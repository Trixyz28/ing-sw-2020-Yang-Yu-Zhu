package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class ModelTest extends TestCase {

    Model model = new Model();


    @Test
    public void testInitialize() {
        Player player1 = new Player();
        player1.setPlayerNickname("A");
        Player player2 = new Player();
        player2.setPlayerNickname("B");

        model.initialize(2);
        model.addPlayer(player1);
        model.addPlayer(player2);

        assertEquals("A",model.getMatchPlayersList().get(0).getPlayerNickname());
        assertEquals("B",model.getMatchPlayersList().get(1).getPlayerNickname());
    }


    @Test
    public void testChallenger() {
        testInitialize();
        model.randomChooseChallenger();
        assertTrue(model.getChallengerID()==0 || model.getChallengerID()==1);
        assertTrue((model.getMatchPlayersList().get(0).isChallenger() && !model.getMatchPlayersList().get(1).isChallenger())
        || (!model.getMatchPlayersList().get(0).isChallenger() && model.getMatchPlayersList().get(1).isChallenger()));
    }

    @Test
    public void testStartingPlayerID() {
        testInitialize();
        model.setStartingPlayerID(1);
        assertEquals(1,model.getStartingPlayerID());
        assertEquals("B",model.getMatchPlayersList().get(model.getStartingPlayerID()).getPlayerNickname());
    }

    @Test
    public void testCurrentTurn() {
        testInitialize();
        model.setStartingPlayerID(1);
        model.startCurrentTurn();
        assertSame(model.getMatchPlayersList().get(1),model.getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void testCommandToTile() {
        testInitialize();
        assertSame(model.getBoard().getMap()[2][3],model.commandToTile(2,3));
    }

    @Test
    public void testCheckWin() {
        testCurrentTurn();
        Tile position = new Tile();
        position.setBlockLevel(2);
        Tile destination = new Tile();
        destination.setBlockLevel(3);
        model.getCurrentTurn().setInitialTile(position);
        model.getCurrentTurn().setFinalTile(destination);
        assertTrue(model.checkWin());

        destination.setBlockLevel(2);
        model.getCurrentTurn().setFinalTile(destination);
        assertFalse(model.checkWin());
    }

    public void testCheckLose() {
    }

    public void testGameOver() {
    }
}