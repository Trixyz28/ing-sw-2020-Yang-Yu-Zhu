package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.GodPowerMessage;
import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.messages.Obj;
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

public class ControllerTest extends TestCase {

    Model model = new Model();
    Controller controller = new Controller(model);
    Player player1 = new Player("A");
    Player player2 = new Player("B");
    Observable observable = new Observable();
    Player challenger;

    @Before
    public void initialize() {
        model.initialize(2);
        model.getMatchPlayersList().add(player1);
        model.getMatchPlayersList().add(player2);
        player1.setPlayerID(0);
        player2.setPlayerID(1);
        observable.addObservers(controller);
    }


    @Test
    public void testSetup() {
        initialize();
        Assert.assertEquals(null, model.getCurrentTurn());
        observable.notify("setup");
        Assert.assertNotEquals(null, model.getCurrentTurn());
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer(), model.getMatchPlayersList().get(model.getChallengerID()));
        challenger = model.getMatchPlayersList().get(model.getChallengerID());
        Assert.assertEquals(0, model.getCurrentTurn().getTurnNumber());
    }

    @Test
    public void testDefineGodList() {
        testSetup();
        Obj obj = new Obj(new GameMessage(null));
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        Obj obj2 = new Obj(new GameMessage(null));
        obj2.setReceiver(model.getMatchPlayersList().get(model.getNextPlayerIndex()));

        GameMessage gm = obj.getGameMessage();
        GameMessage gm2 = obj2.getGameMessage();
        gm.setAnswer("ATHENA");
        observable.notify(obj);
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());
        Assert.assertTrue(model.getGodsList().getCurrentGodList().contains("ATHENA"));
        Assert.assertFalse(model.getGodsList().checkLength());
        gm.setAnswer("ooo");
        observable.notify(obj);
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());
        Assert.assertFalse(model.getGodsList().checkLength());

        gm2.setAnswer("APOLLO");
        observable.notify(obj2);
        Assert.assertFalse(model.getGodsList().getCurrentGodList().contains("APOLLO"));
        Assert.assertFalse(model.getGodsList().checkLength());

        gm.setAnswer("ZEUS");
        observable.notify(obj);
        Assert.assertEquals(2, model.getGodsList().getCurrentGodList().size());
        Assert.assertTrue(model.getGodsList().getCurrentGodList().contains("ZEUS"));
        Assert.assertTrue(model.getGodsList().checkLength());

        Assert.assertNotEquals(model.getCurrentTurn().getCurrentPlayer(), challenger);

    }

    @Test
    public void testChooseGod() {
        testDefineGodList();
        Obj obj = new Obj(new GameMessage(null));
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        GameMessage gm = obj.getGameMessage();

        gm.setAnswer("LIMUS");
        observable.notify(obj);
        Assert.assertEquals(2, model.getGodsList().getCurrentGodList().size());

        gm.setAnswer("ZEUS");
        observable.notify(obj);
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());

        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer(), challenger);
        Assert.assertEquals("ATHENA", challenger.getGodCard());
        Assert.assertEquals("ZEUS", model.getMatchPlayersList().get(model.getNextPlayerIndex()).getGodCard());
        Assert.assertEquals(4, model.getTotalWorkers().size());
    }

    @Test
    public void testSetStartingPlayer() {
        testChooseGod();
        Obj obj = new Obj(new GameMessage(null));
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        GameMessage gm = obj.getGameMessage();


        Obj obj2 = new Obj(new GameMessage(null));
        obj2.setReceiver(model.getMatchPlayersList().get(model.getNextPlayerIndex()));
        /*
        observable.notify();
        Assert.assertNotEquals(player2.getPlayerID(), model.getStartingPlayerID());
        */
        Assert.assertNotEquals(challenger.getPlayerNickname(), obj2.getReceiver());
        obj2.getGameMessage().setAnswer("B");
        Assert.assertNotEquals(player2.getPlayerID(), model.getStartingPlayerID());

        Assert.assertEquals(challenger.getPlayerNickname(), obj.getReceiver());
        gm.setAnswer("C");
        observable.notify(obj);
        Assert.assertFalse(model.setStartingPlayer(gm.getAnswer()));

        gm.setAnswer("B");
        observable.notify(obj);
        Assert.assertEquals(player2.getPlayerID(), model.getStartingPlayerID());
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());

    }

    @Test
    public void testPlaceWorkers() {
        testSetStartingPlayer();

        Obj obj = new Obj(new Operation(0, 2,2));
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        Operation op = obj.getOperation();
        observable.notify(obj);
        Assert.assertEquals(player2.chooseWorker(0).getPosition(), model.commandToTile(2,2));
        op.setPosition(1,1);
        observable.notify(obj);
        Assert.assertEquals(player2.chooseWorker(1).getPosition(), model.commandToTile(1,1));

        Assert.assertEquals(player1, model.getCurrentTurn().getCurrentPlayer());

        Operation op2 = new Operation(0, 4,3);
        Obj obj2 = new Obj(op2);
        obj2.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(obj2);
        Assert.assertEquals(player1.chooseWorker(0).getPosition(), model.commandToTile(4,3));
        op2.setPosition(4,4);
        observable.notify(obj2);
        Assert.assertEquals(player1.chooseWorker(1).getPosition(), model.commandToTile(4,4));

        Assert.assertEquals(1, model.getCurrentTurn().getTurnNumber());
        Assert.assertEquals(model.getMatchPlayersList().get(model.getStartingPlayerID()), model.getCurrentTurn().getCurrentPlayer());
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void testChooseWorker() {
        testPlaceWorkers();
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        GameMessage gm = new GameMessage(Messages.worker);
        gm.setAnswer("0");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());

        observable.notify(obj);
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0), model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(0, model.getCurrentTurn().getState());
    }

    @Test
    public void testConfirmWorkerChoice() {
        testChooseWorker();

        GameMessage gm = new GameMessage(Messages.confirmWorker);
        gm.setAnswer("YES");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());

        observable.notify(obj);

        Assert.assertTrue(model.checkAnswer(gm));

        /* the turn state became 1 -> confirmed worker choice */
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0), model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(1, model.getCurrentTurn().getState());
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void testConfirmWorkerChoice2() {
        testChooseWorker();

        GameMessage gm = new GameMessage(Messages.confirmWorker);
        gm.setAnswer("NO");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());

        observable.notify(obj);

        Assert.assertFalse(model.checkAnswer(gm));

        /* the turn state didn't become 1 -> didn't confirm worker choice */
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void testMove() {
        testConfirmWorkerChoice();

        Obj obj = new Obj(new Operation( 1, 2,2));
        Operation move = obj.getOperation();
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());

        Assert.assertEquals("B", model.getCurrentTurn().getCurrentPlayer().getPlayerNickname());
        observable.notify(obj);
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());

        move.setPosition(2,1);
        observable.notify(obj);
        Assert.assertNotEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(model.commandToTile(2,1), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(model.getCurrentTurn().getFinalTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(2, model.getCurrentTurn().getState());
    }



    @Test
    public void testBuild() {
        testMove();

        Operation build = new Operation( 2, 2,2);
        Obj obj = new Obj(build);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());

        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canBuildBlock(model.commandToTile(2,2)));
        observable.notify(obj);
        Assert.assertEquals(1, model.commandToTile(2,2).getBlockLevel());
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertEquals(2, model.getCurrentTurn().getTurnNumber());
        Assert.assertEquals(null, model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(player1, model.getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void testBeforeGmUpdate() {
        testPlaceWorkers();
        Tile position1 = player2.chooseWorker(0).getPosition();
        Tile position2 = player2.chooseWorker(1).getPosition();
        player2.setGodCard("PROMETHEUS");
        player2.getWorkerList().set(0, new Prometheus(new NoGod(player2.getPlayerID(), model.getConditions())));
        player2.getWorkerList().set(1, new Prometheus(new NoGod(player2.getPlayerID(), model.getConditions())));
        player2.chooseWorker(0).setPosition(position1);
        player2.chooseWorker(1).setPosition(position2);

        Assert.assertEquals(0, model.getCurrentTurn().getState());
        GameMessage gm = new GameMessage(Messages.worker);
        gm.setAnswer("0");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(obj);
        Assert.assertTrue(model.checkTurn(obj.getReceiver()));
        Assert.assertTrue(model.checkAnswer(obj.getGameMessage()));

        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0), model.getCurrentTurn().getChosenWorker());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker() instanceof Prometheus);
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());

    }

    @Test
    public void testGmUpdate() {
        testBeforeGmUpdate();
        GameMessage gm = new GameMessage(GodPowerMessage.valueOf("PROMETHEUS").getMessage());
        gm.setAnswer("BUILD");
        Obj obj = new Obj(gm);
        obj.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(obj);
        Assert.assertTrue(model.checkAnswer(gm));
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(2, model.getCurrentTurn().getState());
    }

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
        Assert.assertEquals(1, model.getCurrentTurn().getState());
    }

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

        Obj move = new Obj(new Operation( 1, 2,1));
        move.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(move);

        Assert.assertTrue(model.getCurrentTurn().getChosenWorker() instanceof Artemis);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());

        Assert.assertEquals(1, model.getCurrentTurn().getState());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());

        Obj godAnswer = new Obj(new GameMessage(GodPowerMessage.valueOf("ARTEMIS").getMessage()));
        godAnswer.getGameMessage().setAnswer("MOVE");
        godAnswer.setReceiver(model.getCurrentTurn().getCurrentPlayer());
        observable.notify(godAnswer);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());

        godAnswer.getGameMessage().setAnswer("NO");
        observable.notify(godAnswer);
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(model.getCurrentTurn().getFinalTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(2, model.getCurrentTurn().getState());
    }



    }