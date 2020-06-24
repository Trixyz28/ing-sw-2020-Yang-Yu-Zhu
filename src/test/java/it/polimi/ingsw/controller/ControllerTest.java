package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.GodPowerMessage;
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
        GameMessage gm = new GameMessage(model.getCurrentTurn().getCurrentPlayer(), null, true);
        GameMessage gm2 = new GameMessage(model.getMatchPlayersList().get(model.getNextPlayerIndex()), null, true);

        gm.setAnswer("ATHENA");
        observable.notify(gm);
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());
        Assert.assertTrue(model.getGodsList().getCurrentGodList().contains("ATHENA"));
        Assert.assertFalse(model.getGodsList().checkLength());
        gm.setAnswer("ooo");
        observable.notify(gm);
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());
        Assert.assertFalse(model.getGodsList().checkLength());

        gm2.setAnswer("APOLLO");
        observable.notify(gm2);
        Assert.assertFalse(model.getGodsList().getCurrentGodList().contains("APOLLO"));
        Assert.assertFalse(model.getGodsList().checkLength());

        gm.setAnswer("ZEUS");
        observable.notify(gm);
        Assert.assertEquals(2, model.getGodsList().getCurrentGodList().size());
        Assert.assertTrue(model.getGodsList().getCurrentGodList().contains("ZEUS"));
        Assert.assertTrue(model.getGodsList().checkLength());

        Assert.assertNotEquals(model.getCurrentTurn().getCurrentPlayer(), challenger);

    }

    @Test
    public void testChooseGod() {
        testDefineGodList();
        GameMessage gm = new GameMessage(model.getCurrentTurn().getCurrentPlayer(), null, true);

        gm.setAnswer("LIMUS");
        observable.notify(gm);
        Assert.assertEquals(2, model.getGodsList().getCurrentGodList().size());

        gm.setAnswer("ZEUS");
        observable.notify(gm);
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());

        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer(), challenger);
        Assert.assertEquals("ATHENA", challenger.getGodCard());
        Assert.assertEquals("ZEUS", model.getMatchPlayersList().get(model.getNextPlayerIndex()).getGodCard());
        Assert.assertEquals(4, model.getTotalWorkers().size());
    }

    @Test
    public void testSetStartingPlayer() {
        testChooseGod();
        GameMessage gm = new GameMessage(model.getCurrentTurn().getCurrentPlayer(), null, true);

        observable.notify("B");
        Assert.assertNotEquals(player2.getPlayerID(), model.getStartingPlayerID());
        gm.setAnswer("C");
        observable.notify(gm);
        Assert.assertFalse(model.setStartingPlayer(gm.getAnswer()));

        gm.setAnswer("B");
        observable.notify(gm);
        Assert.assertEquals(player2.getPlayerID(), model.getStartingPlayerID());
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());

    }

    @Test
    public void testPlaceWorkers() {
        testSetStartingPlayer();

        Operation op = new Operation(model.getCurrentTurn().getCurrentPlayer(), 0, 2,2);
        observable.notify(op);
        Assert.assertEquals(player2.chooseWorker(0).getPosition(), model.commandToTile(2,2));
        op.setPosition(1,1);
        observable.notify(op);
        Assert.assertEquals(player2.chooseWorker(1).getPosition(), model.commandToTile(1,1));

        Assert.assertEquals(player1, model.getCurrentTurn().getCurrentPlayer());

        Operation op2 = new Operation(model.getCurrentTurn().getCurrentPlayer(), 0, 4,3);
        observable.notify(op2);
        Assert.assertEquals(player1.chooseWorker(0).getPosition(), model.commandToTile(4,3));
        op2.setPosition(4,4);
        observable.notify(op2);
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
        observable.notify(0);
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0), model.getCurrentTurn().getChosenWorker());
        Assert.assertEquals(1, model.getCurrentTurn().getState());
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void testMove() {
        testChooseWorker();

        Operation move = new Operation(model.getCurrentTurn().getCurrentPlayer(), 1, 2,2);
        Assert.assertEquals("B", model.getCurrentTurn().getCurrentPlayer().getPlayerNickname());
        observable.notify(move);
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());

        move.setPosition(2,1);
        observable.notify(move);
        Assert.assertNotEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(model.commandToTile(2,1), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(model.getCurrentTurn().getFinalTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(2, model.getCurrentTurn().getState());
    }



    @Test
    public void testBuild() {
        testMove();

        Operation build = new Operation(model.getCurrentTurn().getCurrentPlayer(), 2, 2,2);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().canBuildBlock(model.commandToTile(2,2)));
        observable.notify(build);
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
        observable.notify(0);
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0), model.getCurrentTurn().getChosenWorker());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker() instanceof Prometheus);
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());

    }

    @Test
    public void testGmUpdate() {
        testBeforeGmUpdate();
        GameMessage gm = new GameMessage(player2, GodPowerMessage.valueOf("PROMETHEUS").getMessage(), false);
        gm.setAnswer("BUILD");
        observable.notify(gm);
        Assert.assertTrue(model.checkAnswer(gm));
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(2, model.getCurrentTurn().getState());
    }

    @Test
    public void testGmUpdate2() {
        testBeforeGmUpdate();
        GameMessage gm = new GameMessage(player2, GodPowerMessage.valueOf("PROMETHEUS").getMessage(), false);
        gm.setAnswer("MOVE");
        observable.notify(gm);
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

        observable.notify(0);
        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0), model.getCurrentTurn().getChosenWorker());

        Assert.assertEquals(1, model.getCurrentTurn().getState());
        Operation move = new Operation(model.getCurrentTurn().getCurrentPlayer(), 1, 2,1);
        observable.notify(move);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker() instanceof Artemis);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(model.getCurrentTurn().getInitialTile(), model.getCurrentTurn().getChosenWorker().getPosition());

        Assert.assertEquals(1, model.getCurrentTurn().getState());
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());

        GameMessage gm = new GameMessage(player2, GodPowerMessage.valueOf("ARTEMIS").getMessage(), false);
        gm.setAnswer("MOVE");
        observable.notify(gm);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().getGodPower());

        gm.setAnswer("NO");
        observable.notify(gm);
        Assert.assertFalse(model.getCurrentTurn().getChosenWorker().getGodPower());
        Assert.assertEquals(model.getCurrentTurn().getFinalTile(), model.getCurrentTurn().getChosenWorker().getPosition());
        Assert.assertEquals(2, model.getCurrentTurn().getState());
    }



    }