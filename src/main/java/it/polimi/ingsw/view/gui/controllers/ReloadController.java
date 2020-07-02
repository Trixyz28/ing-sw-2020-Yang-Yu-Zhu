package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;

public class ReloadController {

    private GUI gui;

    //Popup
    public void playAgain() {
        gui.restart();
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

}
