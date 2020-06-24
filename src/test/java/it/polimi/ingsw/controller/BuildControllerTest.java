package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.God.*;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class BuildControllerTest extends TestCase {

    Model model = new Model();
    BuildController buildController = new BuildController(model);
    Player player1 = new Player("A");
    Player player2 = new Player("B");

    @Before
    public void initialize(){
        model.initialize(2);
        model.getMatchPlayersList().add(player1);
        model.getMatchPlayersList().add(player2);
        model.challengerStart();
        player1.setPlayerID(0);
        player1.createWorker("HERA", model.getConditions(), model.getTotalWorkers());
        player1.chooseWorker(0).setPosition(model.commandToTile(0,0));
        model.getCurrentTurn().nextTurn(player1);
        model.getCurrentTurn().choseWorker(player1.chooseWorker(0));
    }

    @Test
    public void testBuild () {
        initialize();
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker() instanceof Hera);

        Assert.assertFalse(buildController.build(new Operation(player1, 2, 4,4)));
        Assert.assertEquals(0, model.commandToTile(4,4).getBlockLevel());

        Assert.assertTrue(buildController.build(new Operation(player1, 2, 1,0)));
        Assert.assertEquals(1, model.commandToTile(1,0).getBlockLevel());
    }

    @Test
    public void testBuildDome () {
        initialize();
        model.commandToTile(1,1).setBlockLevel(3);

        Assert.assertTrue(buildController.build(new Operation(player1, 2, 1,1)));
        Assert.assertTrue(model.commandToTile(1,1).isDomePresence());
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
        player1.createWorker("ATLAS", conditions, totalWorkerList);
        player2.createWorker("PAN", conditions, totalWorkerList);
        UndecoratedWorker worker1 = player1.getWorkerList().get(0);

        worker1.setPosition(model.getBoard().getTile(2, 1));

        model.startTurn();
        model.getCurrentTurn().choseWorker(worker1);

        buildController = new BuildController(model);


        assertTrue(player1.getWorkerList().get(0) instanceof Atlas);
    }
*/


    /*
    @Test
    public void testBuild() {
        initialize();

        Operation operation = new Operation(model.getCurrentTurn().getCurrentPlayer(),2,4,3);
        assertFalse(buildController.build(operation));

        operation = new Operation(model.getCurrentTurn().getCurrentPlayer(),2,1,1);
        assertTrue(buildController.build(operation));
        model.getBoard().getTile(1,1).setBlockLevel(3);
        assertTrue(buildController.build(operation));

    }*/
}