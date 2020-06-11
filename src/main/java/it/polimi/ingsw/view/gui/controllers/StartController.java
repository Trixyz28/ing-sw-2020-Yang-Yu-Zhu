package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.gui.GuiLauncher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class StartController extends Commuter {

    @FXML
    private Pane startPane;

    @FXML
    public void loadingScene(MouseEvent event) throws Exception {
        System.out.println("Go to loading scene");
        super.getGuiLauncher().changeScene(1);
    }


}

