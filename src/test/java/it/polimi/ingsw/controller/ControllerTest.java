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



    @Test
    public void testMinorControllers() {
        controller = new Controller(model,views);
        controller.minorControllers(model,views);
    }

    public void testCheckTurn() {
    }

    public void testUpdate() {
    }
}