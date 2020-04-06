package it.polimi.ingsw;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.view.View;


public class App {


    public static void main(String[] args) {
        Match match = new Match(2);
        View view = new View();
        Controller controller = new Controller(match, view);
        view.addObservers(controller);
        match.addObservers(view);
        view.run();

    }


}