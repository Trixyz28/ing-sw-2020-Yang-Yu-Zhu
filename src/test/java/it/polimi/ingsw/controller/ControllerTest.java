package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.God.Artemis;
import it.polimi.ingsw.model.God.NoGod;
import it.polimi.ingsw.model.God.Prometheus;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.observers.Observable;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.text.TabableView;

/**
 * Tests of the <code>Controller</code>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */


public class ControllerTest {

    Model model = new Model();
    Controller controller = new Controller(model);
    Player player1 = new Player("A");
    Player player2 = new Player("B");
    Observable<Obj> observable = new Observable<Obj>();
    Player challenger;

    /**
     * Initialize the match : (before testing)
     * <p>
     * create match of 2 players
     * <p>
     * create an <code>Observable</code> to be listened by the <code>Controller</code>
     */
    @Before
    public void initialize() {
        model.initialize(2);
        model.getMatchPlayersList().add(player1);
        model.getMatchPlayersList().add(player2);
        player1.setPlayerID(0);
        player2.setPlayerID(1);
        observable.addObservers(controller);
    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * when <code>Controller</code> received a setup message from the observable:
     * <p>
     *     - create the first turn, which turn number is 0;
     *     <br>
     *     - set the challenger as current player
     * </p>
     */
    @Test
    public void testSetup() {
        initialize();
        /* the match hasn't been set up yet, the turn doesn't exist */
        Assert.assertNull(model.getCurrentTurn());

        /* setup */
        observable.notify(new Obj(Tags.SETUP,""));

        /* the turn is created */
        Assert.assertNotEquals(null, model.getCurrentTurn());
        /* challenger must be the current player (must start to define the godList) */
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer(), model.getMatchPlayersList().get(model.getChallengerID()));
        challenger = model.getMatchPlayersList().get(model.getChallengerID());
        /* the first turn (turn of setup) has turnNumber = 0 */
        Assert.assertEquals(0, model.getCurrentTurn().getTurnNumber());
    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * after setup,
     * when <code>Controller</code> received a message from the observable:
     * <p>
     *     message from challenger:
     *     <p>
     *         -if the message is a god name, the God must be added into the currentGodList, otherwise, nothing changes.
     *         <br>
     *         When the size of the currentGodList is equals to the number of the players, the defining is ended and
     *         the the player next to the Challenger will become the current player.
     *     </p>
     *     message from other players:
     *     <p>
     *         -nothing changes
     *     </p>
     * </p>
     */
    @Test
    public void testDefineGodList() {
        testSetup();
        /* obj -> message from current player (challenger) */
        Obj obj = new Obj(new GameMessage(null));
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        GameMessage gm = obj.getGameMessage();

        /* obj2 -> message from another player */
        Obj obj2 = new Obj(new GameMessage(null));
        obj2.setReceiver(model.getMatchPlayersList().get(model.getNextPlayerIndex()));
        GameMessage gm2 = obj2.getGameMessage();

        /* current player sends message "ATHENA" */
        gm.setAnswer("ATHENA");
        observable.notify(obj);

        /* The God "ATHENA" must be added into the currentGodList */
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());
        Assert.assertTrue(model.getGodsList().getCurrentGodList().contains("ATHENA"));
        Assert.assertFalse(model.getGodsList().checkLength());

        /* current player sends message "ooo" */
        gm.setAnswer("ooo");
        observable.notify(obj);

        /* "ooo" is not a God, nothing changed */
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());
        Assert.assertFalse(model.getGodsList().checkLength());

        /* another player sends message "APOLLO" */
        gm2.setAnswer("APOLLO");
        observable.notify(obj2);

        /* the player is not the challenger, nothing changed */
        Assert.assertFalse(model.getGodsList().getCurrentGodList().contains("APOLLO"));
        Assert.assertFalse(model.getGodsList().checkLength());

        /* current player sends message "ZEUS" */
        gm.setAnswer("ZEUS");
        observable.notify(obj);

        /* The God "ZEUS" must be added into the currentGodList -> end of define */
        Assert.assertEquals(2, model.getGodsList().getCurrentGodList().size());
        Assert.assertTrue(model.getGodsList().getCurrentGodList().contains("ZEUS"));
        Assert.assertTrue(model.getGodsList().checkLength());

