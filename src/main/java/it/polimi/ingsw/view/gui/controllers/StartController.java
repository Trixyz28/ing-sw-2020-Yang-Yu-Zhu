package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GuiLauncher;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.security.Key;
import java.util.Timer;

public class StartController {

    // Start.fxml

    private GuiLauncher guiLauncher;

    @FXML
    private Pane startPane;
    @FXML
    private Label firstPageLabel;
    @FXML
    public void loadingScene(MouseEvent event) {
        guiLauncher.changeScene(1);
    }


    public void setGlow() {
        FadeTransition transition = new FadeTransition();
        transition.setNode(firstPageLabel);
        transition.setDuration(Duration.millis(1200));
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.setCycleCount(-1);
        transition.setAutoReverse(true);

        transition.play();
    }


    public void setGuiLauncher(GuiLauncher guiLauncher) {
        this.guiLauncher = guiLauncher;
    }

}
