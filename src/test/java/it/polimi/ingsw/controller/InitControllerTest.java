package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.God.Athena;
import it.polimi.ingsw.model.God.Limus;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests of the <code>InitController</code>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */

public class InitControllerTest extends TestCase {

    Model model = new Model();
    InitController initController = new InitController(model);
    Player player1;
    Player player2;

    /**
     * Initialize the match : (before testing)
     * <p>
     * &nbsp - create match of 2 players:
     * </p>
     */
    @Before
    public void initialize(){
        model.initialize(2);
        player1 = new Player("A");
        player1.setPlayerID(0);
        model.addPlayer(player1);
        player2 = new Player("B");
        player2.setPlayerID(1);
        model.addPlayer(player2);
    }



    /**
     * Test of <code>initializeMatch()</code>:
     * <p>
     *   results:
     *   <br>
     *   &nbsp - the challengerID must be chosen randomly
     *   <br>
     *   &nbsp - the challenger must become the current player
     * </p>
     *
     */
    @Test
    public void testInitialize() {
        initialize();
        Assert.assertEquals(2, model.getMatchPlayersList().size());

        /* the initialization of the match is not finished */
        Assert.assertFalse(initController.isEndInitialize());
        initController.initializeMatch();
        /* current player must be the challenger */
        Assert.assertEquals(model.getChallengerID(), model.getMatchPlayersList().get(model.getChallengerID()).getPlayerID());
        Assert.assertSame(model.getCurrentTurn().getCurrentPlayer(), model.getMatchPlayersList().get(model.getChallengerID()));
    }