        /* the defining of godList is finished, the current player is changed */
        Assert.assertNotEquals(model.getCurrentTurn().getCurrentPlayer(), challenger);

    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * after defined currentGodList,
     * when <code>Controller</code> received a message from the observable:
     * <p>
     *
     *     message from the current player:
     *     <p>
     *         - if the answer is right, the player's god card must become the chosen god and the current player must become
     *         the next player, otherwise, nothing changes.
     *     </p>
     *     When the all players except the challenger chose their god, the totalWorkers List must be created.
     *     The challenger's god card must be the remaining one in the currentGodList.
     * </p>
     */
    @Test
    public void testChooseGod() {
        testDefineGodList();

        /* obj = message from the current player */
        Obj obj = new Obj(new GameMessage(null));
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        GameMessage gm = obj.getGameMessage();

        /* the player sends "LIMUS" */
        gm.setAnswer("LIMUS");
        observable.notify(obj);

        /* nothing changed, "LIMUS" is not in the currentGodList */
        Assert.assertEquals(2, model.getGodsList().getCurrentGodList().size());

        /* the player sends "ZEUS" */
        gm.setAnswer("ZEUS");
        observable.notify(obj);

        /* the god "ZEUS" must be removed by the currentGodList */
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());

        /* the current player must become the next player (challenger) */
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer(), challenger);
        /* challenger's god card must be the rest god "ATHENA" */
        Assert.assertEquals("ATHENA", challenger.getGodCard());
        /* another player's god card must be the chosen one: "ZEUS"  */
        Assert.assertEquals("ZEUS", model.getMatchPlayersList().get(model.getNextPlayerIndex()).getGodCard());
        /* the totalWorkers List must be created */
        Assert.assertEquals(4, model.getTotalWorkers().size());
    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * after all players have chosen their gods,
     * when <code>Controller</code> received a message from the observable:
     * <p>
     *     message from the challenger:
     *     <p>
     *         -if the message is right, the chosen player must become the start player,
     *         otherwise, nothing changes.
     *     </p>
     *     The current player will changed to the start player.
     * </p>
     */
    @Test
    public void testSetStartingPlayer() {
        testChooseGod();
        /* obj = message from the current player (challenger) */
        Obj obj = new Obj(new GameMessage(null));
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        GameMessage gm = obj.getGameMessage();

        /* obj2 = message from another player */
        Obj obj2 = new Obj(new GameMessage(null));
        obj2.setReceiver(model.getMatchPlayersList().get(model.getNextPlayerIndex()));

        /* the other player sends "B" */
        Assert.assertNotEquals(challenger.getPlayerNickname(), obj2.getReceiver());
        obj2.getGameMessage().setAnswer("B");
        /* the start player ID will not change */
        Assert.assertNotEquals(player2.getPlayerID(), model.getStartingPlayerID());

        /* the challenger sends "C" */
        Assert.assertEquals(challenger.getPlayerNickname(), obj.getReceiver());
        gm.setAnswer("C");
        observable.notify(obj);
        /* the start player ID will not change -> because C is not a player nickname */
        Assert.assertFalse(model.setStartingPlayer(gm.getAnswer()));

        /* the challenger sends "B" */
        gm.setAnswer("B");
        observable.notify(obj);
        /* B must become the start player of the match (current player) */
        Assert.assertEquals(player2.getPlayerID(), model.getStartingPlayerID());
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());

    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * after chose the start player,
     * when <code>Controller</code> received a message from the observable:
     * <p>
     *
     *     message from the current player:
     *     <p>
     *         - if the answer is right, the player's worker must be placed on the chosen <code>Tile</code>, otherwise, nothing changes.
     *     </p>
     *     After placing the worker1, the current player must be changed to the next player.
     *     Finally, when all the players have placed their worker, the turn 0 will finish and the turn 1 will start.
     *     <br>
     *     The player of the turn 1 must be the start player.
     * </p>
     */
    @Test
    public void testPlaceWorkers() {
        testSetStartingPlayer();

        /* obj = message from the current player */
        Obj obj = new Obj(new Operation(0, 2,2));
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        Operation op = obj.getOperation();
        /* current player sends an operation on (2,2) */
        observable.notify(obj);

        /* the worker 0 must be placed on (2,2) */
        Assert.assertEquals(player2.chooseWorker(0).getPosition(), model.commandToTile(2,2));

        /* current player sends an operation on (1,1) */
        op.setPosition(1,1);
        observable.notify(obj);

        /* the worker 1 must be placed on (1,1) */
        Assert.assertEquals(player2.chooseWorker(1).getPosition(), model.commandToTile(1,1));

        /* the current player must be changed to the next player */
        Assert.assertEquals(player1, model.getCurrentTurn().getCurrentPlayer());

        /* obj2 = message from the new current player */
        Operation op2 = new Operation(0, 4,3);
        Obj obj2 = new Obj(op2);
        obj2.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        /* the player sends an operation on (4,3) */
        observable.notify(obj2);

        /* the worker 0 must be placed on (4,3) */
        Assert.assertEquals(player1.chooseWorker(0).getPosition(), model.commandToTile(4,3));

        /* the player sends an operation on (4,4) */
        op2.setPosition(4,4);
        observable.notify(obj2);

        /* the worker 1 must be placed on (4,4) */
        Assert.assertEquals(player1.chooseWorker(1).getPosition(), model.commandToTile(4,4));

        /* the turn of setup is ended, the next turn starts */
        Assert.assertEquals(1, model.getCurrentTurn().getTurnNumber());
        Assert.assertEquals(model.getMatchPlayersList().get(model.getStartingPlayerID()), model.getCurrentTurn().getCurrentPlayer());
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());
    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * Start of the game:
     * <p>
     *     - the turn number is 1;
     *     - the state of the turn is 0;
     *     - the current player is the start player = player2 (Nickname: "B", ID : 1);
     *     - no worker is chosen;
     * </p>
     * When <code>Controller</code> received a message from the observable:
     * <p>
     *      message from the current player:
     *      <p>
     *          - if the answer is right, the chosen worker must become the worker with the chosen index,
     *          otherwise, nothing changes.
     *      </p>
     *
     * </p>
     */
    @Test
    public void testChooseWorker() {
        testPlaceWorkers();
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        GameMessage gm = new GameMessage(Messages.worker);
        gm.setAnswer("0");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());

        /* current player sends "0" */
        observable.notify(obj);
        /* the chosenWorker must be the player's worker with index = 0 */
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0), model.getCurrentTurn().getChosenWorker());
        /* the state must be 0, because the choice hasn't been confirmed yet */
        Assert.assertEquals(0, model.getCurrentTurn().getState());
    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * After chose workerIndex,
     * when <code>Controller</code> received a message from the observable:
     * <p>
     *     the answer from the current player is "YES", the chosen worker must be confirmed and the state of the turn will be changed.
     * </p>
     */
    @Test
    public void testConfirmWorkerChoice() {
        testChooseWorker();

        GameMessage gm = new GameMessage(Messages.confirmWorker);
        gm.setAnswer("YES");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());

        /* the player sends "YES" */
        observable.notify(obj);
        Assert.assertTrue(model.checkAnswer(gm));

        /* the turn state became 1 -> confirmed worker choice */
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0), model.getCurrentTurn().getChosenWorker());
        /* next state */
        Assert.assertEquals(1, model.getCurrentTurn().getState());
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());
    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * After chose workerIndex,
     * when <code>Controller</code> received a message from the observable:
     * <p>
     *     the answer from the current player is "NO", the workerIndex must be re-asked, nothing changes.
     * </p>
     */
    @Test
    public void testConfirmWorkerChoice2() {
        testChooseWorker();

        GameMessage gm = new GameMessage(Messages.confirmWorker);
        gm.setAnswer("NO");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());

        /* the player sends "NO" */
        observable.notify(obj);

        Assert.assertFalse(model.checkAnswer(gm));

        /* the turn state didn't become 1 -> didn't confirm worker choice */
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());
    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * after confirmed the chosenWorker:
     *
     * <p>
     *     - currentPlayer = player2 (Nickname: "B", ID: 1);
     *     <br>
     *     - chosenWorker = worker 0;
     *     <br>
     *     - chosenWorker's position = (2,2);
     *     <br>
     *     - state = 1
     *     <br>
     * </p>
     * when <code>Controller</code> received a message from the observable:
     * <p>
     *
     *     message from the current player:
     *     <p>
     *         - if the answer is right, the chosen worker must "move" to the chosen <code>Tile</code>,
     *         otherwise, nothing changes.
     *     </p>
     *     After the move of the worker, the turn state will be changed.
     * </p>
     */
    @Test
    public void testMove() {
        testConfirmWorkerChoice();

        Obj obj = new Obj(new Operation( 1, 2,2));
        Operation move = obj.getOperation();
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());

        /* the player sends an operation on (1,2) */
        Assert.assertEquals("B", model.getCurrentTurn().getCurrentPlayer().getPlayerNickname());
        observable.notify(obj);
        /* the worker can't move to (1,2) */
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());

        /* the player sends an operation on (2,1) */
        move.setPosition(2,1);
        observable.notify(obj);

        /* the worker moves to (2,1) */
        Assert.assertNotEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(model.commandToTile(2,1), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(model.getCurrentTurn().getFinalTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(2, model.getCurrentTurn().getState());
    }


    /**
     * Test of <code>update()</code>:
     * <br>
     * after confirmed the chosenWorker:
     *
     * <p>
     *     - currentPlayer = player2 (Nickname: "B", ID: 1);
     *     <br>
     *     - chosenWorker = worker 0;
     *     <br>
     *     - chosenWorker's position = (2,1);
     *     <br>
     *     - state = 2
     *     <br>
     * </p>
     * when <code>Controller</code> received a message from the observable:
     * <p>
     *
     *     message from the current player:
     *     <p>
     *         - if the answer is right, the chosen worker must "build" on the chosen <code>Tile</code>,
     *         otherwise, nothing changes.
     *     </p>
     *     After the build of the worker, the turn state will be changed:
     *     <p>
     *         - currentPlayer = player1 (Nickname: "A", ID: 0);
     *         <br>
     *         - no worker is chosen;
     *         <br>
     *         - state = 0;
     *     </p>
     * </p>
     */
    @Test
    public void testBuild() {
        testMove();

        Operation build = new Operation( 2, 2,2);
        Obj obj = new Obj(build);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());

        /* the player sends an operation on (2,2) */
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canBuildBlock(model.commandToTile(2,2)));
        observable.notify(obj);

        /* the worker builds to (2,2) */
        Assert.assertEquals(1, model.commandToTile(2,2).getBlockLevel());
        /* the turn is ended */
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertEquals(2, model.getCurrentTurn().getTurnNumber());
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(player1, model.getCurrentTurn().getCurrentPlayer());
    }


    /**
     * Test of <code>update()</code>:
     * <br>
     * If the chosenWorker is Prometheus:
     * <br>
     * after confirmed the worker:
     * <p>
     *     - godPower of the chosenWorker is active (getGodPower == true);
     *     <br>
     *     - the state of the turn = 0;
     * </p>
     */
    @Test
    public void testBeforeGmUpdate() {
        testPlaceWorkers();
        Tile position1 = player2.chooseWorker(0).getPosition();
        Tile position2 = player2.chooseWorker(1).getPosition();

        /* change the workers to Prometheus */
        player2.setGodCard("PROMETHEUS");
        player2.getWorkerList().set(0, new Prometheus(new NoGod(player2.getPlayerID(), model.getConditions())));
        player2.getWorkerList().set(1, new Prometheus(new NoGod(player2.getPlayerID(), model.getConditions())));
        player2.chooseWorker(0).setPosition(position1);
        player2.chooseWorker(1).setPosition(position2);

        Assert.assertEquals(0, model.getCurrentTurn().getState());

        /* the player sends "0" */
        GameMessage gm = new GameMessage(Messages.worker);
        gm.setAnswer("0");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(obj);
        Assert.assertTrue(model.checkTurn(obj.getReceiver()));
        Assert.assertTrue(model.checkAnswer(obj.getGameMessage()));
        /* the player sends "YES" */
        gm = new GameMessage(Messages.confirmWorker);
        gm.setAnswer("YES");
        obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(obj);
        Assert.assertTrue(model.checkAnswer(obj.getGameMessage()));

        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0), model.getCurrentTurn().getChosenWorker());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker() instanceof Prometheus);
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());

    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * If the chosenWorker is Prometheus:
     * <br>
     * after confirmed the worker:
     * <br>
     *     - godPower of the chosenWorker is active (getGodPower == true);
     *     - the state of the turn = 0;
     * when <code>Controller</code> received a message from the observable:
     * <p>
     *     - if the answer from the current player is "BUILD", the state of the worker must become 2,
     *     and the godPower will become inactive.
     * </p>
     */
    @Test
    public void testGmUpdate() {
        testBeforeGmUpdate();
        /* the player sends "BUILD" */
        GameMessage gm = new GameMessage(GodPowerMessage.valueOf("PROMETHEUS").getMessage());
        gm.setAnswer("BUILD");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(obj);

        Assert.assertTrue(model.checkAnswer(gm));
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());
        /* state must be 2 -> build state */
        Assert.assertEquals(2, model.getCurrentTurn().getState());
    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * If the chosenWorker is Prometheus:
     * <br>
     * after confirmed the worker:
     * <p>
     *     - godPower of the chosenWorker is active (getGodPower == true);
     *     <br>
     *     - the state of the turn = 0;
     * </p>
     * when <code>Controller</code> received a message from the observable:
     * <p>
     *     - if the answer from the current player is "MOVE", the state of the worker must become 1,
     *     and the godPower will become inactive.
     * </p>
     */
    @Test
    public void testGmUpdate2() {
        testBeforeGmUpdate();
        GameMessage gm = new GameMessage(GodPowerMessage.valueOf("PROMETHEUS").getMessage());
        gm.setAnswer("MOVE");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(obj);

        Assert.assertTrue(model.checkAnswer(gm));
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());
        /* state must be 1 -> move state */
        Assert.assertEquals(1, model.getCurrentTurn().getState());
    }

    /**
     * Test of <code>update()</code>:
     * <br>
     * If the chosenWorker is Artemis:
     * <br>
     * after confirmed the worker:
     * <p>
     *     - godPower of the chosenWorker is inactive (getGodPower == false);
     *     <br>
     *     - the state of the turn = 1;
     * </p>
     * after first move of the worker:
     * <p>
     *     - godPower of the chosenWorker is active (getGodPower == true);
     *     <br>
     *     - the state of the turn = 1; (didn't change)
     * </p>
     * when <code>Controller</code> received a message from the observable:
     * <p>
     *     - if the answer from the current player is "NO", the state of the worker must become 2,
     *     and the godPower will become inactive.
     * </p>
     */
    @Test
    public void testGmUpdate3() {
        testPlaceWorkers();
        Tile position1 = player2.chooseWorker(0).getPosition();
        Tile position2 = player2.chooseWorker(1).getPosition();
        player2.setGodCard("ARTEMIS");
        player2.getWorkerList().set(0, new Artemis(new NoGod(player2.getPlayerID(), model.getConditions())));
        player2.getWorkerList().set(1, new Artemis(new NoGod(player2.getPlayerID(), model.getConditions())));
        player2.chooseWorker(0).setPosition(position1);
        player2.chooseWorker(1).setPosition(position2);

        /* choosing the worker with index 0 */
        GameMessage gm = new GameMessage(Messages.worker);
        gm.setAnswer("0");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(obj);
        /* current worker must be the chosen worker */
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0), model.getCurrentTurn().getChosenWorker());

        /* confirm the worker choice */
        gm = new GameMessage(Messages.confirmWorker);
        gm.setAnswer("YES");
        obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(obj);

        /* current worker must be the chosen worker */
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0), model.getCurrentTurn().getChosenWorker());
        /* the state of the turn must be 1 -> end of choosing worker, start to move */
        Assert.assertEquals(1, model.getCurrentTurn().getState());

        /* before move godPower is not activated */
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());

        /* the player sends operation on (2,1) */
        Obj move = new Obj(new Operation( 1, 2,1));
        move.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canMove(model.commandToTile(2,1)));
        observable.notify(move);

        /* the worker moved to (2,1) */
        Assert.assertEquals(model.commandToTile(2,1), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker() instanceof Artemis);
        /* the godPower must be active */
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());

        /* the state will not be changed */
        Assert.assertEquals(1, model.getCurrentTurn().getState());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());

        /* the player sends "MOVE" */
        Obj godAnswer = new Obj(new GameMessage(GodPowerMessage.valueOf("ARTEMIS").getMessage()));
        godAnswer.getGameMessage().setAnswer("MOVE");
        godAnswer.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(godAnswer);
        /* nothing changed -> move is a wrong answer */
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());

        /* the player sends "NO" */
        godAnswer.getGameMessage().setAnswer("NO");
        observable.notify(godAnswer);

        /* the state must become 2 */
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(model.getCurrentTurn().getFinalTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(2, model.getCurrentTurn().getState());
    }



    }