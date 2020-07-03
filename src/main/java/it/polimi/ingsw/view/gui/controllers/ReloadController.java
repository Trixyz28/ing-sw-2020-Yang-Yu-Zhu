package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;

/**
 * Class that implements the ReloadController of the GUI of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class ReloadController {

    private GUI gui;

    //Popup

    /**
     * Reloads the GUI.
     */
    public void playAgain() {
        gui.restart();
    }

    /**
     * Handles the GUI association to the ReloadController.
     * @param gui Variable that represents the GUI that needs to reload.
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

}
