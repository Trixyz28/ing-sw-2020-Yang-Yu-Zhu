package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.God.Conditions;
import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.view.View;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnControllerTest extends TestCase {

    Model model;
    TurnController turnController;


    @Test
    public void testChooseWorker() {
        model = new Model();
        model.initialize(2);
        model.setStartingPlayerID(0);
        Player player1 = new Player("A");
        Player player2 = new Player("B");
        model.addPlayer(player1);
        model.addPlayer(player2);
        Conditions conditions = new Conditions();
        player1.createWorker("ATLAS",conditions,model.getTotalWorkers());
        player2.createWorker("PAN",conditions,model.getTotalWorkers());
        model.createTotalWorkerList();
        UndecoratedWorker worker1 = player1.getWorkerList().get(0);

        worker1.setPosition(model.getBoard().getTile(2,1));

        Map<Player, View> views = new HashMap<>();
        turnController = new TurnController(model);



    }



}