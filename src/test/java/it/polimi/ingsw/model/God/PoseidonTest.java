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
        /* worker (0,0) builds on (0,1) */
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,1)));
        worker.buildBlock(board.getTile(0,1));
        /* GodPower must be active */
        Assert.assertTrue(worker.getGodPower());
        /* state = 0 -> waiting answer */
        Assert.assertEquals(0, worker.getState());
        worker.setGodPower(false);
        /* state 3 -> after answer */
        Assert.assertEquals(3,worker.getState());

        /* worker(0,0) can only build on unmoved worker(2,2)'s adjacent tiles */
        Assert.assertFalse(poseidon.canBuildBlock(board.getTile(1,0)));
        Assert.assertFalse(worker.canBuildBlock(board.getTile(1,0)));
        Assert.assertTrue(worker.canBuildBlock(board.getTile(3,3)));

        /* build on (3,3) */
        worker.buildBlock(board.getTile(3,3));
        /* GodPower must be active -> can build again */
        Assert.assertTrue(worker.getGodPower());

        worker.useGodPower(false);
        /* update counters, state 3 -> 0 */
        worker.nextState();
        Assert.assertEquals(0, worker.getState());
    }

    @Test
    public void testGodPower() {
        initialize();
        /* set godPower cannot active conditions */
        poseidon.getPosition().setBlockLevel(1);

        /* build on (0,1) */
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,1)));
        worker.buildBlock(board.getTile(0,1));
        /* godPower inactive -> unmoved isn't on level 0 */
        Assert.assertFalse(worker.getGodPower());
    }

    @Test
    public void testBuildDome() {
        initialize();

        /* set can build dome conditions */
        board.getTile(1,0).setBlockLevel(3);
        board.getTile(2,1).setBlockLevel(3);
        Assert.assertEquals(poseidon.getPosition().getAdjacentTiles(), poseidon.getAdjacentTiles());

        /* set  buildable only (2,1) of unmoved worker(2,2)'s adjacent tiles */
        for(Tile t: poseidon.getPosition().getAdjacentTiles()){
            if(t != board.getTile(2,1)){
                t.setDomePresence(true);
            }
        }
        /* worker(0,0) builds dome on (1,0) */
        Assert.assertTrue(worker.canBuildDome(board.getTile(1,0)));
        worker.buildDome(board.getTile(1,0));

        /* godPower must be active */
        Assert.assertTrue(worker.getGodPower());
        worker.setGodPower(false);
        /* worker(0,0)'s adjacent tiles become unmoved worker's tiles */
        Assert.assertEquals(worker.getAdjacentTiles(), poseidon.getAdjacentTiles());
        /* build dome on (2,1) */
        Assert.assertTrue(worker.canBuildDome(board.getTile(2,1)));
        worker.buildDome(board.getTile(2,1));
        /* GodPower must be inactive -> unmoved worker(2,2) no others tiles available to build */
        Assert.assertFalse(worker.getGodPower());

    }

    @Test
    public void testUnmovedWorker(){
        initialize();
        /* when the unmoved worker = null (never in this game) */
        totalWorkerList.remove(poseidon);
        Assert.assertTrue(worker.canBuildBlock(board.getTile(0,1)));
        Assert.assertEquals(null, board.getTile(0,1).getWorker(totalWorkerList));
        worker.buildBlock(board.getTile(0,1));
        /* godPower can't active without another worker */
        Assert.assertFalse(worker.getGodPower());
    }
}