    /**
     * Test of <code>defineGodList(String god, GodList godList)</code>:
     * <p>
     *   results:
     *   <br>
     *   &nbsp - the chosen gods must be added in the currentGodList: LIMUS, ATHENA.
     *   <br>
     *   &nbsp - the defining is ended when the list size equals to the number of the players.
     *   The player next to the challenger must become the current player.
     *
     * </p>
     *
     */
    @Test
    public void testDefineGodList() {
        testInitialize();
        /* the currentGodList is not defined */
        Assert.assertFalse(model.getGodsList().checkLength());

        /* add LIMUS in the currentGodList */
        initController.defineGodList("LIMUS", model.getGodsList());
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());
        /* add LIMUS in the currentGodList again */
        initController.defineGodList("LIMUS", model.getGodsList());
        /* the defining is not finished, because LIMUS has already been added in the List */
        Assert.assertFalse(model.getGodsList().checkLength());
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());

        /* add ATHENA in the currentGodList */
        initController.defineGodList("ATHENA", model.getGodsList());
        Assert.assertEquals(2, model.getGodsList().getCurrentGodList().size());
        /* end of define */
        Assert.assertTrue(model.getGodsList().checkLength());
        Assert.assertTrue(model.getGodsList().getCurrentGodList().contains("LIMUS"));
        Assert.assertTrue(model.getGodsList().getCurrentGodList().contains("ATHENA"));

        /* next player */
        Assert.assertNotEquals(model.getCurrentTurn().getCurrentPlayer(), model.getMatchPlayersList().get(model.getChallengerID()));
    }

    /**
     * Test of <code>chooseGod(String god)</code>:
     * <p>
     *   results:
     *   <br>
     *   &nbsp - The player (not challenger) must create the workers according to the chosen god (if it is in the currentGodList).
     *   <br>
     *   &nbsp - The player's god card must be the chosen god: ATHENA.
     *   <br>
     *   &nbsp - The remaining god in the list must be automatically given to the challenger: LIMUS.
     *   <br>
     *   &nbsp - The totalWokers list must be created, the size must be 4 (2 ATHENA workers, 2 LIMUS workers)
     *   <br>
     *   &nbsp - The challenger must become the current player,
     *
     * </p>
     *
     */
    public void testChooseGod() {
        testDefineGodList();
        Player player = model.getCurrentTurn().getCurrentPlayer();

        /* the current player chooses ATLAS */
        initController.chooseGod("ATLAS");
        /* ATLAS is not in the currentGodList */
        Assert.assertNotEquals("ATLAS", player.getGodCard());
        /* the current player will not change */
        Assert.assertSame(player, model.getCurrentTurn().getCurrentPlayer());

        /* the current player chooses ATHENA */
        initController.chooseGod("ATHENA");
        /* the player's god must be ATHENA */
        Assert.assertEquals("ATHENA", player.getGodCard());
        Assert.assertTrue(player.chooseWorker(0) instanceof Athena);

        /* the currentPlayer must be changed -> challenger */
        Assert.assertNotEquals(player, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertSame(model.getCurrentTurn().getCurrentPlayer(), model.getMatchPlayersList().get(model.getChallengerID()));
        Assert.assertEquals("LIMUS", model.getMatchPlayersList().get(model.getChallengerID()).getGodCard());
        Assert.assertTrue(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0) instanceof Limus);

        Assert.assertEquals(4, model.getTotalWorkers().size());
        /* end of god choice */
    }

    /**
     * Test of <code>setStartingPlayer(String startingPlayerNickname)</code>:
     * <p>
     *   results:
     *   <br>
     *   &nbsp - The player, whose nickname is equal to the chosen one (if exists), must become the start player: "A"
     *   <br>
     *   &nbsp - The current player must be the start player: "A"
     * </p>
     *
     */
    @Test
    public void testSetStartingPlayer() {
        testChooseGod();

        initController.setStartingPlayer("C");
        /* "C" is not a player of this match -> can't be the start player */
        Assert.assertNotEquals("C", model.getMatchPlayersList().get(model.getStartingPlayerID()).getPlayerNickname());

        initController.setStartingPlayer(player1.getPlayerNickname());
        /* "A" is a player of this match -> "A" must become the start player */
        Assert.assertSame(player1, model.getMatchPlayersList().get(model.getStartingPlayerID()));
        Assert.assertEquals(player1.getPlayerID(), model.getStartingPlayerID());
        Assert.assertSame(player1, model.getCurrentTurn().getCurrentPlayer());
    }

    /**
     * Test of <code>placeWorker(Operation position)</code>:
     * <p>
     *   results:
     *   <br>
     *   &nbsp - The worker must be placed on the chosen <code>Tile</code> (if it is free).
     *   <br>
     *   &nbsp - When a player placed all his workers, the current turn must be changed to the next player: "A" -> "B" -> "A"
     *   <br>
     *   &nbsp - The position of the workers :
     *   <p>
     *      &nbsp &nbsp * A: worker0 (0,0), worker1 (1,0)
     *      <br>
     *      &nbsp &nbsp * B: worker0 (2,2), worker1 (4,4)
     *   </p>
     *   &nbsp - The setup of the match is finished: isEndInilialize == true;
     *
     */
    @Test
    public void testPlaceWorker() {
        testSetStartingPlayer();
        Player player = model.getCurrentTurn().getCurrentPlayer();

        /* place the worker0 on (0,0) */
        initController.placeWorker(new Operation( 0, 0,0));
        Assert.assertSame(model.commandToTile(0,0), player.chooseWorker(0).getPosition());
        /* place the worker1 on (0,0) */
        initController.placeWorker(new Operation(0, 0,0));
        /* occupied Tile -> the worker can't be placed on (0,0) */
        Assert.assertEquals(null, player.chooseWorker(1).getPosition());
        /* place the worker1 on (1,0) */
        initController.placeWorker(new Operation(0, 1,0));
        Assert.assertSame(model.commandToTile(1,0), player.chooseWorker(1).getPosition());

        /* current player must be changed to the next Player */
        Assert.assertNotEquals(player, model.getCurrentTurn().getCurrentPlayer());

        player = model.getCurrentTurn().getCurrentPlayer();
        /* place the worker0 on (2,2) */
        initController.placeWorker(new Operation(0, 2,2));
        Assert.assertSame(model.commandToTile(2,2), player.chooseWorker(0).getPosition());
        /* place the worker1 on (4,4) */
        initController.placeWorker(new Operation( 0, 4,4));
        Assert.assertSame(model.commandToTile(4,4), player.chooseWorker(1).getPosition());

        /* end of the initialization */
        Assert.assertTrue(initController.isEndInitialize());
    }


}