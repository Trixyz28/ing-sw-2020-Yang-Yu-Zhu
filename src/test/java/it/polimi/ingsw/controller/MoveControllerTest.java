package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.*;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MoveControllerTest extends TestCase {

    Model model = new Model();
    MoveController moveController = new MoveController(model);
    Player player1 = new Player("A");
    Player player2 = new Player("B");

    @Before
    public void initialize(){
        model.initialize(2);
        model.getMatchPlayersList().add(player1);
        model.getMatchPlayersList().add(player2);
        model.challengerStart();
        player1.setPlayerID(0);
        player1.createWorker("ATLAS", model.getConditions(), model.getTotalWorkers());
        player1.chooseWorker(0).setPosition(model.commandToTile(0,0));
        model.getCurrentTurn().nextTurn(player1);
        model.getCurrentTurn().choseWorker(player1.chooseWorker(0));
    }

    @Test
    public void testMove () {
        initialize();
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker() instanceof Atlas);

        Assert.assertFalse(moveController.moveWorker(new Operation(player1, 1, 3,4)));
        Assert.assertFalse(model.commandToTile(3,4).isOccupiedByWorker());

        Assert.assertTrue(moveController.moveWorker(new Operation(player1, 1, 0,1)));
        Assert.assertTrue(model.commandToTile(0,1).isOccupiedByWorker());
    }

    /*
    @Test
    public void initialize() {
        model = new Model();
        model.initialize(2);
        model.setStartingPlayerID(0);
        Player player1 = new Player("A");
        Player player2 = new Player("B");
        model.addPlayer(player1);
        model.addPlayer(player2);
        Conditions conditions = new Conditions();
        List<UndecoratedWorker> totalWorkerList = new ArrayList<>();
        player1.createWorker("ATLAS",conditions,totalWorkerList);
        player2.createWorker("PAN",conditions,totalWorkerList);
        UndecoratedWorker worker1 = player1.getWorkerList().get(0);

        worker1.setPosition(model.getBoard().getTile(2,1));

        model.startTurn();
        model.getCurrentTurn().choseWorker(worker1);

        moveController = new MoveController(model);

        assertEquals(player1.getWorkerList().get(0), model.getCurrentTurn().getChosenWorker());
    }

     */

}