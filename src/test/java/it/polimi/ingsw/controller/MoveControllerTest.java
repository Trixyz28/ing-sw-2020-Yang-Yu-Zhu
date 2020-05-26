package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class MoveControllerTest extends TestCase {

    Model model;
    MoveController moveController;


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

        model.startCurrentTurn();
        model.getCurrentTurn().choseWorker(worker1);

        moveController = new MoveController(model);
    }


    @Test
    public void testMoveWorker() {
        initialize();
        Operation operation = new Operation(model.getCurrentTurn().getCurrentPlayer(),1,1,1);
        assertTrue(moveController.moveWorker(operation));

        operation = new Operation(model.getCurrentTurn().getCurrentPlayer(),1,3,3);
        assertFalse(moveController.moveWorker(operation));
    }


}