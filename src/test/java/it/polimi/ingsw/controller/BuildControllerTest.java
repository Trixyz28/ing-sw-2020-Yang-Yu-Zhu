package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.God.*;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 * Cosa fa iltest
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class BuildControllerTest extends TestCase {

    Model model = new Model();
    BuildController buildController = new BuildController(model);
    Player player1 = new Player("A");
    Player player2 = new Player("B");

    /**
     * This test
     */
    @Before
    public void initialize(){

        /* Before building test */

        model.initialize(2);
        model.getMatchPlayersList().add(player1);
        model.getMatchPlayersList().add(player2);
        model.challengerStart();


        /* create Workers and start the Turn -> set the current Player and the chosen worker */

        player1.setPlayerID(0);
        player1.createWorker("HERA", model.getConditions(), model.getTotalWorkers());
        player1.chooseWorker(0).setPosition(model.commandToTile(0,0));
        model.getCurrentTurn().nextTurn(player1);
        model.getCurrentTurn().choseWorker(player1.chooseWorker(0));
    }

    @Test
    public void testBuild () {
        initialize();
        /* the worker must be of the same type that we created */
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker() instanceof Hera);

        /* the worker can't build on (4,4) because it is on (0,0) */
        Assert.assertFalse(buildController.build(new Operation( 2, 4,4)));
        /* the level of (4,4) will not change */
        Assert.assertEquals(0, model.commandToTile(4,4).getBlockLevel());

        /* the worker can build on (1,0) */
        Assert.assertTrue(buildController.build(new Operation( 2, 1,0)));
        /* the level of (1,0) will become 1 */
        Assert.assertEquals(1, model.commandToTile(1,0).getBlockLevel());
    }

    @Test
    public void testBuildDome () {
        initialize();
        model.commandToTile(1,1).setBlockLevel(3);

        Assert.assertTrue(buildController.build(new Operation( 2, 1,1)));
        Assert.assertTrue(model.commandToTile(1,1).isDomePresence());
    }
}