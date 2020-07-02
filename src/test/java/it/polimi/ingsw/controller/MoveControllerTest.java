package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.*;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the <code>MoveController</code>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class MoveControllerTest extends TestCase {

    Model model = new Model();
    MoveController moveController = new MoveController(model);
    Player player1 = new Player("A");
    Player player2 = new Player("B");

    /**
     * Initialize the match : (before testing)
     * <p>
     *     &nbsp - create match of 2 players, workers
     *     <br>
     *     &nbsp - set current player and chosen worker : Atlas0 (0,0)
     * </p>
     */
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

    /**
     * Test of <code>placeWorker(Operation position)</code>:
     * <p>
     *   results:
     *   <br>
     *   &nbsp - The worker must move to the chosen <code>Tile</code> (if it is available to move, otherwise, nothing changes):
     *   <br>
     *   Atlas0 (0,1)
     *
     */
    @Test
    public void testMove () {
        initialize();
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker() instanceof Atlas);

        /* move to (3,4) */
        Assert.assertFalse(moveController.moveWorker(new Operation( 1, 3,4)));
        /* the worker can't move to (3,4) -> (3,4) not occupied */
        Assert.assertFalse(model.commandToTile(3,4).isOccupiedByWorker());

        /* move to (0,1) */
        Assert.assertTrue(moveController.moveWorker(new Operation( 1, 0,1)));
        /* the worker must be on (0,1) */
        Assert.assertTrue(model.commandToTile(0,1).isOccupiedByWorker());
        Assert.assertEquals(model.commandToTile(0,1), model.getCurrentTurn().getChosenWorker().getPosition());
    }


}