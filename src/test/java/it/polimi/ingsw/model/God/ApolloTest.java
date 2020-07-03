package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Turn;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ApolloTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    ArrayList<UndecoratedWorker> totalWorkerList = new ArrayList<>();
    Apollo worker = new Apollo(new NoGod(0, conditions),totalWorkerList);
    UndecoratedWorker apollo;
    UndecoratedWorker artemis;
    Turn turn = new Turn(new Player("a"));

    @Before
    public void initialize() {
        artemis = new Artemis(new NoGod(1, conditions));
        apollo = new Apollo(new NoGod(0, conditions),totalWorkerList);
        totalWorkerList.add(worker);
        totalWorkerList.add(apollo);
        totalWorkerList.add(artemis);
        worker.setPosition(board.getTile(0,1));
        apollo.setPosition(board.getTile(0,2));
        artemis.setPosition(board.getTile(1,1));
    }

    @Test
    public void testAvailableApolloToMove() {
        initialize();
        /* worker can't change position with another Apollo (0,2) */
        Assert.assertFalse(worker.canMove(apollo.getPosition()));
        /* Apollo can change position with Artemis (1,1) */
        Assert.assertTrue(worker.canMove(artemis.getPosition()));
    }


    @Test
    public void testCanMove() {
        initialize();
        /* worker can move to (0,0, (1,0), (1,1), (1,2) */
        List<Tile> movableTiles = turn.movableList(worker);
        Assert.assertEquals(4,movableTiles.size());
        Assert.assertTrue(movableTiles.contains(board.getTile(0,0)));
        Assert.assertTrue(movableTiles.contains(board.getTile(1,0)));
        Assert.assertTrue(movableTiles.contains(board.getTile(1,1)));
        Assert.assertTrue(movableTiles.contains(board.getTile(1,2)));
    }


    @Test
    public void testMove() {
        initialize();
        /* worker (0,1) moves to (1,1) -> must change position with Artemis (1,1) */
        worker.move(artemis.getPosition());
        /* worker (1,1) artemis (0,1) */
        Assert.assertEquals(board.getTile(1,1),worker.getPosition());
        Assert.assertEquals(board.getTile(0,1),artemis.getPosition());

        /* worker (1,1) moves to (2,2) */
        Assert.assertTrue(worker.canMove(board.getTile(2,2)));
        worker.move(board.getTile(2,2));
        Assert.assertEquals(board.getTile(2,2), worker.getPosition());
    }


}