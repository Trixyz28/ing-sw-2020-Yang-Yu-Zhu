package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.God.*;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the <code>InitController</code>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class TurnControllerTest extends TestCase {

    Model model = new Model();
    TurnController turnController;
    Player player1 = new Player("A");
    Player player2 = new Player("B");
    Player player3 = new Player("C");




    /**
     * Initialize the match : (before testing)
     * <p>
     *  &nbsp - create match of 3 players: "A" "B" "C"
     *  <br>
     *  &nbsp - create and place workers :
     *  <br>
     *  &nbsp &nbsp &nbsp A -> Poseidon0 (0,0) Poseidon1 (0,1)
     *  <br>
     *  &nbsp &nbsp &nbsp B -> Prometheus0 (1,0) Prometheus1 (1,1)
     *  <br>
     *  &nbsp &nbsp &nbsp C -> Triton0 (4,4) Triton1 (3,3)
     *  <br>
     *  &nbsp - create <code>TurnController</code>
     *  <br>
     *  &nbsp - set the current player: A
     *
     * </p>
     */
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
        model.getCurrentTurn().nextTurn(player1);
    }


    /**
     * Test of <code>setChosenWorker(int index) + choseWorker()</code>: with POSEIDON
     * <p>
     *   results:
     *   <br>
     *   &nbsp - the chosen worker must be the worker with chosen index (if it can be chosen): Poseidon1 (0,1)
     *   <br>
     *   &nbsp - the turn state must be changed: 0 -> 1
     *   <br>
     *   &nbsp - the godPower must be inactive: Poseidon
     *   <br>
     *   &nbsp - the initialTile must be the position of the chosen worker: (0,1)
     * </p>
     *
     */
    @Test
    public void testChoseWorker() {
        initialize();
        /* player1 is the current player */
        Assert.assertSame(player1, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertTrue(player1.chooseWorker(0) instanceof Poseidon);
        Assert.assertTrue(player1.chooseWorker(1) instanceof Poseidon);
        /* the worker in not chosen */
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(0, model.getCurrentTurn().getState());

        /* the worker 0 can't be chosen (not available to move) */
        turnController.setChosenWorker(0);
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        /* the state is not changed */
        Assert.assertEquals(0, model.getCurrentTurn().getState());

        /* the player chooses the worker index 1 */
        turnController.setChosenWorker(1);

        /* the worker 0 can't move -> confirm the worker choice automatically */
        Assert.assertTrue(model.getCurrentTurn().movableList(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0)).size()==0);

        /* the chosen worker must be the worker 1 */
        Assert.assertSame(player1.chooseWorker(1), model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), player1.chooseWorker(1).getPosition());
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());
        /* the state must be changed to 1 (start move) */
        Assert.assertEquals(1, model.getCurrentTurn().getState());
    }

    /**
     * Another Test of <code>setChosenWorker(int index) + choseWorker()</code>: with PROMETHEUS
     * <p>
     *   results:
     *   <br>
     *   &nbsp - the chosen worker must be the worker with chosen index (if it can be chosen): Prometheus0 (1,0)
     *   <br>
     *   &nbsp - the turn state isn't changed: 0
     *   <br>
     * </p>
     * Test of <code>choseWorker()</code>:
     * <p>
     *     results:
     *     <br>
     *     &nbsp - the chosen worker is confirmed : Prometheus0 (1,0)
     *     <br>
     *     &nbsp - the turn state isn't changed: 0
     *     <br>
     *     &nbsp - the godPower is active: Prometheus
     *
     * </p>
     *
     */
    @Test
    public void testChoseWorker2() {
        initialize();
        model.getCurrentTurn().nextTurn(player2);
        Assert.assertSame(player2, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertTrue(player2.chooseWorker(0) instanceof Prometheus);
        Assert.assertTrue(player2.chooseWorker(1) instanceof Prometheus);
        Assert.assertEquals(0, model.getCurrentTurn().getState());

        /* choose the worker 0 */
        turnController.setChosenWorker(0);
        /* the worker 1 can move -> need to confirm the choice */
        Assert.assertTrue(model.getCurrentTurn().movableList(model.getCurrentTurn().getCurrentPlayer().chooseWorker(1)).size()!=0);

        /* confirm the choice */
        turnController.choseWorker();
        Assert.assertSame(player2.chooseWorker(0), model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), player2.chooseWorker(0).getPosition());
        /* Prometheus: can move and can build */
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(0, model.getCurrentTurn().getState());
    }


    /**
     * Test of <code>endMove()</code>: with POSEIDON
     * <p>
     *   results:
     *   <br>
     *   &nbsp - the chosen worker is not changed: Poseidon1 (0,2)
     *   <br>
     *   &nbsp - the turn state must be changed: 1 -> 2
     *   <br>
     *   &nbsp - the godPower must be inactive: Poseidon
     *   <br>
     *   &nbsp - the finalTile must be changed: (0,2)
     * </p>
     *
     */
    @Test
    public void testEndMove () {
        testChoseWorker();
        Tile t = model.commandToTile(0,2);
        /* move to (0,2) */
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canMove(t));
        model.getCurrentTurn().getChosenWorker().move(t);

        /* state 1 -> moving */
        Assert.assertEquals(1, model.getCurrentTurn().getState());
        turnController.endOperation();

        Assert.assertEquals(t, model.getCurrentTurn().getFinalTile());
        Assert.assertFalse(model.checkWin());
        /* state must be changed */
        Assert.assertEquals(2, model.getCurrentTurn().getState());
        /* godPower off -> Poseidon (activated after build) */
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());
    }

    /**
     * Test of <code>endMove()</code>: with TRITON
     * <p>
     *   before endMove():
     *   <br>
     *   &nbsp - the chosen worker: Triton1 (3,3)
     *   <br>
     *   &nbsp - the turn state must be changed: 0 -> 1
     *   <br>
     *   &nbsp - the chosen worker moves to (3,4)
     *   <br>
     *   results endMove():
     *   <br>
     *   &nbsp - the chosen worker is not changed: Triton1 (3,4)
     *   <br>
     *   &nbsp - the turn state is not changed: 1
     *   <br>
     *   &nbsp - the godPower must be active: Triton
     *   <br>
     *   &nbsp - the finalTile must be changed: (3,4)
     *   <br>
     *   &nbsp - the initialTile must be changed: (3,4) -> because the GodPower is active
     * </p>
     *
     */
    @Test
    public void testEndMove2() {
        initialize();
        model.getCurrentTurn().nextTurn(player3);
        Assert.assertSame(player3, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertTrue(player3.chooseWorker(0) instanceof Triton);
        Assert.assertTrue(player3.chooseWorker(1) instanceof Triton);
        /* choose worker index 1 */
        turnController.setChosenWorker(1);
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        /* the other worker can move -> the worker choice must be confirmed */
        Assert.assertTrue(model.getCurrentTurn().movableList(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0)).size()!=0);

        /* confirming the choice */
        turnController.choseWorker();
        Assert.assertEquals(1, model.getCurrentTurn().getState());

        /* move to (3,4) */
        Tile t = model.commandToTile(3,4);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canMove(t));
        model.getCurrentTurn().getChosenWorker().move(t);

        /* the state is not change */
        Assert.assertEquals(1, model.getCurrentTurn().getState());
        turnController.endOperation();

        /* the GodPower is on */
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertFalse(model.checkWin());
        Assert.assertEquals(t, model.getCurrentTurn().getInitialTile());
        Assert.assertEquals(1, model.getCurrentTurn().getState());
    }

    /**
     * Test of <code>endBuild()</code>: with POSEIDON
     * <p>
     *   before endBuild():
     *   &nbsp - the chosen worker is not changed: Poseidon1 (0,2)
     *   <br>
     *   &nbsp - the state of the turn must be 2
     *   <br>
     *   &nbsp - the chosen worker builds on (1,2): (1,2).level = 1
     *   <br>
     *   results endBuild() before useGodPower():
     *   <br>
     *   &nbsp - the chosen worker is not changed: Poseidon1 (0,2)
     *   <br>
     *   &nbsp - the turn state is not changed: 2
     *   <br>
     *   &nbsp - the godPower must be active: Poseidon
     *   <br>
     *   &nbsp - the worker state must be 0
     *   <br>
     *   results after useGodPower(false):
     *   <br>
     *   &nbsp - the worker state must be changed: 0 -> 3
     *   <br>
     *   results endBuild() after useGodPower(false):
     *   <br>
     *   &nbsp - the turn state must be changed: 2 -> 0
     *
     * </p>
     *
     */
    @Test
    public void testEndBuild() {
        testEndMove();
        /* build on (1,2) */
        Tile build = model.commandToTile(1,2);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canBuildBlock(build));
        model.getCurrentTurn().getChosenWorker().buildBlock(build);

        /* state 2 -> building */
        Assert.assertEquals(2, model.getCurrentTurn().getState());
        /* end first build */
        turnController.endOperation();

        /* GodPower on -> waiting for using power */
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(2, model.getCurrentTurn().getState());
        Assert.assertEquals(0, model.getCurrentTurn().getChosenWorker().getState());

        /* do not use Poseidon's power */
        model.getCurrentTurn().getChosenWorker().useGodPower(false);
        Assert.assertEquals(3, model.getCurrentTurn().getChosenWorker().getState());

        /* end build */
        turnController.endOperation();
        Assert.assertEquals(0, model.getCurrentTurn().getState());
    }

    /**
     * Test of <code>nextTurn()</code> (in endBuild()):
     * <p>
     *
     *   results:
     *   <br>
     *   &nbsp - the current player must be changed to the next player: "A" -> "B"
     *   <br>
     *   &nbsp - no worker chosen
     *   <br>
     *   &nbsp - the turn state must be 0
     *
     * </p>
     *
     */
    @Test
    public void testNextTurn() {
        testEndBuild();
        /* endTurn -> the current player must be changed */
        Assert.assertSame(player2, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertFalse(model.checkLose());
    }

    /**
     * Test of lose cases:
     * <p>
     *   losing after move:
     *   <br>
     *   &nbsp - set lose circumstance: worker cannot build after move
     *   <br>
     *   results:
     *   <br>
     *   &nbsp - the player loses: "A"
     *   <br>
     *   &nbsp - the players number must be changed: 3 -> 2 ("A" must be removed from the playerList)
     *   <br>
     *   &nbsp - the totalWorkers size must be changed: 6 -> 4
     *   <br>
     *   &nbsp - the current player must be changed to the next player: "A" -> "B"
     *   <br>
     *   &nbsp - no worker chosen
     *   <br>
     *   &nbsp - the turn state must be 0
     *
     * </p>
     *
     */
    @Test
    public void testCheckGameOver() {
        testChoseWorker();
        Tile t = model.commandToTile(0,2);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canMove(t));
        model.getCurrentTurn().getChosenWorker().move(t);

        /* set domePresence on worker's adjacent tiles */
        for(Tile tile : model.getCurrentTurn().getChosenWorker().getPosition().getAdjacentTiles()){
            if(!tile.isOccupiedByWorker()){
                tile.setDomePresence(true);
            }
        }

        /* move state */
        Assert.assertEquals(1, model.getCurrentTurn().getState());

        /* the player loses in the checkLose() in endOperation */
        turnController.endOperation();
        Assert.assertEquals(t, model.getCurrentTurn().getFinalTile());
        /* the match is not ended -> rest 2 players */
        Assert.assertFalse(model.isGameOver());

        /* player1 removed */
        Assert.assertEquals(2, model.getMatchPlayersList().size());
        Assert.assertEquals(4, model.getTotalWorkers().size());
        Assert.assertFalse(model.getMatchPlayersList().contains(player1));

        /* current player must be changed */
        Assert.assertSame(player2, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertFalse(model.checkLose());
    }

    /**
     * Test of lose cases:
     * <p>
     *   losing before move:
     *   <br>
     *   &nbsp - set lose circumstance: both the workers cannot move
     *   <br>
     *   results:
     *   <br>
     *   &nbsp - the player loses: "B"
     *   <br>
     *   &nbsp - the players number must be changed: 3 -> 2 ("B" must be removed from the playerList)
     *   <br>
     *   &nbsp - the totalWorkers size must be changed: 6 -> 4
     *   <br>
     *   &nbsp - the current player must be changed to the next player: "B" -> "C"
     *   <br>
     *   &nbsp - no worker chosen
     *   <br>
     *   &nbsp - the turn state must be 0
     *
     * </p>
     *
     */
    @Test
    public void testCheckGameOver2(){
        testEndMove();

        /* set unmovable the adjacent tiles of worker0 */
        for(Tile tile : player2.chooseWorker(0).getPosition().getAdjacentTiles()){
            if(!tile.isOccupiedByWorker()){
                tile.setDomePresence(true);
            }
        }
        /* set unmovable the adjacent tiles of worker1 */
        for(Tile tile : player2.chooseWorker(1).getPosition().getAdjacentTiles()){
            if(!tile.isOccupiedByWorker()){
                tile.setDomePresence(true);
            }
        }

        /* endTurn of player1 */
        turnController.endOperation();

        /* player2 loses -> match not ended */
        Assert.assertFalse(model.isGameOver());
        Assert.assertEquals(2, model.getMatchPlayersList().size());
        Assert.assertEquals(4, model.getTotalWorkers().size());
        /* player2 removed */
        Assert.assertFalse(model.getMatchPlayersList().contains(player2));

        /* current player must be the player next to "B" -> "C" */
        Assert.assertSame(player3, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertFalse(model.checkLose());


    }





}