package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.view.View;



public class Controller implements Observer {

    private Match match;
    private View view;

    public Controller(Match match, View view) {
        this.match = match;
        this.view = view;
    }







    @Override
    public void update(Object message) {

    }
}
