package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.*;
import it.polimi.ingsw.observers.Observer;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class ModelTest extends TestCase {

    Model model = new Model();
    Messages messages = new Messages();
    Player player1 = new Player("A");
    Player player2 = new Player("B");

    @Test
    public void testInitialize() {


        model.initialize(2);
        player1.setPlayerID(0);
        player2.setPlayerID(1);
        model.addPlayer(player1);
        model.addPlayer(player2);

        assertEquals(2,model.getPlayersNumber());
        assertEquals("A",model.getMatchPlayersList().get(0).getPlayerNickname());
        assertEquals("B",model.getMatchPlayersList().get(1).getPlayerNickname());
    }


    @Test
    public void testChallenger() {
        testInitialize();
        model.challengerStart();
        Assert.assertTrue(model.getChallengerID()==0 || model.getChallengerID()==1);
        Assert.assertTrue((model.getMatchPlayersList().get(0).isChallenger() && !model.getMatchPlayersList().get(1).isChallenger())
        || (!model.getMatchPlayersList().get(0).isChallenger() && model.getMatchPlayersList().get(1).isChallenger()));
        Assert.assertSame(model.getCurrentTurn().getCurrentPlayer(), model.getMatchPlayersList().get(model.getChallengerID()));
    }

    @Test
    public void testDefineGodList() {
        testChallenger();
        Assert.assertFalse(model.defineGodList("aaa"));
        Assert.assertFalse(model.defineGodList("bbb"));
        Assert.assertEquals(0, model.getGodsList().getCurrentGodList().size());
        Assert.assertFalse(model.defineGodList("HEPHAESTUS"));
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());
        Assert.assertTrue(model.defineGodList("HESTIA"));
        Assert.assertTrue(model.getGodsList().checkLength());
    }

    @Test
    public void testChoiceGod() {
        testDefineGodList();
        Assert.assertEquals(model.getChallengerID(), model.getCurrentTurn().getCurrentPlayer().getPlayerID());
        model.nextChoiceGod();
        Assert.assertNotEquals(model.getChallengerID(), model.getCurrentTurn().getCurrentPlayer().getPlayerID());

        Assert.assertFalse(model.chooseGod("PAN"));

        Assert.assertTrue(model.chooseGod("HEPHAESTUS"));
        Assert.assertFalse(model.getGodsList().getCurrentGodList().contains("HEPHAESTUS"));
        Assert.assertEquals("HEPHAESTUS", model.getCurrentTurn().getCurrentPlayer().getGodCard());
        Assert.assertTrue(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0) instanceof Hephaestus);

        model.nextChoiceGod();
        Assert.assertEquals(model.getChallengerID(), model.getCurrentTurn().getCurrentPlayer().getPlayerID());
        Assert.assertEquals("HESTIA", model.getCurrentTurn().getCurrentPlayer().getGodCard());
        Assert.assertTrue(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0) instanceof Hestia);
        Assert.assertEquals(model.getCondition(), model.getCurrentTurn().getCurrentPlayer().chooseWorker(0).getConditions());

    }

    @Test
    public void testCreateTotalWorkerList() {
        testChoiceGod();
        Assert.assertTrue(model.getTotalWorkers().containsAll(player1.getWorkerList()));
        Assert.assertTrue(model.getTotalWorkers().containsAll(player2.getWorkerList()));
    }


    @Test
    public void testStartingPlayer() {
        testChoiceGod();
        Assert.assertFalse(model.setStartingPlayer("C"));
        Assert.assertTrue(model.setStartingPlayer("B"));
        Assert.assertEquals(player2.getPlayerID(),model.getStartingPlayerID());
        Assert.assertEquals("B",model.getMatchPlayersList().get(model.getStartingPlayerID()).getPlayerNickname());

    }

    @Test
    public void testStartTurn() {
        testStartingPlayer();
        model.startTurn();
        Assert.assertEquals(model.getMatchPlayersList().get(model.getStartingPlayerID()), model.getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void testingNextPlayer() {
        testStartingPlayer();
        model.getCurrentTurn().nextTurn(model.getMatchPlayersList().get(model.getStartingPlayerID()));

        Assert.assertEquals(player1.getPlayerID(), model.getNextPlayerIndex());
        model.getCurrentTurn().nextTurn(model.getMatchPlayersList().get(model.getNextPlayerIndex()));
        Assert.assertSame(player1, model.getCurrentTurn().getCurrentPlayer());
        model.getCurrentTurn().nextTurn(model.getMatchPlayersList().get(model.getNextPlayerIndex()));
        Assert.assertSame(player2, model.getCurrentTurn().getCurrentPlayer());
    }


    @Test
    public void testCommandToTile() {
        testInitialize();
        assertSame(model.getBoard().getMap()[2][3],model.commandToTile(2,3));
    }


    @Test
    public void testCheckTurn() {
        testStartTurn();
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());

        Assert.assertFalse(model.checkTurn(player1.getPlayerNickname()));
        Assert.assertTrue(model.checkTurn(player2.getPlayerNickname()));
    }

    @Test
    public void testCheckAnswer() {
        testStartTurn();
        model.getCurrentTurn().choseWorker(player2.chooseWorker(0));
        GodPowerMessage god = GodPowerMessage.valueOf(player2.getGodCard());
        GameMessage gm = new GameMessage(player2, god.getMessage(), false);
        gm.setAnswer("ciao!");
        Assert.assertFalse(model.checkAnswer(gm));
        gm.setAnswer("NO");
        Assert.assertTrue(model.checkAnswer(gm));
        if(model.getCurrentTurn().getChosenWorker() instanceof Hestia){
            gm.setAnswer("YES");
            Assert.assertTrue(model.checkAnswer(gm));
        }else {
            model.getCurrentTurn().getChosenWorker().buildBlock(model.getBoard().getTile(0,0));
            gm.setAnswer("YES");
            Assert.assertTrue(model.checkAnswer(gm));
        }
    }

    @Test
    public void testOperation(){
        testStartTurn();
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        model.getCurrentTurn().choseWorker(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0));
        model.getCurrentTurn().nextState();
        Assert.assertEquals(1, model.getCurrentTurn().getState());
        model.operation();
    }


    @Test
    public void testCheckWin() {
        testOperation();
        Tile position = new Tile();
        position.setBlockLevel(2);
        Tile destination = new Tile();
        destination.setBlockLevel(3);

        model.getCurrentTurn().setInitialTile(position);
        model.getCurrentTurn().getChosenWorker().setPosition(destination);
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().checkWin(position));
        Assert.assertTrue(model.checkWin());
        Assert.assertTrue(model.isGameOver());

        destination.setBlockLevel(2);
        assertFalse(model.checkWin());

        Conditions conditions = new Conditions();
        UndecoratedWorker pan = new Pan(new NoGod(0, conditions));
        pan.setPosition(position);
        model.getCurrentTurn().choseWorker(pan);
        model.getCurrentTurn().setFinalTile(destination);
        destination.setBlockLevel(0);
        pan.setPosition(destination);
        assertTrue(model.checkWin());

    }





    @Test
    public void testWorkerChosen() {
        model.setWorkerChosen(true);
        assertTrue(model.isWorkerChosen());
    }

    @Test
    public void testLose() {
        testOperation();
        model.getCurrentTurn().getChosenWorker().setPosition(model.commandToTile(0,0));
        Assert.assertFalse(model.checkLose());;

        model.getMatchPlayersList().get(0).chooseWorker(0).setPosition(model.commandToTile(0,1));
        model.getMatchPlayersList().get(0).chooseWorker(1).setPosition(model.commandToTile(1,1));
        model.getMatchPlayersList().get(1).chooseWorker(1).setPosition(model.commandToTile(1,0));
        Assert.assertEquals(1, model.getCurrentTurn().getState());

        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertTrue(model.checkLose());
        Assert.assertEquals(2, model.getMatchPlayersList().size());
        Assert.assertEquals(player1, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertTrue(model.isGameOver());
    }

    @Test
    public void testLose2() {
        testStartTurn();
        Player player3 = new Player("C");
        player3.setPlayerID(2);
        model.addPlayer(player3);
        player3.createWorker("MINOTAUR", model.getCondition(), model.getTotalWorkers());
        Assert.assertTrue(player3.chooseWorker(0) instanceof Minotaur);
        Assert.assertSame(player3.chooseWorker(0).getClass(), player3.chooseWorker(1).getClass());
        model.getTotalWorkers().addAll(player3.getWorkerList());


        model.getCurrentTurn().nextTurn(player1);
        model.getCurrentTurn().choseWorker(player1.chooseWorker(0));
        model.getCurrentTurn().getChosenWorker().setPosition(model.commandToTile(0,0));
        model.getMatchPlayersList().get(1).chooseWorker(0).setPosition(model.commandToTile(0,1));
        model.getMatchPlayersList().get(0).chooseWorker(1).setPosition(model.commandToTile(1,1));
        model.getMatchPlayersList().get(1).chooseWorker(1).setPosition(model.commandToTile(1,0));

        model.getCurrentTurn().nextState();
        Assert.assertEquals(1, model.getCurrentTurn().getState());
        Assert.assertEquals(player1, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertEquals(3, model.getMatchPlayersList().size());
        Assert.assertEquals(6, model.getTotalWorkers().size());
        Assert.assertTrue(model.checkLose());
        model.getCurrentTurn().nextTurn(model.getMatchPlayersList().get(model.getNextPlayerIndex()));
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertEquals(2, model.getMatchPlayersList().size());
        Assert.assertEquals(4, model.getTotalWorkers().size());
    }



    @Test
    public void testGameOver() {
        testStartTurn();
        model.gameOver();
        assertTrue(model.isGameOver());
    }


    @Test
    public void testTotalWorkerList() {
        testInitialize();
        Conditions conditions = new Conditions();
        model.getMatchPlayersList().get(0).createWorker("DEMETER",conditions,model.getTotalWorkers());
        model.getMatchPlayersList().get(1).createWorker("PROMETHEUS",conditions,model.getTotalWorkers());
        model.createTotalWorkerList();

        assertEquals(4,model.getTotalWorkers().size());
        assertTrue(model.getTotalWorkers().get(1) instanceof Demeter);
        assertTrue(model.getTotalWorkers().get(2) instanceof Prometheus);


    }

    @Test
    public void testSendMessage() {
        testOperation();
        Observer observer = new Observer() {
            @Override
            public void update(Object message) {
                Assert.assertTrue(message instanceof GameMessage);
                GameMessage gm = (GameMessage) message;
                Assert.assertEquals(gm.getMessage(), "ciao");
                Assert.assertTrue(gm.readOnly());
                Assert.assertEquals(gm.getPlayer(), model.getCurrentTurn().getCurrentPlayer().getPlayerNickname());
            }
        };
        model.addObservers(observer);
        model.sendMessage("ciao");

    }

    @Test
    public void testSendGodPowerMessage() {
        testOperation();
        Observer observer = new Observer() {
            @Override
            public void update(Object message) {
                Assert.assertTrue(message instanceof GameMessage);
                GameMessage gm = (GameMessage) message;
                GodPowerMessage god = GodPowerMessage.valueOf(model.getCurrentTurn().getCurrentPlayer().getGodCard());
                Assert.assertEquals(gm.getMessage(), god.getMessage());
                Assert.assertFalse(gm.readOnly());
                Assert.assertEquals(gm.getPlayer(), model.getCurrentTurn().getCurrentPlayer().getPlayerNickname());
            }
        };
        model.addObservers(observer);
        model.sendMessage(model.getCurrentTurn().getCurrentPlayer().getGodCard());

    }

}