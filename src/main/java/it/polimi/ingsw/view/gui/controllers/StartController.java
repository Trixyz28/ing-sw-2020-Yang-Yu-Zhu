package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GuiLauncher;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
/**
 * Class that implements the StartController of the GUI of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */

public class StartController {

    // Start.fxml

    private GuiLauncher guiLauncher;

    @FXML
    private Pane startPane;
    @FXML
    private ImageView startLogo;
    @FXML
    private Label firstPageLabel;

    /**
     * Loads the JavaFX scene.
     */
    @FXML
    public void loadingScene() {
        guiLauncher.appStarted();
    }

    /**
     * Handles the transition effect at the start of the GUI.
     */
    public void setEffects() {
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(startLogo);
        scale.setDuration(Duration.millis(1500));
        scale.setFromX(0.5f);
        scale.setFromY(0.5f);
        scale.setToX(1f);
        scale.setToY(1f);
        scale.setCycleCount(1);
        scale.setAutoReverse(false);
        scale.play();

        FadeTransition glowing = new FadeTransition();
        glowing.setNode(firstPageLabel);
        glowing.setDuration(Duration.millis(1200));
        glowing.setFromValue(0);
        glowing.setToValue(1);
        glowing.setCycleCount(-1);
        glowing.setAutoReverse(true);
        glowing.play();
    }

    /**
     * Sets the GuiLauncher for the StartController
     * @param guiLauncher Variable that helps the initial setting and start for the GUI implementation.
     */
    public void setGuiLauncher(GuiLauncher guiLauncher) {
        this.guiLauncher = guiLauncher;
    }

}
