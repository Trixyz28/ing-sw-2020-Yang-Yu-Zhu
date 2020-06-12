package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Tile;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PoseidonTest extends TestCase {

    Conditions conditions = new Conditions();
    Board board = new Board();
    ArrayList<UndecoratedWorker> totalWorkerList = new ArrayList<>();
    Poseidon worker = new Poseidon(new NoGod(0, conditions), totalWorkerList);
    UndecoratedWorker poseidon;

    @Before
    public void initialize() {
        poseidon = new Poseidon(new NoGod(0, conditions), totalWorkerList);
        worker.setPosition(board.getTile(0,0));
        poseidon.setPosition(board.getTile(2,2));
        totalWorkerList.add(worker);
        totalWorkerList.add(poseidon);
        worker.setState(2);
    }


    @Test
    public void testBuildBlock() {
        initialize();
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,1)));
        worker.buildBlock(board.getTile(0,1));
        Assert.assertTrue(worker.getGodPower());
        Assert.assertEquals(0, worker.getState());
        worker.setGodPower(false);
        Assert.assertEquals(3,worker.getState());
        Assert.assertFalse(poseidon.canBuildBlock(board.getTile(1,0)));
        Assert.assertFalse(worker.canBuildBlock(board.getTile(1,0)));
        Assert.assertTrue(worker.canBuildBlock(board.getTile(3,3)));
        worker.buildBlock(board.getTile(3,3));
        worker.useGodPower(false);
        worker.nextState();
        Assert.assertEquals(0, worker.getState());
    }

    @Test
    public void testGodPower() {
        initialize();
        poseidon.getPosition().setBlockLevel(1);
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,1)));
        worker.buildBlock(board.getTile(0,1));
        Assert.assertFalse(worker.getGodPower());
    }

    @Test
    public void testBuildDome() {
        initialize();
        board.getTile(1,0).setBlockLevel(3);
        board.getTile(2,1).setBlockLevel(3);
        for(Tile t: poseidon.getPosition().getAdjacentTiles()){
            if(t != board.getTile(2,1)){
                t.setDomePresence(true);
            }
        }
        Assert.assertTrue(worker.canBuildDome(board.getTile(1,0)));
        worker.buildDome(board.getTile(1,0));
        Assert.assertTrue(worker.getGodPower());
        worker.setGodPower(false);
        Assert.assertTrue(worker.canBuildDome(board.getTile(2,1)));
        worker.buildDome(board.getTile(2,1));
        Assert.assertFalse(worker.getGodPower());

    }

    @Test
    public void testUnmovedWorker(){
        initialize();
        totalWorkerList.remove(poseidon);
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,1)));
        Assert.assertEquals(null, worker.getWorker(board.getTile(0,1),totalWorkerList));
        worker.buildBlock(board.getTile(0,1));
        Assert.assertFalse(worker.getGodPower());
    }
}