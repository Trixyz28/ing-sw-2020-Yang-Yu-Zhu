package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Turn;
import junit.framework.TestCase;
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
        assertFalse(worker.canMove(apollo.getPosition()));
        assertTrue(worker.canMove(artemis.getPosition()));
    }


    @Test
    public void testCanMove() {
        initialize();
        List<Tile> movableTiles = turn.movableList(worker);
        assertEquals(4,movableTiles.size());
    }


    @Test
    public void testMove() {
        initialize();
        worker.move(artemis.getPosition());
        assertEquals(board.getTile(1,1),worker.getPosition());
        assertEquals(board.getTile(0,1),artemis.getPosition());
        worker.move(board.getTile(2,2));
    }


}