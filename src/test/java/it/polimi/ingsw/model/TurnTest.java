package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.*;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TurnTest extends TestCase {

    Player player = new Player("A");
    Player player2 = new Player("B");
    Conditions condition = new Conditions();
    Turn turn = new Turn(player);
    Board board = new Board();
    ArrayList<UndecoratedWorker> totalWorkerList = new ArrayList<>();

    @Before
    public void initialize(){
        player.createWorker("ZEUS", condition, totalWorkerList);
        player.chooseWorker(0).setPosition(board.getTile(0,0));
        player.chooseWorker(1).setPosition(board.getTile(1,0));
        player2.createWorker("PAN", condition, totalWorkerList);
        player2.chooseWorker(0).setPosition(board.getTile(2,0));
        player2.chooseWorker(1).setPosition(board.getTile(1,4));
    }


    @Test
    public void testNextTurn() {
        testChoseWorker();

        Assert.assertEquals(0,turn.getTurnNumber());
        Assert.assertSame(player, turn.getCurrentPlayer());
        Assert.assertEquals(2, turn.getState());
        Assert.assertNotEquals(null, turn.getChosenWorker());

        /* new turn -> current player = player2 */
        turn.nextTurn(player2);
        Assert.assertEquals(1,turn.getTurnNumber());
        Assert.assertSame(player2, turn.getCurrentPlayer());
        Assert.assertEquals(null, turn.getChosenWorker());
        Assert.assertEquals(0, turn.getState());

    }


    @Test
    public void testInitialTile() {
        Tile t = new Tile();
        turn.setInitialTile(t);
        assertEquals(t,turn.getInitialTile());
    }

    @Test
    public void testFinalTile() {
        Tile t = new Tile();
        turn.setFinalTile(t);
        assertEquals(t,turn.getFinalTile());
    }


    @Test
    public void testChoseWorker() {
        initialize();
        /* choose worker0 (0,0) */
        turn.choseWorker(player.chooseWorker(0));
        Assert.assertEquals(player.chooseWorker(0),turn.getChosenWorker());
        Assert.assertEquals(turn.getInitialTile(), turn.getChosenWorker().getPosition());
        Assert.assertEquals(null, turn.getFinalTile());
        Assert.assertEquals(null, turn.getFinalTile());
        Assert.assertEquals(0, turn.getState());
        Assert.assertFalse(turn.checkLose());

        /* move state */
        turn.nextState();
        Assert.assertEquals(1, turn.getChosenWorker().getState());
        Assert.assertEquals(1, turn.getState());

        /* state with godPower active */
        turn.getChosenWorker().setGodPower(true);
        Assert.assertEquals(0, turn.getChosenWorker().getState());
        /* turn state doesn't change */
        Assert.assertEquals(1, turn.getState());
        Assert.assertFalse(turn.checkLose());
        turn.getChosenWorker().useGodPower(false);

        /* build state */
        turn.nextState();
        Assert.assertEquals(2, turn.getState());
        Assert.assertFalse(turn.checkLose());

        /* buildableList */
        Assert.assertEquals(3, turn.buildableList(turn.getChosenWorker()).size());
        Assert.assertEquals(5, turn.buildableList(player2.chooseWorker(1)).size());
    }

    @Test
    public void testCheckLose(){
        initialize();
        /* worker0 (0,0) worker1(1,0) */
        Assert.assertEquals(0, turn.getState());

        /* set lose (before move) conditions */
        board.getTile(0,1).setDomePresence(true);
        board.getTile(1,1).setDomePresence(true);
        board.getTile(2,1).setDomePresence(true);
        /* player loses */
        Assert.assertTrue(turn.checkLose());
    }

    @Test
    public void testCheckLose2(){
        initialize();
        /* player2: worker1(1,4) */
        turn.nextTurn(player2);
        Assert.assertEquals(player2, turn.getCurrentPlayer());
        turn.choseWorker(player2.chooseWorker(1));
        Assert.assertEquals(player2.chooseWorker(1), turn.getChosenWorker());

        /* build state */
        turn.getChosenWorker().setState(2);
        Assert.assertEquals(2,turn.getState());

        /* set can't build conditions */
        board.getTile(0,3).setDomePresence(true);
        board.getTile(0,4).setDomePresence(true);
        board.getTile(1,3).setDomePresence(true);
        board.getTile(2,3).setDomePresence(true);
        board.getTile(2,4).setDomePresence(true);

        /* player2 loses */
        Assert.assertTrue(turn.checkLose());
    }



}