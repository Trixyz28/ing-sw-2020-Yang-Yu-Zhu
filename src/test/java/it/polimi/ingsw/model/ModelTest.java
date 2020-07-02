package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.God.*;
import it.polimi.ingsw.observers.Observer;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ModelTest extends TestCase {

    Model model = new Model();
    Player player1 = new Player("A");
    Player player2 = new Player("B");

    @Test
    public void testInitialize() {


        /* create match of 2 players */
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
        /* challenger chosen randomly */
        Assert.assertTrue(model.getChallengerID()==0 || model.getChallengerID()==1);
        Assert.assertTrue((model.getMatchPlayersList().get(0).isChallenger() && !model.getMatchPlayersList().get(1).isChallenger())
        || (!model.getMatchPlayersList().get(0).isChallenger() && model.getMatchPlayersList().get(1).isChallenger()));
        /* challenger must be the current player */
        Assert.assertSame(model.getCurrentTurn().getCurrentPlayer(), model.getMatchPlayersList().get(model.getChallengerID()));
    }

    @Test
    public void testDefineGodList() {
        testChallenger();
        /* wrong input can't be added in the currentGodList */
        Assert.assertFalse(model.defineGodList("aaa"));
        Assert.assertFalse(model.defineGodList("bbb"));
        Assert.assertEquals(0, model.getGodsList().getCurrentGodList().size());

        /* add Hephaestus */
        Assert.assertFalse(model.defineGodList("HEPHAESTUS"));
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());
        /* add Hestia */
        Assert.assertTrue(model.defineGodList("HESTIA"));
        /* end to define currentGodList -> 2 players 2 Gods */
        Assert.assertTrue(model.getGodsList().checkLength());
    }

    @Test
    public void testChoiceGod() {
        testDefineGodList();
        /* CurrentGodList: "HEPHAESTUS", "HESTIA" */
        Assert.assertEquals(model.getChallengerID(), model.getCurrentTurn().getCurrentPlayer().getPlayerID());
        /* end define -> start choosing god */
        model.nextChoiceGod();
        /* current player must be the player next to the challenger */
        Assert.assertNotEquals(model.getChallengerID(), model.getCurrentTurn().getCurrentPlayer().getPlayerID());

        /* invalid choice */
        Assert.assertFalse(model.chooseGod("PAN"));

        /* choose Hephaestus : set player god card, create workers, remove Hephaestus */
        Assert.assertTrue(model.chooseGod("HEPHAESTUS"));
        Assert.assertFalse(model.getGodsList().getCurrentGodList().contains("HEPHAESTUS"));
        Assert.assertEquals("HEPHAESTUS", model.getCurrentTurn().getCurrentPlayer().getGodCard());
        Assert.assertTrue(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0) instanceof Hephaestus);

        /* end choice -> next choice */
        model.nextChoiceGod();
        /* the remaining god "HESTIA" is given automatically to the challenger, create workers */
        Assert.assertEquals(model.getChallengerID(), model.getCurrentTurn().getCurrentPlayer().getPlayerID());
        Assert.assertEquals("HESTIA", model.getCurrentTurn().getCurrentPlayer().getGodCard());
        Assert.assertTrue(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0) instanceof Hestia);
        Assert.assertEquals(model.getConditions(), model.getCurrentTurn().getCurrentPlayer().chooseWorker(0).getConditions());

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
        /* "C" -> invalid input */
        Assert.assertFalse(model.setStartingPlayer("C"));
        /* start player = "B" */
        Assert.assertTrue(model.setStartingPlayer("B"));
        Assert.assertEquals(player2.getPlayerID(),model.getStartingPlayerID());
        Assert.assertEquals("B",model.getMatchPlayersList().get(model.getStartingPlayerID()).getPlayerNickname());

    }

    @Test
    public void testStartTurn() {
        testStartingPlayer();
        /* the current player is the challenger */
        Assert.assertEquals(model.getMatchPlayersList().get(model.getChallengerID()), model.getCurrentTurn().getCurrentPlayer());
        /* the start player start the turn */
        model.startTurn();
        Assert.assertEquals(model.getMatchPlayersList().get(model.getStartingPlayerID()), model.getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void testingNextPlayer() {
        testStartingPlayer();
        model.getCurrentTurn().nextTurn(model.getMatchPlayersList().get(model.getStartingPlayerID()));
        /* next player = player next to the current player "B" -> "A" "A" -> "B"  */
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

        /* observer to check notify */
        Observer observer = new Observer() {
            @Override
            public void update(Object message) {
                Assert.assertTrue(message instanceof Obj);
                Assert.assertEquals(((Obj) message).getReceiver(), player1.getPlayerNickname());
                Assert.assertEquals(Messages.wrongTurn, ((Obj) message).getMessage());
            }
        };
        model.addObservers(observer);

        /* send wrongTurn message to players "A" -> not the current player */
        Assert.assertFalse(model.checkTurn(player1.getPlayerNickname()));
        Assert.assertTrue(model.checkTurn(player2.getPlayerNickname()));
    }

    @Test
    public void testCheckGodPowerAnswer() {
        testStartTurn();
        /* check the answer of GodPowerMessages */
        model.getCurrentTurn().choseWorker(player2.chooseWorker(0));
        GodPowerMessage god = GodPowerMessage.valueOf(player2.getGodCard());
        GameMessage gm = new GameMessage(god.getMessage());
        /* invalid answer */
        gm.setAnswer("ciao!");
        Assert.assertFalse(model.checkAnswer(gm));

        /* valid answer */
        gm.setAnswer("NO");
        Assert.assertTrue(model.checkAnswer(gm));
        Assert.assertEquals(god.getAnswer2(), gm.getAnswer());

        /* GodPower */
        if(model.getCurrentTurn().getChosenWorker() instanceof Hestia){
            gm.setAnswer("YES");
            Assert.assertTrue(model.checkAnswer(gm));
            Assert.assertEquals(god.getAnswer1(), gm.getAnswer());
        }else {  /* Hephaestus */
            Assert.assertTrue(model.getCurrentTurn().getChosenWorker() instanceof Hephaestus);
            model.getCurrentTurn().getChosenWorker().buildBlock(model.getBoard().getTile(0,0));
            gm.setAnswer("YES");
            /* another block on the built tile */
            Assert.assertTrue(model.checkAnswer(gm));
            Assert.assertEquals(god.getAnswer1(), gm.getAnswer());
            Assert.assertEquals(2,model.getBoard().getTile(0,0).getBlockLevel());
        }
    }

    @Test
    public void testCheckWorkerAnswer() {
        testStartTurn();

        /* check answer of Worker messages */
        GameMessage gm = new GameMessage(Messages.worker);
        /* invalid answer */
        gm.setAnswer("ciao!");

        /* valid answers: "0" / "1" */
        Assert.assertFalse(model.checkAnswer(gm));
        gm.setAnswer("0");
        Assert.assertTrue(model.checkAnswer(gm));
        gm.setAnswer("1");
        Assert.assertTrue(model.checkAnswer(gm));

        /* observer to check notify */
        Observer observer;
        observer = new Observer() {
            @Override
            public void update(Object message) {
                Assert.assertTrue(message instanceof Obj);
                if(((Obj) message).getTag().equals(Tags.GENERIC)){
                    Assert.assertEquals(Messages.tryAgain, ((Obj) message).getMessage());
                }
            }
        };
        model.addObservers(observer);

        /* if the answer is not "YES" or "NO" -> try again */
        gm = new GameMessage(Messages.confirmWorker);
        gm.setAnswer("ciao!");

        /* observer to check notify of answer "NO" */
        Assert.assertFalse(model.checkAnswer(gm));
        observer = new Observer() {
            @Override
            public void update(Object message) {
                Assert.assertTrue(message instanceof Obj);
                if(((Obj) message).getTag().equals(Tags.G_MSG)){
                    Assert.assertEquals(Messages.worker, ((Obj) message).getGameMessage().getMessage());
                }
            }
        };
        /* if the answer is "NO" return false -> send message to choose new worker index */
        model.addObservers(observer);
        gm.setAnswer("NO");
        Assert.assertFalse(model.checkAnswer(gm));
        gm.setAnswer("YES");
        Assert.assertTrue(model.checkAnswer(gm));
    }

    @Test
    public void testBeforeOperation(){
        testStartTurn();
        /* choose worker state */
        Assert.assertEquals(0, model.getCurrentTurn().getState());
        model.getCurrentTurn().choseWorker(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0));
        model.getCurrentTurn().nextState();
        /* move state */
        Assert.assertEquals(1, model.getCurrentTurn().getState());

    }

    @Test
    public void testOperation(){
        testBeforeOperation();

        /* check notify */
        Observer observer = new Observer() {
            @Override
            public void update(Object message) {
                Operation op = ((Obj) message).getOperation();
                Assert.assertEquals(model.getCurrentTurn().getState(), op.getType());
                /* move operation */
                Assert.assertEquals(1, op.getType());
                Assert.assertEquals(-1, op.getRow());
                Assert.assertEquals(-1, op.getColumn());
            }
        };
        model.addObservers(observer);
        /* notify operation */
        model.operation();
    }


    @Test
    public void testCheckWin() {
        testBeforeOperation();
        /* set win conditions */
        Tile position = new Tile();
        position.setBlockLevel(2);
        Tile destination = new Tile();
        destination.setBlockLevel(3);

        model.getCurrentTurn().setInitialTile(position);
        model.getCurrentTurn().getChosenWorker().setPosition(destination);
        /* win -> game over */
        Assert.assertTrue(model.getCurrentTurn().getChosenWorker().checkWin(position));
        Assert.assertTrue(model.checkWin());
        Assert.assertTrue(model.isGameOver());

        /* not win -> level 2 to 2 */
        destination.setBlockLevel(2);
        assertFalse(model.checkWin());

        /* Pan win conditions: 2 -> 0 */
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
        testBeforeOperation();
        /* worker (0,0) */
        model.getCurrentTurn().getChosenWorker().setPosition(model.commandToTile(0,0));
        Assert.assertFalse(model.checkLose());;

        /* set losing condition */
        model.getMatchPlayersList().get(0).chooseWorker(0).setPosition(model.commandToTile(0,1));
        model.getMatchPlayersList().get(0).chooseWorker(1).setPosition(model.commandToTile(1,1));
        model.getMatchPlayersList().get(1).chooseWorker(1).setPosition(model.commandToTile(1,0));
        Assert.assertEquals(1, model.getCurrentTurn().getState());

        /* worker can't move -> lose -> game over */
        Assert.assertEquals(player2, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertTrue(model.checkLose());
        Assert.assertEquals(2, model.getMatchPlayersList().size());
        /* current player = winner */
        Assert.assertEquals(player1, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertTrue(model.isGameOver());
    }

    @Test
    public void testLose2() {
        testStartTurn();
        /* test of lose without game over */
        Player player3 = new Player("C");
        player3.setPlayerID(2);
        model.addPlayer(player3);
        player3.createWorker("MINOTAUR", model.getConditions(), model.getTotalWorkers());
        Assert.assertTrue(player3.chooseWorker(0) instanceof Minotaur);
        Assert.assertSame(player3.chooseWorker(0).getClass(), player3.chooseWorker(1).getClass());
        model.getTotalWorkers().addAll(player3.getWorkerList());

        /* check notify lose */
        Observer observer = new Observer() {
            @Override
            public void update(Object message) {
                Assert.assertTrue(message instanceof Obj);
                Assert.assertEquals(Tags.END, ((Obj) message).getTag());
                Assert.assertEquals("lose", ((Obj) message).getMessage());
                Assert.assertEquals(player1.getPlayerNickname(), ((Obj) message).getPlayer());
            }
        };
        model.addObservers(observer);

        /* set current player = "A" + place workers */
        model.getCurrentTurn().nextTurn(player1);
        model.getCurrentTurn().choseWorker(player1.chooseWorker(0));
        model.getCurrentTurn().getChosenWorker().setPosition(model.commandToTile(0,0));
        model.getMatchPlayersList().get(1).chooseWorker(0).setPosition(model.commandToTile(0,1));
        model.getMatchPlayersList().get(0).chooseWorker(1).setPosition(model.commandToTile(1,1));
        model.getMatchPlayersList().get(1).chooseWorker(1).setPosition(model.commandToTile(1,0));

        /* worker can't move -> lose, remove player, workers, no game over */
        model.getCurrentTurn().nextState();
        Assert.assertEquals(1, model.getCurrentTurn().getState());
        Assert.assertEquals(player1, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertEquals(3, model.getMatchPlayersList().size());
        Assert.assertEquals(6, model.getTotalWorkers().size());
        Assert.assertTrue(model.checkLose());
        /* change current player */
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
        /* test creation list of all workers in match */
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
        testBeforeOperation();

        /* obbeserver to check notify message */
        Observer observer = new Observer() {
            @Override
            public void update(Object message) {
                Assert.assertTrue(message instanceof Obj);
                Assert.assertEquals(Tags.GENERIC, ((Obj) message).getTag());
                Assert.assertEquals("ciao", ((Obj) message).getMessage());
                Assert.assertEquals(((Obj) message).getReceiver(), model.getCurrentTurn().getCurrentPlayer().getPlayerNickname());
            }
        };
        model.addObservers(observer);
        model.sendMessage(Tags.GENERIC,"ciao");

    }

    @Test
    public void testSendGodPowerMessage() {
        testBeforeOperation();
        /* check notify send godPower message */
        Observer observer = new Observer() {
            @Override
            public void update(Object message) {
                Assert.assertTrue(message instanceof Obj);
                Assert.assertEquals(Tags.G_MSG, ((Obj) message).getTag());
                GameMessage gm = ((Obj) message).getGameMessage();
                /* message = message of the god card */
                GodPowerMessage god = GodPowerMessage.valueOf(model.getCurrentTurn().getCurrentPlayer().getGodCard());
                Assert.assertEquals(gm.getMessage(), god.getMessage());
                Assert.assertEquals(((Obj) message).getReceiver(), model.getCurrentTurn().getCurrentPlayer().getPlayerNickname());
            }
        };
        model.addObservers(observer);
        model.sendMessage(Tags.G_MSG,model.getCurrentTurn().getCurrentPlayer().getGodCard());

    }

    @Test
    public void testShowComplete() {
        testBeforeOperation();
        /* check notify showComplete */
        Observer observer = new Observer() {
            @Override
            public void update(Object message) {
                Assert.assertTrue(message instanceof Obj);
                Assert.assertEquals(((Obj) message).getList(), model.getGodsList().getCompleteGodList());
            }
        };
        model.addObservers(observer);
        model.showCompleteGodList();
    }

    @Test
    public void testShowGodList(){
        testDefineGodList();
        /* check notify showGodList */
        Observer observer = new Observer() {
            @Override
            public void update(Object message) {
                Assert.assertTrue(message instanceof Obj);
                Assert.assertEquals(((Obj) message).getList(), model.getGodsList().getCurrentGodList());
            }
        };
        model.addObservers(observer);
        Assert.assertEquals(2, model.getGodsList().getCurrentGodList().size());
        model.showGodList();
    }

    @Test
    public void testBroadcast() {
        Observer observer = new Observer() {
            @Override
            public void update(Object message) {
                Assert.assertTrue(message instanceof Obj);
                Assert.assertTrue(((Obj) message).isBroadcast());
                Assert.assertEquals("ciao", ((Obj) message).getMessage());
            }
        };
        model.addObservers(observer);
        model.broadcast(new Obj(Tags.GENERIC,"ciao"));

    }
}