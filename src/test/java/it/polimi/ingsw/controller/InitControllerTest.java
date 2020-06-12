package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.God.Athena;
import it.polimi.ingsw.model.God.Limus;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class InitControllerTest extends TestCase {

    Model model = new Model();
    InitController initController = new InitController(model);
    Player player1;
    Player player2;


    @Test
    public void testInitialize() {
        model.initialize(2);
        player1 = new Player("A");
        player1.setPlayerID(0);
        model.addPlayer(player1);
        player2 = new Player("B");
        player2.setPlayerID(1);
        model.addPlayer(player2);
        Assert.assertEquals(2, model.getMatchPlayersList().size());

        Assert.assertFalse(initController.isEndInitialize());
        initController.initializeMatch();
        Assert.assertEquals(model.getChallengerID(), model.getMatchPlayersList().get(model.getChallengerID()).getPlayerID());
        Assert.assertSame(model.getCurrentTurn().getCurrentPlayer(), model.getMatchPlayersList().get(model.getChallengerID()));
    }

    @Test
    public void testDefineGodList() {
        testInitialize();
        Assert.assertFalse(model.getGodsList().checkLength());
        initController.defineGodList("LIMUS", model.getGodsList());
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());
        initController.defineGodList("LIMUS", model.getGodsList());
        Assert.assertFalse(model.getGodsList().checkLength());
        Assert.assertEquals(1, model.getGodsList().getCurrentGodList().size());
        initController.defineGodList("ATHENA", model.getGodsList());
        Assert.assertEquals(2, model.getGodsList().getCurrentGodList().size());
        Assert.assertTrue(model.getGodsList().checkLength());
        /* next player */
        Assert.assertNotEquals(model.getCurrentTurn().getCurrentPlayer(), model.getMatchPlayersList().get(model.getChallengerID()));
    }

    public void testChooseGod() {
        testDefineGodList();
        Player player = model.getCurrentTurn().getCurrentPlayer();

        initController.chooseGod("ATLAS");
        Assert.assertSame(player, model.getCurrentTurn().getCurrentPlayer());
        initController.chooseGod("ATHENA");
        Assert.assertEquals("ATHENA", player.getGodCard());
        Assert.assertTrue(player.chooseWorker(0) instanceof Athena);

        Assert.assertNotEquals(player, model.getCurrentTurn().getCurrentPlayer());
        Assert.assertSame(model.getCurrentTurn().getCurrentPlayer(), model.getMatchPlayersList().get(model.getChallengerID()));
        Assert.assertEquals("LIMUS", model.getMatchPlayersList().get(model.getChallengerID()).getGodCard());
        Assert.assertTrue(model.getCurrentTurn().getCurrentPlayer().chooseWorker(0) instanceof Limus);

        Assert.assertEquals(4, model.getTotalWorkers().size());
        /* fine scelta god, creazione worker */
    }

    @Test
    public void testSetStartingPlayer() {
        testChooseGod();

        initController.setStartingPlayer("C");
        Assert.assertNotEquals("C", model.getMatchPlayersList().get(model.getStartingPlayerID()).getPlayerNickname());

        initController.setStartingPlayer(player1.getPlayerNickname());
        Assert.assertSame(player1, model.getMatchPlayersList().get(model.getStartingPlayerID()));
        Assert.assertEquals(player1.getPlayerID(), model.getStartingPlayerID());

        Assert.assertSame(player1, model.getCurrentTurn().getCurrentPlayer());
        /* inizio posizione workers */
    }

    @Test
    public void testPlaceWorker() {
        testSetStartingPlayer();
        Player player = model.getCurrentTurn().getCurrentPlayer();

        initController.placeWorker(new Operation(player, 0, 0,0));
        Assert.assertSame(model.commandToTile(0,0), player.chooseWorker(0).getPosition());
        initController.placeWorker(new Operation(player, 0, 0,0));
        /* Tile occupata */
        Assert.assertEquals(null, player.chooseWorker(1).getPosition());
        initController.placeWorker(new Operation(player, 0, 1,0));
        Assert.assertSame(model.commandToTile(1,0), player.chooseWorker(1).getPosition());

        /* next Player */
        Assert.assertNotEquals(player, model.getCurrentTurn().getCurrentPlayer());

        player = model.getCurrentTurn().getCurrentPlayer();
        initController.placeWorker(new Operation(player, 0, 2,2));
        Assert.assertSame(model.commandToTile(2,2), player.chooseWorker(0).getPosition());
        initController.placeWorker(new Operation(player, 0, 4,4));
        Assert.assertSame(model.commandToTile(4,4), player.chooseWorker(1).getPosition());

        Assert.assertTrue(initController.isEndInitialize());
        /* Fine inizializzazione */
    }


}