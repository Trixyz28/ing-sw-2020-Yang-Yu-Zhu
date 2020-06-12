package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.server.SocketConnection;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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

        gm.setAnswer("LIMUS");
        observable.notify(gm);
        Assert.assertEquals(2, model.getGodsList().getCurrentGodList().size());
        Assert.assertTrue(model.getGodsList().getCurrentGodList().contains("LIMUS"));
        Assert.assertTrue(model.getGodsList().checkLength());

        Assert.assertNotEquals(model.getCurrentTurn().getCurrentPlayer(), challenger);

    }

    @Test
    public void testChooseGod() {
        testDefineGodList();
        GameMessage gm = new GameMessage(model.getCurrentTurn().getCurrentPlayer(), null, true);

        gm.setAnswer("LIMU");
        observable.notify(gm);
        Assert.assertEquals(2, model.getGodsList().getCurrentGodList().size());

        gm.setAnswer("LIMUS");
        observable.notify(gm);
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());

        Assert.assertEquals(model.getCurrentTurn().getCurrentPlayer(), challenger);
        Assert.assertEquals("ATHENA", challenger.getGodCard());
        Assert.assertEquals("LIMUS", model.getMatchPlayersList().get(model.getNextPlayerIndex()).getGodCard());
    }



    }