package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Turn;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MinotaurTest extends TestCase {


    Conditions conditions = new Conditions();
    Board board = new Board();
    ArrayList<UndecoratedWorker> totalWorkerList = new ArrayList<>();
    Minotaur worker = new Minotaur(new NoGod(1, conditions),totalWorkerList);
    UndecoratedWorker minotaur;
    UndecoratedWorker athena;
    Turn turn = new Turn(new Player("a"));


    @Before
    public void initialize() {
        athena = new Athena(new NoGod(0, conditions));
        minotaur = new Minotaur(new NoGod(1, conditions),totalWorkerList);
        totalWorkerList.add(worker);
        totalWorkerList.add(minotaur);
        totalWorkerList.add(athena);
        worker.setPosition(board.getTile(2,2));
        minotaur.setPosition(board.getTile(1,2));
        athena.setPosition(board.getTile(3,3));
    }


    @Test
    public void testAvailableMinotaurToMove() {
        initialize();
        assertTrue(worker.canMove(athena.getPosition()));
        assertFalse(worker.canMove(minotaur.getPosition()));

        boolean flag = false;
        for(Tile t : turn.movableList(worker)) {
            if(board.getTile(2,3).equals(t)) {
                flag = true;
            }
        }
        assertTrue(flag);
    }


    @Test
    public void testMove() {
        initialize();
        worker.move(athena.getPosition());
        assertEquals(board.getTile(4,4),athena.getPosition());
        assertEquals(board.getTile(3,3),worker.getPosition());
    }



}