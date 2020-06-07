package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;

public class Default {

    private GUI gui;


    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void sendMessage(String str) {
        gui.receiveInput(str);
    }



}
