package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.Observer;


public class Controller implements Observer {

    protected Model model;
    protected View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }




    @Override
    public void update(Object arg) {

    }
}
