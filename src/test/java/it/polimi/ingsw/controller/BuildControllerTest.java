package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.God.Conditions;
import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class BuildControllerTest extends TestCase {

    Model model;
    BuildController buildController;

    @Before
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

        buildController = new BuildController(model);
    }
/*
    @Test
    public void testCheckBlockDome() {
        testBuild();

        assertTrue(buildController.checkBlockDome("DOME"));
        assertTrue(buildController.checkBlockDome("BLOCK"));
        assertFalse(buildController.checkBlockDome("A"));
    }
*/



    @Test
    public void testBuild() {
        initialize();

        Operation operation = new Operation(model.getCurrentTurn().getCurrentPlayer(),2,4,3);
        assertFalse(buildController.build(operation));
        assertEquals(operation,buildController.getOperation());

        operation = new Operation(model.getCurrentTurn().getCurrentPlayer(),2,1,1);
        assertTrue(buildController.build(operation));
        model.getBoard().getTile(1,1).setBlockLevel(3);
        assertTrue(buildController.build(operation));

    }
}