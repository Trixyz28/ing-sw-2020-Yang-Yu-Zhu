package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class ModelTest extends TestCase {

    Model model = new Model();
    Messages messages = new Messages();


    @Test
    public void testInitialize() {
        Player player1 = new Player("A");
        Player player2 = new Player("B");

        model.initialize(2);
        model.addPlayer(player1);
        model.addPlayer(player2);

        assertEquals(2,model.getPlayersNumber());
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

    @Test
    public void testGodList() {
    }


    @Test
    public void testCurrentPlayer() {
        model.initialize(3);
        model.setCurrentPlayerID(1);
        model.updateCurrentPlayer();
        assertEquals(2,model.getCurrentPlayerID());
        model.updateCurrentPlayer();
        assertEquals(0,model.getCurrentPlayerID());
    }

    @Test
    public void testWorkerChosen() {
        model.setWorkerChosen(true);
        assertTrue(model.isWorkerChosen());
    }



    public void testCheckLose() {
    }


    @Test
    public void testGameOver() {
        testCurrentTurn();
        model.gameOver();
        assertTrue(model.isGameOver());
    }


}