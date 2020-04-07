package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.Observer;

import it.polimi.ingsw.model.Player;

import java.util.ArrayList;


public class Controller implements Observer {

    protected Match match;
    protected View view;

    public Controller(Match match, View view) {
        this.match = match;
        this.view = view;
    }




    @Override
    public void update(Object arg) {

    }
}
