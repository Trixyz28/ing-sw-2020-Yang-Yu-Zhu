package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.SocketConnection;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ControllerTest extends TestCase {

    Model model = new Model();
    Map<Player, View> views = new HashMap<>();
    Controller controller;

    @Before
    public void initializing() {
        model.initialize(2);
        Player player1 = new Player("A");
        Player player2 = new Player("B");
        model.addPlayer(player1);
        model.addPlayer(player2);
        controller = new Controller(model,views);
    }


    @Test
    public void testSetup() {
        controller.update("setup");

    }




    public void testMinorControllers() {
    }

    public void testCheckTurn() {
    }

    public void testUpdate() {
    }
}