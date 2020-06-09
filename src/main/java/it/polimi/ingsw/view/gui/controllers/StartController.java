package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class StartController {

    private static GUI gui;

    @FXML
    private Label commandRecv;

    @FXML
    private Pane startPane;

    @FXML
    public void loadingScene(MouseEvent event) throws IOException {
        gui.getGuiLauncher().changeScene(1);
    }

    public void getCommand(String str) {
        System.out.println(str);
        commandRecv.setText(str);
    }


    public static void setGui(GUI gui) {
        StartController.gui = gui;
    }


}

