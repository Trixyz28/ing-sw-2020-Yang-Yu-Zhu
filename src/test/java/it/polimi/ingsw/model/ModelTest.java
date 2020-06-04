package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.*;
import it.polimi.ingsw.observers.Observer;
import junit.framework.TestCase;
import org.junit.Test;

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
        model.startTurn();
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

        Conditions conditions = new Conditions();
        UndecoratedWorker pan = new Pan(new NoGod(conditions));
        pan.setPosition(position);
        model.getCurrentTurn().choseWorker(pan);
        model.getCurrentTurn().setFinalTile(destination);
        destination.setBlockLevel(0);
        pan.setPosition(destination);
        assertTrue(model.checkWin());

    }

    @Test
    public void testNextPlayerIndex() {
        testInitialize();
        model.getMatchPlayersList().get(0).setPlayerID(0);
        model.getMatchPlayersList().get(1).setPlayerID(1);
        model.setStartingPlayerID(0);
        model.startTurn();
        model.getCurrentTurn().setCurrentPlayer(model.getMatchPlayersList().get(0));
        assertEquals(1,model.getNextPlayerIndex());
        model.getCurrentTurn().setCurrentPlayer(model.getMatchPlayersList().get(1));
        assertEquals(0,model.getNextPlayerIndex());
    }



    @Test
    public void testWorkerChosen() {
        model.setWorkerChosen(true);
        assertTrue(model.isWorkerChosen());
    }



    @Test
    public void testGameOver() {
        testCurrentTurn();
        model.gameOver();
        assertTrue(model.isGameOver());
    }


    @Test
    public void testTotalWorkerList() {
        testInitialize();
        Conditions conditions = new Conditions();
        model.getMatchPlayersList().get(0).createWorker("DEMETER",conditions,model.getTotalWorkers());
        model.getMatchPlayersList().get(1).createWorker("PROMETHEUS",conditions,model.getTotalWorkers());
        model.createTotalWorkerList();

        assertEquals(4,model.getTotalWorkers().size());
        assertTrue(model.getTotalWorkers().get(1) instanceof Demeter);
        assertTrue(model.getTotalWorkers().get(2) instanceof Prometheus);


    }


    @Test
    public void testOperations() {

        testCurrentTurn();

        Observer obs = new Observer() {
            @Override
            public void update(Object message) {
                assertTrue(message instanceof Operation);
                Operation operation = (Operation)message;
                assertTrue(operation.getRow() == -1);
                assertTrue(operation.getColumn() == -1);
                assertEquals(model.getMatchPlayersList().get(1).getPlayerNickname(),(operation.getPlayer()));
                assertTrue(operation.getType()==0 || ((operation.getType()==1) || operation.getType()==2));
            }
        };

        model.addObservers(obs);
        model.place();
        model.move();
        model.build();
    }

}