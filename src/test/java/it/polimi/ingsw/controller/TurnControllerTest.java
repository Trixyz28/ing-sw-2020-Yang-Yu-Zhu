package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.God.*;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TurnControllerTest extends TestCase {

    Model model = new Model();
    TurnController turnController;
    Player player1 = new Player("A");
    Player player2 = new Player("B");
    Player player3 = new Player("C");

    @Before
    public void initialize(){
        model.initialize(3);
        model.getMatchPlayersList().add(player1);
        model.getMatchPlayersList().add(player2);
        model.getMatchPlayersList().add(player3);

        player1.setPlayerID(0);
        player2.setPlayerID(1);
        player3.setPlayerID(2);

        player1.setGodCard("POSEIDON");
        player1.createWorker("POSEIDON", model.getConditions(), model.getTotalWorkers());
        player1.chooseWorker(0).setPosition(model.commandToTile(0,0));
        player1.chooseWorker(1).setPosition(model.commandToTile(0,1));

        player2.setGodCard("PROMETHEUS");
        player2.createWorker("PROMETHEUS", model.getConditions(), model.getTotalWorkers());
        player2.chooseWorker(0).setPosition(model.commandToTile(1,0));
        player2.chooseWorker(1).setPosition(model.commandToTile(1,1));

        player3.setGodCard("TRITON");
        player3.createWorker("TRITON", model.getConditions(), model.getTotalWorkers());
        player3.chooseWorker(0).setPosition(model.commandToTile(4,4));
        player3.chooseWorker(1).setPosition(model.commandToTile(3,3));

        model.getTotalWorkers().addAll(player1.getWorkerList());
        model.getTotalWorkers().addAll(player2.getWorkerList());
        model.getTotalWorkers().addAll(player3.getWorkerList());
        model.challengerStart();
        turnController = new TurnController(model);
    }


    @Test
    public void testChoseWorker() {
        initialize();
        model.getCurrentTurn().nextTurn(player1);
        Assert.assertSame(player1, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertTrue(player1.chooseWorker(0) instanceof Poseidon);
        Assert.assertTrue(player1.chooseWorker(1) instanceof Poseidon);
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(0, model.getCurrentTurn().getState());

        turnController.setChosenWorker(0);
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());

        Assert.assertEquals(0, model.getCurrentTurn().getState());
        turnController.setChosenWorker(1);

        /* the worker 0 can't move -> not necessary to confirm the worker choice */
        Assert.assertTrue(model.getCurrentTurn().movableList(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0)).size()==0);

        Assert.assertSame(player1.chooseWorker(1), model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), player1.chooseWorker(1).getPosition());
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(1, model.getCurrentTurn().getState());
    }

    @Test
    public void testChoseWorker2() {
        initialize();
        model.getCurrentTurn().nextTurn(player2);
        Assert.assertSame(player2, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertTrue(player2.chooseWorker(0) instanceof Prometheus);
        Assert.assertTrue(player2.chooseWorker(1) instanceof Prometheus);
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        turnController.setChosenWorker(0);
        /* the worker 1 can move -> need to confirm the choice */
        Assert.assertTrue(model.getCurrentTurn().movableList(model.getCurrentTurn().getCurrentPlayer().chooseWorker(1)).size()!=0);

        /* confirm the choice */
        turnController.choseWorker();
        Assert.assertSame(player2.chooseWorker(0), model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), player2.chooseWorker(0).getPosition());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(0, model.getCurrentTurn().getState());
    }


    @Test
    public void testEndMove () {
        testChoseWorker();
        Tile t = model.commandToTile(0,2);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canMove(t));
        model.getCurrentTurn().getChosenWorker().move(t);

        Assert.assertEquals(1, model.getCurrentTurn().getState());
        turnController.endOperation();
        Assert.assertEquals(t, model.getCurrentTurn().getFinalTile());
        Assert.assertFalse(model.checkWin());
        Assert.assertEquals(2, model.getCurrentTurn().getState());
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());
    }

    @Test
    public void testEndMove2() {
        initialize();
        model.getCurrentTurn().nextTurn(player3);
        Assert.assertSame(player3, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertTrue(player3.chooseWorker(0) instanceof Triton);
        Assert.assertTrue(player3.chooseWorker(1) instanceof Triton);
        turnController.setChosenWorker(1);
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        /* the other worker can move -> the worker choice must be confirmed */
        Assert.assertTrue(model.getCurrentTurn().movableList(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0)).size()!=0);

        /* confirming the choice */
        turnController.choseWorker();
        Assert.assertEquals(1, model.getCurrentTurn().getState());

        Tile t = model.commandToTile(3,4);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canMove(t));
        model.getCurrentTurn().getChosenWorker().move(t);

        Assert.assertEquals(1, model.getCurrentTurn().getState());
        turnController.endOperation();

        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertFalse(model.checkWin());
        Assert.assertEquals(t, model.getCurrentTurn().getInitialTile());
        Assert.assertEquals(1, model.getCurrentTurn().getState());
    }

    @Test
    public void testEndBuild() {
        testEndMove();
        Tile build = model.commandToTile(1,2);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canBuildBlock(build));
        model.getCurrentTurn().getChosenWorker().buildBlock(build);

        Assert.assertEquals(2, model.getCurrentTurn().getState());
        turnController.endOperation();
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(2, model.getCurrentTurn().getState());
        Assert.assertEquals(0, model.getCurrentTurn().getChosenWorker().getState());
        model.getCurrentTurn().getChosenWorker().useGodPower(false);
        Assert.assertEquals(3, model.getCurrentTurn().getChosenWorker().getState());
        turnController.endOperation();
        Assert.assertEquals(0, model.getCurrentTurn().getState());
    }

    @Test
    public void testNextTurn() {
        testEndBuild();
        Assert.assertSame(player2, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertFalse(model.checkLose());
    }

    @Test
    public void testCheckGameOver() {
        testChoseWorker();
        Tile t = model.commandToTile(0,2);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canMove(t));
        model.getCurrentTurn().getChosenWorker().move(t);

        for(Tile tile : model.getCurrentTurn().getChosenWorker().getPosition().getAdjacentTiles()){
            if(!tile.isOccupiedByWorker()){
                tile.setDomePresence(true);
            }
        }

        Assert.assertEquals(1, model.getCurrentTurn().getState());
        turnController.endOperation();
        Assert.assertEquals(t, model.getCurrentTurn().getFinalTile());
        Assert.assertFalse(model.isGameOver());
        Assert.assertEquals(2, model.getMatchPlayersList().size());
        Assert.assertEquals(4, model.getTotalWorkers().size());
        Assert.assertFalse(model.getMatchPlayersList().contains(player1));

        Assert.assertSame(player2, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertFalse(model.checkLose());
    }

    @Test
    public void testCheckGameOver2(){
        testEndMove();
        for(Tile tile : player2.chooseWorker(0).getPosition().getAdjacentTiles()){
            if(!tile.isOccupiedByWorker()){
                tile.setDomePresence(true);
            }
        }

        for(Tile tile : player2.chooseWorker(1).getPosition().getAdjacentTiles()){
            if(!tile.isOccupiedByWorker()){
                tile.setDomePresence(true);
            }
        }

        turnController.endOperation();

        Assert.assertFalse(model.isGameOver());
        Assert.assertEquals(2, model.getMatchPlayersList().size());
        Assert.assertEquals(4, model.getTotalWorkers().size());
        Assert.assertFalse(model.getMatchPlayersList().contains(player2));

        Assert.assertSame(player3, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertFalse(model.checkLose());


    }





}