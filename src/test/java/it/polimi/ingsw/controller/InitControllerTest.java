package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class InitControllerTest extends TestCase {

    Model model = new Model();
    Map<Player, View> views = new HashMap<>();
    InitController initController = new InitController(model);


    @Test
    public void testInitialize() {
        model.initialize(2);
        Player player1 = new Player("A");
        model.addPlayer(player1);
        Player player2 = new Player("B");
        model.addPlayer(player2);


        //assertFalse(initController.isGodChanged());
        assertFalse(initController.isEndInitialize());



    }


}